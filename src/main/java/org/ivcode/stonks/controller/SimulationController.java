package org.ivcode.stonks.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.ivcode.stonks.graph.Graph;
import org.ivcode.stonks.graph.GraphBuilder;
import org.ivcode.stonks.graph.GraphBuilderFactory;
import org.ivcode.stonks.graph.GraphBuilderImpl;
import org.ivcode.stonks.graph.GraphBuilderNop;
import org.ivcode.stonks.invester.Investor;
import org.ivcode.stonks.market.simulator.Simulation;
import org.ivcode.stonks.market.simulator.SimulationFactory;
import org.ivcode.stonks.market.simulator.SimulationSummary;
import org.ivcode.stonks.service.investor.InvestorFactory;
import org.ivcode.stonks.service.investor.InvestorService;
import org.ivcode.stonks.service.investor.model.InvestorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service/simulation")
public class SimulationController {
	
	@Autowired
	private SimulationFactory simulationFactory;
	
	@Autowired
	private InvestorService investorService;
	
	@PostMapping(path="graph")
	public Graph createGraph(@RequestBody InvestorDto investorDto) throws IOException {
		GraphBuilderImpl graphBuilder = GraphBuilderFactory.createGraphBuilder(investorDto);
		
		Investor i = InvestorFactory.createInvestor(investorDto, graphBuilder);
		
		LocalDate end = LocalDate.now();
		LocalDate start = end.minusWeeks(2);
		
		Simulation sim = simulationFactory.createSimulation(1000, investorDto.getSymbol(), start, end, i);
		sim.run();
		
		return graphBuilder.build();
	}
	
	@GetMapping(path="graph")
	public Graph createGraphBySymbol(@RequestParam String symbol) throws IOException {
		InvestorDto investor = investorService.getInvestor(symbol);
		return createGraph(investor);
	}
	
	@PostMapping(path="summary")
	public SimulationSummary createGraphBySymbol(@RequestBody InvestorDto investorDto) throws IOException {
		GraphBuilder graphBuilder = GraphBuilderNop.INSTANCE;
		
		Investor i = InvestorFactory.createInvestor(investorDto, graphBuilder);
		
		LocalDate end = LocalDate.now();
		LocalDate start = end.minusWeeks(2);
		
		Simulation sim = simulationFactory.createSimulation(1000, investorDto.getSymbol(), start, end, i);
		SimulationSummary summary = sim.run();
		
		return summary;
	}
}
