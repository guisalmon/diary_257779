package org.rob.diary_257779.database;

import java.util.ArrayList;

import org.rob.diary_257779.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyListAdapter extends ArrayAdapter<String>{

	private final Context context;
	private final ArrayList<String> name;

	public MyListAdapter(Context context,  ArrayList<String> name) {
		super(context,  R.layout.custom_list, name);
		this.context = context;
		this.name = name;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.custom_list, parent, false);
		TextView nameText = (TextView) rowView.findViewById(R.id.title);
		nameText.setText(name.get(position));
		
		return rowView;
	}
}
