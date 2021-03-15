package org.ivcode.stonks.controller;

import java.io.IOException;

import org.ivcode.stonks.market.AccountSummary;
import org.ivcode.stonks.market.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides information on your live broker
 * @author isaiah
 */
@RestController
@RequestMapping("/service/broker")
public class BrokerController {
	@Autowired
	private BrokerService broker;
	
	@GetMapping
	public AccountSummary getSummary() throws IOException {
		return broker.getAccountSummary();
	}
	
	@PostMapping(path = "/tradeMax")
	public void setTradeMax(@RequestParam(name="value") double tradeMax) throws IOException {
		broker.setTadeMax(tradeMax);
	}
	
	@DeleteMapping(path = "/tradeMax")
	public void removeTradeMax() {
		broker.setTadeMax(null);
	}
}
