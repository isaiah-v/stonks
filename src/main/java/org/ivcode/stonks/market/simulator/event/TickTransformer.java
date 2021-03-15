package org.ivcode.stonks.market.simulator.event;

import static org.ivcode.stonks.utils.SaftyUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.Tick;
import org.ivcode.stonks.utils.TimeUtils;

class TickTransformer {
	
	public enum TickPrice {
		OPEN {
			@Override
			public Double getPrice(ChartTick tick) {
				return tick.getOpen();
			}
		},
		CLOSE {
			@Override
			public Double getPrice(ChartTick tick) {
				return tick.getClose();
			}
		},
		HIGH {
			@Override
			public Double getPrice(ChartTick tick) {
				return tick.getHigh();
			}
		},
		LOW {
			@Override
			public Double getPrice(ChartTick tick) {
				return tick.getLow();
			}
		};
		
		public abstract Double getPrice(ChartTick tick);
	}
	
	public static List<Tick> toTick(List<ChartTick> chartTicks, TickPrice value) {
		List<Tick> ticks = new ArrayList<>();
		
		for(ChartTick chartTick : chartTicks) {
			Double price = value.getPrice(chartTick);
			if(price==null) {
				continue;
			}
			
			Tick tick = new Tick(chartTick.getSymbol(), TimeUtils.toLocalDateTime(chartTick.getDate()), price, safe(chartTick.getVolume()));
			ticks.add(tick);
		}
		
		return ticks;
	}
}
