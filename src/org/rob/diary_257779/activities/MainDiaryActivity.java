package org.rob.diary_257779.activities;

import java.util.ArrayList;

import org.rob.diary_257779.R;
import org.rob.diary_257779.database.DataBaseHelper;
import org.rob.diary_257779.database.MyListAdapter;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainDiaryActivity extends ListActivity {
	private DataBaseHelper db = null;
	private MyListAdapter adapter=null;
	private ArrayList<String[]> rows = null;
	private static final String DIARY = "diary";
	private ArrayList<Integer> idArray = new ArrayList<Integer>();
	private ArrayList<String> diaryArray = new ArrayList<String>();
	final Context context = this;
	EditText myTitle;

	String[] diary = new String[] {"toto", "titi", "tata", "tutu"};  
	String [] CONTEXT_MENU = {"Share", "Edit", "Delete"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new DataBaseHelper(MainDiaryActivity.this);
		db.open();
		int business_rows = db.countRows(DIARY);
		if(business_rows == 0){
			db.setupDiary(diary);
		}
		populate_data();
	
		adapter = new MyListAdapter(this, diaryArray);
		setListAdapter(adapter);
		ListView listView = getListView();
		registerForContextMenu(listView);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		menu.setHeaderTitle(diaryArray.get(info.position));
		menu.add(0, v.getId(), 0, CONTEXT_MENU[0]);
		menu.add(0, v.getId(), 0, CONTEXT_MENU[1]);
		menu.add(0, v.getId(), 0, CONTEXT_MENU[2]);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		if(item.getTitle() == CONTEXT_MENU[0]){
			share(CONTEXT_MENU[0], info.position);
		}else if(item.getTitle() ==  CONTEXT_MENU[1]){
			edit(info.position);
		}else if(item.getTitle() == CONTEXT_MENU[2]){
			delete(CONTEXT_MENU[2], info.position);
		}else return false;
		return true;
	}

	public void share(String tittle, int i){
		Toast.makeText(this, "You selected :"+diaryArray.get(i)+","+tittle, Toast.LENGTH_LONG).show();
	}
	
	public void edit(int i){
		statusDialog(diaryArray.get(i), idArray.get(i), i);  
		adapter.notifyDataSetChanged();
	}  
	
	public void delete(String tittle, int i){
		Toast.makeText(this, "You selected :"+diaryArray.get(i)+","+tittle, Toast.LENGTH_LONG).show();
		db.deleteDiary(idArray.get(i));
		idArray.remove(i);
		diaryArray.remove(i);
		adapter.notifyDataSetChanged();
	}  

	private void populate_data(){
		rows = db.get_all_from_table(DIARY);    
		for(String[] p : rows){
			idArray.add(Integer.parseInt(p[0]));
			diaryArray.add(p[1]);       
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String value = (String) getListAdapter().getItem(position);
		Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
	}
	
	private void statusDialog(String title, int id, int i){
		final int fId = id;
		final int fI = i;
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.custom_list);
		dialog.setTitle("EDIT");    
		myTitle = (EditText) dialog.findViewById(R.id.title);
		myTitle.setText(title);
		
		Button update = (Button) dialog.findViewById(R.id.btnUpdate);
		Button cancel = (Button) dialog.findViewById(R.id.btnCancel);

		update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				db.updateDiary(fId, myTitle.getText().toString());
				diaryArray.set(fI, myTitle.getText().toString());
				dialog.dismiss();          
			}
		});  
		
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();          
			}
		});
		
		LayoutParams params = getWindow().getAttributes(); 
		params.height = LayoutParams.FILL_PARENT; 
		getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
		dialog.show();      
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		//CommonMenu.onCreateOptionsMenu(menu, getApplicationContext());
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//if(CommonMenu.onOptionsItemSelected(item, this) == false) {
			//TODO
		//}
		return super.onOptionsItemSelected(item);
	}
}
