package org.ivcode.stonks.graph;

import java.time.LocalDateTime;

public interface GraphBuilder {

	GraphBuilder next(LocalDateTime time);
	GraphBuilder withPoint(String line, double value);
	GraphBuilder withLineNote(String line, String title, String note);
	GraphBuilder withGraphNote(String title, String note);

	
}
