package org.rob.diary_257779.classes;

public class DiaryDate {
	private long id;
	private long day;
	private long month;
	private long year;

	public DiaryDate(long id, long day, long month, long year) {
		this.id = id;
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public long getId() {
		return id;
	}

	public long getDay() {
		return day;
	}

	public long getMonth() {
		return month;
	}

	public long getYear() {
		return year;
	}
}
