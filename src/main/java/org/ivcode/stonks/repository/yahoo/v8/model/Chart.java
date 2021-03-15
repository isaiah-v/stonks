package org.ivcode.stonks.repository.yahoo.v8.model;

import java.util.List;

public class Chart {
	
	private List<ChartResult> result;
	private String error;

	public List<ChartResult> getResult() {
		return result;
	}

	public void setResult(List<ChartResult> result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
