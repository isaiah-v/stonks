package org.ivcode.stonks.service.yahoo.v8;

import static org.ivcode.stonks.utils.EpochUtils.*;
import static org.ivcode.stonks.utils.CsvUtils.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.ivcode.stonks.repository.yahoo.v8.YahooClassicClient;
import org.ivcode.stonks.repository.yahoo.v8.YahooQueryClient;
import org.ivcode.stonks.repository.yahoo.v8.model.ChartResponse;
import org.ivcode.stonks.repository.yahoo.v8.model.YahooMarketTime;
import org.ivcode.stonks.repository.yahoo.v8.model.YahooQuote;
import org.ivcode.stonks.service.yahoo.v8.model.Interval;
import org.ivcode.stonks.service.yahoo.v8.model.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import retrofit2.Call;
import retrofit2.Response;

@Service
public class YahooService {

	@Autowired
	private YahooQueryClient client;
	
	@Autowired
	private YahooClassicClient classicClient;
	
	public ChartResponse getChart(String symbol, Range range, Interval interval) throws IOException {
		return call(client.getChart(symbol, range.getText(), interval.getText()));
	}
	
	public ChartResponse getChart(String symbol, Range range, Interval interval, boolean includePrePost) throws IOException {
		return call(client.getChart(symbol, includePrePost, range.getText(), interval.getText()));
	}
	
	public ChartResponse getChart(String symbol, LocalDate start, LocalDate stop, Interval interval) throws IOException {
		long startTime = epoch(start);
		long stopTime = epoch(stop.atStartOfDay().withHour(23).withMinute(59).withSecond(59));
		
		return call(client.getChart(symbol, startTime, stopTime, interval.getText()));
	}
	
	public ChartResponse getChart(String symbol, LocalDate start, LocalDate stop, Interval interval, boolean includePrePost) throws IOException {
		long startTime = epoch(start);
		long stopTime = epoch(stop.atStartOfDay().withHour(23).withMinute(59).withSecond(59));
		
		return call(client.getChart(symbol, includePrePost, startTime, stopTime, interval.getText()));
	}
	
	public YahooQuote getQuote(List<String> symbols) throws IOException {
		return call(client.getQuote(toCsv(symbols)));
	}
	
	public YahooMarketTime getMarketTime() throws IOException {
		return call(classicClient.getMarketTime());
	}
	
	private <T> T call(Call<T> call) throws IOException {
		Response<T> response = call.execute();
		if(!response.isSuccessful()) {
			throw new IllegalStateException("call failed: " + response.errorBody().string());
		}
		
		return response.body();
	}
}
