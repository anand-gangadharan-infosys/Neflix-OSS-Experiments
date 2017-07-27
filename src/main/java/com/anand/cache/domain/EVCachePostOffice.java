package com.anand.cache.domain;

import com.anand.common.model.PostalLocation;
import com.google.inject.Inject;
import com.netflix.evcache.EVCache;
import com.netflix.evcache.EVCacheException;

public class EVCachePostOffice {
	
	private EVCache evCache;
	

	@Inject
	public EVCachePostOffice(EVCache.Builder evCacheBuilder) throws EVCacheException {
		String deploymentDescriptor = System.getenv("EVC_SAMPLE_DEPLOYMENT");
		if (deploymentDescriptor == null) {
			// No deployment descriptor in the environment, use a default:
			deploymentDescriptor = "SERVERGROUP1=localhost:11211,localhost:11212;SERVERGROUP2=localhost:11213,localhost:11214";
		}
		System.setProperty("EVCACHE_APP1.use.simple.node.list.provider", "true");
		System.setProperty("EVCACHE_APP1-NODES", deploymentDescriptor);

		evCache = evCacheBuilder.setAppName("CACHE-POSTCODES").setCachePrefix("prefix").enableRetry()
				.setDefaultTTL(300).build();
		
		//Debug
		evCache.set("695025", new PostalLocation("695025","Muttada S.O","Thiruvananthapuram","Thiruvananthapuram","KERALA"));
		evCache.set("695025", new PostalLocation("695143","Palachira B.O","Chirayinkeezhu","Thiruvananthapuram","KERALA"));
		evCache.set("695025", new PostalLocation("695603","Parakunnu B.O","Chirayinkeezhu","Thiruvananthapuram","KERALA"));
	}
	
	public PostalLocation getPostalLocation(String pincode) throws EVCacheException{
		return evCache.get(pincode);
	}

}
