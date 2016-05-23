package com.jlogicsolutions.primenumbergenerator.core;


import com.codahale.metrics.annotation.Timed;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.lang.Math.sqrt;

public abstract class AbstractPrimeGeneratorService implements PrimeGeneratorService {
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
                getRangeCandidates(highestPrimeRequired)
                        .parallel()
                        .filter(this::checkIfPrime)
                        .boxed()
                        .collect(Collectors.toList()), forkJoinPool);
        return primes;
    }

    /**
     * Gets a long stream of prime candidates
     *
     * @param highestPrimeRequired
     * @return
     */
    protected abstract LongStream getRangeCandidates(long highestPrimeRequired);

    /**
     * check if the candidate number is prime
     *
     * @param candidatePrime
     * @return
     */
    private boolean checkIfPrime(long candidatePrime) {
        return getRangeCandidates((long) sqrt(candidatePrime))
                        .noneMatch(divisor -> candidatePrime % divisor == 0);
    }

}
