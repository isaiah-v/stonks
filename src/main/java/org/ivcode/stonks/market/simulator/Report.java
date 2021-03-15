package org.ivcode.stonks.market.simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ivcode.stonks.service.investor.model.InvestorDto;

public class Report {

	private final List<ReportItem> items = new ArrayList<>();

	public synchronized void addItem(double change, InvestorDto settings) {
		items.add(new ReportItem(change, settings));
	}
	
	public List<ReportItem> getItems() {
		List<ReportItem> list = new ArrayList<>(items);
		Collections.sort(list, (i1, i2)->Double.compare(i1.change, i2.change));
		
		return list;
	}

	public static class ReportItem {

		private final double change;
		private final InvestorDto settings;

		public ReportItem(double change, InvestorDto settings) {
			this.change = change;
			this.settings = settings;
		}

		public double getChange() {
			return change;
		}

		public InvestorDto getSettings() {
			return settings;
		}
	}
}
