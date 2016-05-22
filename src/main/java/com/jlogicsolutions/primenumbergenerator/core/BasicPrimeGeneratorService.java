package com.jlogicsolutions.primenumbergenerator.core;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static java.lang.Math.sqrt;
import static java.util.stream.LongStream.rangeClosed;

/**
 * basic prime number generator
 */
public class BasicPrimeGeneratorService implements PrimeGeneratorService {
    /**
     * starts generating prime numbers upto the given value
     *
     * @param highestPrimeRequired
     * @return
     */
    @Override
    public Future<List<Long>> startPrimeGeneration(final Long highestPrimeRequired) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        CompletableFuture<List<Long>> primes = CompletableFuture.supplyAsync(() ->
                rangeClosed(2, highestPrimeRequired)
                        .parallel()
                        .filter(BasicPrimeGeneratorService::checkIfPrime)
                        .boxed()
                        .collect(Collectors.toList()), forkJoinPool);
        return primes;
    }

    /**
     * check if the candidate number is prime
     *
     * @param candidatePrime
     * @return
     */
    private static boolean checkIfPrime(long candidatePrime) {
        return candidatePrime > 1 &&
                rangeClosed(2, (long) sqrt(candidatePrime))
                        .noneMatch(divisor -> candidatePrime % divisor == 0);
    }
}
