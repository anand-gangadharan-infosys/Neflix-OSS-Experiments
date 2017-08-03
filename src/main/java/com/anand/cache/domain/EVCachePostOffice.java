package com.anand.cache.domain;

import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.anand.common.model.PostalLocation;
import com.netflix.evcache.EVCache;
import com.netflix.evcache.EVCacheException;

@Repository
public class EVCachePostOffice {

	private EVCache evCache;

	public EVCachePostOffice() throws Exception {
		String deploymentDescriptor = System.getenv("EVC_SAMPLE_DEPLOYMENT");
		if (deploymentDescriptor == null) {
			// No deployment descriptor in the environment, use a default:
			deploymentDescriptor = "SERVERGROUP1=localhost:11211,localhost:11212;SERVERGROUP2=localhost:11213,localhost:11214";
		}
		System.setProperty("evcache.use.simple.node.list.provider", "true");
		System.setProperty("CACHE-POSTCODES.use.simple.node.list.provider", "true");
		System.setProperty("CACHE-POSTCODES-NODES", deploymentDescriptor);

		evCache = new EVCache.Builder().setAppName("CACHE-POSTCODES").build();
	}

	public PostalLocation getPostalLocation(String pincode) throws CacheMissException {

		try {
			return PostalLocation.getObj(evCache.get(pincode));
		} catch (IOException | EVCacheException | NullPointerException ex) {
			throw new CacheMissException();
		}
	}

	public void setKey(String key, PostalLocation value, int timeToLive) throws Exception {
		try {
			evCache.set(key, value.toString(), timeToLive);
		} catch (EVCacheException e) {
			e.printStackTrace();
		}
	}

}
