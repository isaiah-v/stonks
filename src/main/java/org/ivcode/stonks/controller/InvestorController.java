package org.ivcode.stonks.controller;

import java.io.IOException;
import java.util.List;

import org.ivcode.stonks.service.investor.InvestorService;
import org.ivcode.stonks.service.investor.model.InvestorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Sets up investors to 
 * @author isaiah
 *
 */
@RestController
@RequestMapping("/service/investor")
public class InvestorController {
	
	@Autowired
	private InvestorService service;
	
	@GetMapping
	public List<InvestorDto> getInvestors() {
		return service.getInvestors();
	}
	
	@PostMapping
	public void setInvestor(@RequestBody InvestorDto investor) throws IOException {
		service.updateInvestor(investor);
	}
	
	@DeleteMapping("/{symbol}")
	public void removeInvestor(@PathVariable String symbol) throws IOException {
		service.removeInvestor(symbol);
	}
}
