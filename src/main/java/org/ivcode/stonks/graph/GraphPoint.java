package org.ivcode.stonks.graph;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class GraphPoint {
	private final LocalDateTime time;
	private final List<Data> data;
	private final List<Note> notes;

	public GraphPoint(LocalDateTime time, List<Data> data, List<Note> notes) {
		this.time = time;
		this.data = data==null||data.isEmpty() ? null : Collections.unmodifiableList(data);
		this.notes = notes==null||notes.isEmpty() ? null : Collections.unmodifiableList(notes);
	}

	public List<Data> getData() {
		return data;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public List<Note> getNotes() {
		return notes;
	}

}
