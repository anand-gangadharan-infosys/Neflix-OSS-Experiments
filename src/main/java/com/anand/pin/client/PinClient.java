package com.anand.pin.client;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.anand.common.model.PostalLocation;

@FeignClient(name="pin",fallback = PinClientFallback.class)
public interface PinClient {

	
	@RequestMapping(method = RequestMethod.GET, value = "/persist/postal/search/{pin}")
	public PostalLocation getPinCode(@PathVariable("pin") String pin);
	
	

}

@Component
class PinClientFallback implements PinClient {

	@Override
	public PostalLocation getPinCode(String pin) {
		PostalLocation loc = new PostalLocation();
		loc.setPincode(pin);
		return loc;
	}



}
