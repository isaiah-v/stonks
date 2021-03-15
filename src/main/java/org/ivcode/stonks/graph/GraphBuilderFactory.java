package org.ivcode.stonks.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ivcode.stonks.service.investor.model.InvestorDto;
import org.ivcode.stonks.service.investor.model.SlopeTradeGovernorDto;
import org.ivcode.stonks.service.investor.model.VariableMovingAverageDto;
import org.ivcode.stonks.service.investor.model.WilliamsPercentRatingDto;

public class GraphBuilderFactory {
	
	public static final String PRICE_NAME = "price";
	public static final String RATING_NAME = "rating";
	
	public static GraphBuilderImpl createGraphBuilder(InvestorDto investorDto) {
		// create series list
		List<Series> series = new ArrayList<>();
		series.add(createMainSeries(investorDto));
		series.addAll(createWilliamsSeries(investorDto));
		series.add(createRatingSeries());
		
		// verify there are no name colisions
		Set<String> names = new HashSet<>();
		for(Series s : series) {
			
			for(String line : s.getLines()) {
				if(!names.add(line)) {
					// There cannot be duplicate line names
					throw new IllegalArgumentException("line name collision");
				}
			}
		}
		
		Series s = createTradeGovSeries(investorDto.getSlopeTradeGovernor());
		if(s!=null) {
			series.add(s);
		}
		
		return new GraphBuilderImpl(series);
	}
	
	private static Series createMainSeries(InvestorDto investorDto) {
		List<String> lines = new ArrayList<>();
		lines.add(PRICE_NAME);
		
		for(VariableMovingAverageDto vma : investorDto.getVariableMovingAverage()) {
			lines.add(vma.getName());
			lines.add(vma.getName() + "-top");
			lines.add(vma.getName() + "-bottom");
		}
		
		Series main = new Series();
		main.setName("Main");
		main.setLines(lines);
		
		return main;
	}
	
	private static List<Series> createWilliamsSeries(InvestorDto investorDto) {
		List<Series> series = new ArrayList<Series>();
		
		for(WilliamsPercentRatingDto dto : investorDto.getWilliamsPercentRating()) {
			series.add(createWilliamsSeries(dto));
		}
		
		return series;
	}
	
	private static Series createWilliamsSeries(WilliamsPercentRatingDto dto) {
		Map<String, String> properties = new HashMap<>();
		properties.put("type", "MinMax");
		properties.put("min", Double.toString(dto.getOversold()));
		properties.put("max", Double.toString(dto.getOverbought()));
		
		Series series = new Series();
		series.setName("Williams %R");
		series.setLines(Arrays.asList(dto.getName()));
		series.setProperties(properties);
		
		return series;
	}
	
	private static Series createRatingSeries() {
		Map<String, String> properties = new HashMap<>();
		properties.put("type", "MinMax");
		properties.put("min", "-1");
		properties.put("max", "1");
		
		Series rating = new Series();
		rating.setName("Rating");
		rating.setLines(Arrays.asList(RATING_NAME));
		rating.setProperties(properties);
		
		return rating;
	}
	
	private static Series createTradeGovSeries(SlopeTradeGovernorDto dto) {
		if(dto==null) {
			return null;
		}
		
		Series rating = new Series();
		rating.setName("Trade Gov");
		rating.setLines(Arrays.asList(dto.getName()));
		
		return rating;
	}
}
