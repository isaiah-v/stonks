package org.ivcode.stonks.graph;

import java.util.ArrayList;
import java.util.List;

class DataBuilder {
	private Double value;
	private List<Note> notes = new ArrayList<>();
	
	public DataBuilder withValue(double value) {
		this.value = value;
		return this;
	}
	public DataBuilder withNote(String title, String note) {
		this.notes.add(new Note(title, note));
		return this;
	}
	
	public Data build() {
		return new Data(value, notes.isEmpty() ? null : notes);
	}
}
