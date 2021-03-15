package org.ivcode.stonks.graph;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphRow {
	private final LocalDateTime time;
	private final Map<String, DataBuilder> dataMap = new HashMap<>();
	private final List<Note> graphNotes = new ArrayList<>();

	public GraphRow(LocalDateTime time) {
		this.time = time;
	}
	
	public void withPoint(String line, double value) {
		getDataBuilder(line).withValue(value);
	}
	
	public void withLineNote(String line, String title, String note) {
		getDataBuilder(line).withNote(title, note);
	}
	
	public void withGraphNote(String title, String note) {
		graphNotes.add(new Note(title, note));
	}
	
	public LocalDateTime getTime() {
		return time;
	}

	public Map<String, DataBuilder> getDataMap() {
		return dataMap;
	}

	public List<Note> getGraphNotes() {
		return graphNotes;
	}

	private DataBuilder getDataBuilder(String line) {
		return dataMap.computeIfAbsent(line, s->new DataBuilder());
	}
}
