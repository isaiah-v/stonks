package org.ivcode.stonks.graph;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphBuilderImpl implements GraphBuilder {

	private final List<Series> series;
	private final Set<String> columns = new HashSet<>();
	private final Deque<GraphRow> rows = new LinkedList<>();

	public GraphBuilderImpl(List<Series> series) {
		this.series = series;
	}

	public GraphBuilderImpl next(LocalDateTime time) {
		GraphRow last = getRow();

		if (last != null) {
			columns.addAll(last.getDataMap().keySet());
		}

		rows.addLast(new GraphRow(time));
		return this;
	}

	public GraphBuilderImpl withPoint(String line, double value) {
		getRow().withPoint(line, value);
		return this;
	}

	public GraphBuilderImpl withLineNote(String line, String title, String note) {
		getRow().withLineNote(line, title, note);
		return this;
	}

	public GraphBuilderImpl withGraphNote(String title, String note) {
		getRow().withGraphNote(title, note);
		return this;
	}

	private GraphRow getRow() {
		return rows.peekLast();
	}

	public Graph build() {
		
		List<LocalDateTime> times = new ArrayList<>(rows.size());;
		Map<String, Line> lines = new HashMap<>(rows.size());
		Map<Integer, Map<String, String>> notes = new HashMap<>(rows.size());

		int i=0;
		for(GraphRow row : rows) {
			times.add(row.getTime());
			
			List<Note> graphNotes = row.getGraphNotes();
			if(graphNotes!=null && !graphNotes.isEmpty()) {
				Map<String, String> n = new HashMap<>();
				for(Note note : graphNotes) {
					n.put(note.getTitle(), note.getNote());
				}
				
				notes.put(i, n);
			}
			
			for(String name : columns) {
				Line line = lines.computeIfAbsent(name, k -> new Line());
				
				DataBuilder db = row.getDataMap().get(name);
				if(db==null) {
					line.getData().add(null);
				} else {
					Data data = db.build();
					line.getData().add(data.getValue());
					
					List<Note> noteList = data.getNotes();
					if(noteList!=null && !noteList.isEmpty()) {
						Map<String, String> n = new HashMap<>();
						for(Note note : noteList) {
							n.put(note.getTitle(), note.getNote());
						}
						
						line.getNotes().put(i, n);
					}
				}
			}
			i++;
		}
		
		Graph graph = new Graph();
		graph.setLines(lines);
		graph.setNotes(notes);
		graph.setSeries(series);
		graph.setTimes(times);
		
		return graph;
	}
}
