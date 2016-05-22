# PrimeNumber Generator Web

How to start the png web application
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/pngweb-1.0-SNAPSHOT.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080`

How to use the png web application
---
The application is to demonstrate generation of prime numbers.
1. to start generation of primes up to a certain value
```
$ curl -X POST http://localhost:8080/primes/1000000
```
2. to retrieve results send a get to /primes
```
$ curl http://localhost:8080/primes
```

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`
