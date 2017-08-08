package com.anand.pin.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anand.common.model.PostalLocation;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class PinClient {

	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="fetchDummyPostalLocation")
	public PostalLocation getPinCode(String pinCode) {
		PostalLocation postalLocation = restTemplate.getForObject("http://pin/persist/postal/search?pin=" + pinCode, PostalLocation.class);
		return postalLocation;

	}
	
	@SuppressWarnings("unused")
	private PostalLocation fetchDummyPostalLocation(String pinCode) {
		PostalLocation loc = new PostalLocation();
		loc.setPincode(pinCode);
		return loc;
	}

}
