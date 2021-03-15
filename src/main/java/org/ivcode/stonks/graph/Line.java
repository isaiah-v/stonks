package org.ivcode.stonks.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Line {
	private List<Double> data = new ArrayList<>();
	private Map<Integer, Map<String, String>> notes = new HashMap<Integer, Map<String,String>>();

	public List<Double> getData() {
		return data;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}

	public Map<Integer, Map<String, String>> getNotes() {
		return notes;
	}

	public void setNotes(Map<Integer, Map<String, String>> notes) {
		this.notes = notes;
	}

}
