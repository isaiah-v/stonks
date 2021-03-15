package org.ivcode.stonks.graph;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;;

@JsonInclude(Include.NON_NULL)
public class Data {
	
	@JsonProperty(value = "v")
	private final Double value;
	
	@JsonProperty(value = "n")
	private final List<Note> notes;

	public Data(Double value, List<Note> notes) {
		this.value = value;
		this.notes = notes==null ? null : Collections.unmodifiableList(notes);
	}

	public Double getValue() {
		return value;
	}

	public List<Note> getNotes() {
		return notes;
	}
}
