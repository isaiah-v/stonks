package org.ivcode.stonks.service.investor;

import static org.ivcode.stonks.utils.SaftyUtils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.ivcode.stonks.invester.InvesterManager;
import org.ivcode.stonks.invester.Investor;
import org.ivcode.stonks.properties.StonkPropertiesService;
import org.ivcode.stonks.service.investor.model.InvestorDto;

public class InvestorService {
		
	private final InvesterManager manager;
	private final StonkPropertiesService properties;
		
	private Map<String, InvestorDto> settingsMap = new LinkedHashMap<>();
	
	public InvestorService(InvesterManager manager, StonkPropertiesService properties, List<InvestorDto> investors) throws IOException {
		this.manager = manager;
		this.properties = properties;
		
		for(var i : safe(investors)) {
			updateInvestorSettings(i);
		}
	}
	
	public void updateInvestor(InvestorDto investorDto) throws IOException {
		updateInvestorSettings(investorDto);
		properties.setInvestors(this.getInvestors());
	}
	
	private void updateInvestorSettings(InvestorDto investorDto) throws IOException {
		Investor investor = InvestorFactory.createInvestor(investorDto);
		
		String symbol = investorDto.getSymbol();
		manager.removeInvester(symbol);
		manager.addInvester(investor, symbol);
		
		settingsMap.put(symbol, investorDto);
	}
	
	public void removeInvestor(String symbol) throws IOException {
		InvestorDto dto = settingsMap.remove(symbol);
		manager.removeInvester(dto.getSymbol());
		
		properties.setInvestors(this.getInvestors());
	}
	
	public List<InvestorDto> getInvestors() {
		return new ArrayList<>(settingsMap.values());
	}
	
	public InvestorDto getInvestor(String symbol) {
		return settingsMap.get(StringUtils.upperCase(symbol));
	}
}
