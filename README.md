# PrimeNumber Generator Web

How to start the png web application
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/pngweb-1.0-SNAPSHOT.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080`

How to use the png web application
---
The application is to demonstrate generation of prime numbers.

To start generation of primes up to a certain value send a POST to one of 3 implementations

```
$ curl -X POST http://localhost:8080/primes/basic/1000000
$ curl -X POST http://localhost:8080/primes/optimised/1000000
$ curl -X POST http://localhost:8080/primes/eratosthenes/1000000
```
To retrieve results send a GET to /primes
The application will return status 202 if it is still processing.
```
$ curl http://localhost:8080/primes
```

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`

Metrics
-------

To see your applications metrics enter url `http://localhost:8081/metrics`
