package org.ivcode.stonks.graph;

public class Note {
	private final String title;
	private final String note;

	public Note(String title, String note) {
		this.title = title;
		this.note = note;
	}

	public String getTitle() {
		return title;
	}

	public String getNote() {
		return note;
	}
}
