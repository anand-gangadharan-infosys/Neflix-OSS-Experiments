# Netflix OSS

## Tools of Focus

* EVCache
* Eureka (Along with Histerix and Ribbon)
* Spectator 
* Zuul for client-gateway application

You can compile and start all micro services using `./megadeploy.sh`. Each service comes up in different ports. Eureka on 8761, PIN on 8080 and cache on 8081.

In addition to this we have our gateway application running on 8765, hystrix monitoring on 8082 and turbine-stream on 8083.

The turbine stream and hystrix monitoring are required for circuit breaking monitoring. to use turbine stream we need to have rabbitmq installed and running on our system.

The idea is to create three micro service. A client, cache and pin service and register them with Eureka registry. The client acts as the web gateway and invokes a search of post office(Indian) details by PIN number. The cache service intercept the call and tries to address the query. In case of a cache miss pin service which is backed by database kicks in.

The client gateway has been implemented and it will be the primary reach point from anywhere outside the application. this will ideantify the service to which the request needs to be re routed

the change in api call is something like this

http://localhost:8765/api/cache/postal/search/686632

which will access cache service and,

http://localhost:8765/api/pin/persist/postal/search/686612

which will access the pin service




## Details
---
Note that I have used gradle to manage each service as a different project in eclipse (STS) though entire repository is monolithic and maintains a maven look. You can work in an IDE on one or more of the services and the same scripts can be easliy weaved later to production deployment systems like jenkins

All service lookups should be handled by Eureka.

The caching service use evcache service. Finally we create some Jmeter loads, simulate some errors in caching layer and monitor the system using Vector.

The infrastructure folder contains the tools requirements like EVCache configs and Eureka. Other dependencies like memcache and mysql should be installed separately, though data dump is available in db folder

If I get time later I will add a Docker to wrap everything neatly so that there is not much setup time.
