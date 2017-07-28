package com.anand.cache.domain;

import java.io.IOException;
import java.util.concurrent.Future;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.anand.common.model.PostalLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
		//evCache = new EVCache.Builder().setAppName("CACHE-POSTCODES").setCachePrefix("prefix").enableRetry()
		//		.setDefaultTTL(300).build();

		// Debug
		setKey("695025",
				new PostalLocation("695025", "Muttada S.O", "Thiruvananthapuram", "Thiruvananthapuram", "KERALA"),500);
		setKey("695143",
				new PostalLocation("695143", "Palachira B.O", "Chirayinkeezhu", "Thiruvananthapuram", "KERALA"),500);
		setKey("695603",
				new PostalLocation("695603", "Parakunnu B.O", "Chirayinkeezhu", "Thiruvananthapuram", "KERALA"),500);
		PostalLocation loc = PostalLocation.getObj(evCache.get("695025"));
		System.out.println("Anand 1-"+ loc);
	}

	public PostalLocation getPostalLocation(String pincode) throws EVCacheException, JsonParseException, JsonMappingException, IOException {
		
		return PostalLocation.getObj(evCache.get(pincode));
	}

	public void setKey(String key, PostalLocation value, int timeToLive) throws Exception {
		try {
			Future<Boolean>[] _future = evCache.set(key, value.toString(), timeToLive);

			// Wait for all the Futures to complete.
			// In "verbose" mode, show the status for each.
			for (Future<Boolean> f : _future) {
				System.out.println("Future "+f);
				boolean didSucceed = f.get();
				System.out.println("per-shard set success code for key " + key + " is " + didSucceed);
			}
			System.out.println("finished setting key " + key);
			PostalLocation loc =  PostalLocation.getObj(evCache.get("695025"));
			System.out.println("Anand 2-"+ loc);

		} catch (EVCacheException e) {
			e.printStackTrace();
		}
	}

}
