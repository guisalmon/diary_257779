package org.rob.diary_257779.classes;

public class Entry {
	private long id;
	private String title;
	private String content;
	private long dateId;
	private long curDateId;
	private long diaryId;
	
	public Entry(long id, String title, String content, long dateId,
			long curDateId, long diaryId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.dateId = dateId;
		this.curDateId = curDateId;
		this.diaryId = diaryId;
	}
	
	public long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public long getDateId() {
		return dateId;
	}
	
	public long getCurDateId() {
		return curDateId;
	}
	
	public long getDiaryId() {
		return diaryId;
	}
}
