package org.ivcode.stonks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Checks the health of the application
 * 
 * @author isaiah
 *
 */
@RestController
@RequestMapping("/health")
public class HealthController {
	
	@GetMapping
	public String healthCheck() {
		return "OK";
	}
}
