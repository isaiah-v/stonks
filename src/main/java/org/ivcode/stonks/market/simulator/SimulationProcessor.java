package org.ivcode.stonks.market.simulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.ivcode.stonks.invester.Investor;
import org.ivcode.stonks.service.investor.InvestorFactory;
import org.ivcode.stonks.utils.CloseableBlockingQueue;

public class SimulationProcessor {

	private final int threads;
	private final SimulationContext context;
	private final Iterable<? extends Investor> investers;

	public SimulationProcessor(int threads, SimulationContext context, Iterable<? extends Investor> investers) {
		this.threads = threads;
		this.context = context;
		this.investers = investers;
	}

	public Report run() throws InterruptedException, ExecutionException {

		Report report = new Report();
		
		List<Future<?>> futures = new ArrayList<>();
		try (CloseableBlockingQueue<Investor> simQ = new CloseableBlockingQueue<>()) {
			ExecutorService s = Executors.newFixedThreadPool(threads);

			for (int i = 0; i < threads; i++) {
				futures.add(s.submit(new MyCallable(report, context, simQ)));
			}

			for (Investor investor : investers) {
				simQ.add(investor);
			}

			s.shutdown();
			
			while(!simQ.isEmpty()) {
				int sizeStart = simQ.size();
				
				TimeUnit.MINUTES.sleep(1);
				int sizeEnd = simQ.size();
				
				if(sizeStart!=sizeEnd) {
					int minutes = sizeEnd / (sizeStart-sizeEnd);
					System.out.println("Time Left: " + minutes + "minutes");
				}
			}
		}

		for (Future<?> f : futures) {
			f.get();
		}

		return report;
	}

	private static class ProgressLogger implements Runnable {
		CloseableBlockingQueue<Investor> simQ;

		@Override
		public void run() {
			
		}
	}
	
	private static class MyCallable implements Runnable {

		private final Report report;
		private final SimulationContext context;
		private final CloseableBlockingQueue<Investor> simQ;

		public MyCallable(Report report, SimulationContext context, CloseableBlockingQueue<Investor> simQ) {
			this.report = report;
			this.context = context;
			this.simQ = simQ;
		}

		@Override
		public void run() {
			try {
				runProcess();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
		
		private void runProcess() throws InterruptedException, IOException {
			Investor i = null;
			while ((i = simQ.next()) != null) {

				Simulation sim = context.createSimulation(i);
				SimulationSummary summary = sim.run();
				
				report.addItem(summary.getChange(), InvestorFactory.createInvestorDto(i.getSymbol(), i));
			}
		}

	}
}
