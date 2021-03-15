package org.ivcode.stonks.graph;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Graph {

	private List<Series> series;
	private List<LocalDateTime> times;
	private Map<String, Line> lines;
	private Map<Integer, Map<String, String>> notes;

	public List<Series> getSeries() {
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

	public List<LocalDateTime> getTimes() {
		return times;
	}

	public void setTimes(List<LocalDateTime> times) {
		this.times = times;
	}

	public Map<String, Line> getLines() {
		return lines;
	}

	public void setLines(Map<String, Line> lines) {
		this.lines = lines;
	}

	public Map<Integer, Map<String, String>> getNotes() {
		return notes;
	}

	public void setNotes(Map<Integer, Map<String, String>> notes) {
		this.notes = notes;
	}

}
