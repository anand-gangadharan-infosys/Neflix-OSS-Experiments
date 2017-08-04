package com.anand.pin.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anand.common.model.PostalLocation;

@Service
public class PinClient {

	@Autowired
	RestTemplate restTemplate;

	public PostalLocation getPinCode(String pinCode) {
		PostalLocation postalLocation = restTemplate.getForObject("http://pin/persist/postal/search?pin=" + pinCode, PostalLocation.class);
		return postalLocation;

	}

}
