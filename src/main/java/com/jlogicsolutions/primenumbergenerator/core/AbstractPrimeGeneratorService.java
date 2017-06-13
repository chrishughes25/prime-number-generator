package com.jlogicsolutions.primenumbergenerator.core;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.lang.Math.sqrt;

public abstract class AbstractPrimeGeneratorService implements PrimeGeneratorService {
    /**
     * starts generating prime numbers up to the given value
     *
     * @param upperLimit stop generating primes at this point
     * @return {@link Future} for {@link List} of prime numbers
     */
    @Override
    public Future<List<Long>> startPrimeGeneration(final Long upperLimit) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        CompletableFuture<List<Long>> primes = CompletableFuture.supplyAsync(() ->
                getRangeCandidates(upperLimit)
                        .parallel()
                        .filter(this::checkIfPrime)
                        .boxed()
                        .collect(Collectors.toList()), forkJoinPool);
        return primes;
    }

    /**
     * Gets a long stream of prime candidates
     *
     * @param upperLimit of prime range
     * @return {@link LongStream} of range candidates
     */
    protected abstract LongStream getRangeCandidates(long upperLimit);

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
