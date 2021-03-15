package org.ivcode.stonks.graph;

import java.time.LocalDateTime;

public class GraphBuilderNop implements GraphBuilder {

	public static GraphBuilder INSTANCE = new GraphBuilderNop();
	
	private GraphBuilderNop() {
	}
	
	@Override
	public GraphBuilder next(LocalDateTime time) {
		return this;
	}

	@Override
	public GraphBuilder withPoint(String line, double value) {
		return this;
	}

	@Override
	public GraphBuilder withLineNote(String line, String title, String note) {
		return this;
	}

	@Override
	public GraphBuilder withGraphNote(String title, String note) {
		return this;
	}

}
