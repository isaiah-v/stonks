package org.ivcode.stonks.invester.tradegov;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.ivcode.stonks.market.ChartTick;
import org.ivcode.stonks.market.Tick;

public class DayTradeGovernor implements TradeGovernor {

	private static final LocalTime EOD = LocalTime.parse("15:45");
	
	private final int buyQuantity;
	private final int sellQuantity;

	private InOut inOut = new InOut();

	public DayTradeGovernor(int buyQuantity, int sellQuantity) {
		this.buyQuantity = buyQuantity;
		this.sellQuantity = sellQuantity;
	}

	@Override
	public void init(List<ChartTick> history) {

	}

	@Override
	public int getQuantity(double qualityRating, Tick tick) {
		if(inOut.isTrade(qualityRating, tick)) {
			return inOut.type.equals(InOutType.SELL) ? sellQuantity : (inOut.type.equals(InOutType.BUY) ? buyQuantity : 0);
		} else {
			return 0;
		}
	}

	@Override
	public <T, R> R visit(TradeGovernorVisitor<T, R> visitor, T argument) {
		return visitor.apply(this, argument);
	}

	public int getBuyQuantity() {
		return buyQuantity;
	}
	
	private static enum InOutType {
		BUY, SELL, NONE
	}

	private static class InOut {
		private static long RESET = TimeUnit.MINUTES.convert(3, TimeUnit.HOURS);
		
		private InOutType type = InOutType.NONE;
		private boolean isOut = true;
		
		private double qualityMax = 1.25;
		private int count = 0;
		
		private LocalDateTime last;
		
		
		public boolean isTrade(double quality, Tick tick) {
			tryReset(tick);
			
			if (quality>=1 && !InOutType.BUY.equals(type)) {
				// when over-sold, set to buy
				setAs(InOutType.BUY);
			} else if(quality<=-1 && !InOutType.SELL.equals(type)) {
				// when over-bought, set to sell
				setAs(InOutType.SELL);
			} else if(Math.abs(quality)< 0.1) {
				setAs(InOutType.NONE);
			}
			
			if(this.type.equals(InOutType.NONE)) {
				return false;
			}
			
			if(Math.abs(quality) >= 1) {
				// is trade while over-bought or over-sold?
				return this.isTradeOver(quality, tick);
			} else if(Math.abs(quality) >= 0.5) {
				// is trade when exiting the over-bought/-sold zone?
				return this.isTradeOut(quality, tick);
			}
			
			return false;
		}
		
		private void setAs(InOutType type) {
			if(type.equals(this.type)) {
				return;
			}
			
			this.type = type;
			this.hardReset();
		}
		
		private boolean isTradeOut(double quality, Tick tick) {
			// trade only if we're exiting the over-bought/-sold zone and no trades were done since the last reset
			if(isOut) {
				return false;
			}
			isOut = true;
			
			boolean isTrade = count==0 && ((quality>=0 && InOutType.BUY.equals(type)) || (quality<=0 && InOutType.SELL.equals(type)));
			
			if(isTrade) {
				doTrade(quality, tick);
				return true;
			} else {
				return false;
			}
		}
		
		private boolean isTradeOver(double quality, Tick tick) {
			isOut = false;
			quality = Math.abs(quality);
			
			// if it's the end of the day and we're over-bought or over-sold, do a trade
			if(tick.getTime().toLocalTime().isAfter(EOD) && count==0) {
				doTrade(quality, tick);
				return true;
			}
			
			// if the quality has increased by 25%, do a trade
			if(quality > qualityMax * 1.25 ) {
				qualityMax = quality;
				doTrade(quality, tick);
				return true;
			} else {
				return false;
			}
		}
		
		/**
		 * Resets the state after a period of time
		 * @param tick
		 */
		private void tryReset(Tick tick) {
			LocalDateTime now = tick.getTime();
			if(last!=null && last.plusMinutes(RESET).isBefore(now)) {
				hardReset();
			}
		}
		
		private void hardReset() {
			qualityMax = 1.25;
			count = 0;
			last = null;
			isOut = true;
		}
		
		private void doTrade(double quality, Tick tick) {
			last = tick.getTime();
			count++;
			if(quality>qualityMax) {
				this.qualityMax = quality;
			}
		}
	}
}
