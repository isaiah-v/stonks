package org.ivcode.stonks.service.investor;

import java.io.IOException;
import java.util.List;

import org.ivcode.stonks.invester.InvesterManager;
import org.ivcode.stonks.properties.StonkPropertiesService;
import org.ivcode.stonks.service.investor.model.InvestorDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvestorServiceConfig {

	@Bean
	public InvestorService createInvestorService(InvesterManager manager, StonkPropertiesService properties) throws IOException {
		List<InvestorDto> investors = properties.getInvestors();
		return new InvestorService(manager, properties, investors);
	}
}
