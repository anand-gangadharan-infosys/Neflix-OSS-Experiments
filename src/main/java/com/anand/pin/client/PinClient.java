package com.anand.pin.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.client.RestTemplate;

import com.anand.common.model.PostalLocation;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;

public class PinClient {

	@Autowired
	private EurekaClient discoveryClient;

	@Autowired
	RestTemplate restTemplate;

	public PostalLocation getPinCode(String pinCode) {
		Application app = discoveryClient.getApplication("PIN");	
		List<InstanceInfo> instances = app.getInstances();
		PostalLocation postalLocation = restTemplate.getForObject(
				instances.get(0).getHomePageUrl() + "/persist/postal/search?pin=" + pinCode, PostalLocation.class);
		return postalLocation;

	}

}
