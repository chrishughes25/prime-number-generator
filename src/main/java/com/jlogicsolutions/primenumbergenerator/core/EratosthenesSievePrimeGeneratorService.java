package com.jlogicsolutions.primenumbergenerator.core;


import com.codahale.metrics.annotation.Timed;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.util.stream.LongStream.rangeClosed;

/**
 * Sieved prime number generator removes multiples of common primes to reduce the number of candidates
 */
public class EratosthenesSievePrimeGeneratorService implements PrimeGeneratorService {
    /**
     * starts generating prime numbers upto the given value
     *
     * @param upperLimit stop generating at this point
     * @return <code>Future</code> for <code>List</code> of prime numbers
     */
    @Override
    @Timed
    public Future<List<Long>> startPrimeGeneration(final Long upperLimit) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        return CompletableFuture.supplyAsync(() -> getPrimeList(upperLimit), forkJoinPool);
    }

    private List<Long> getPrimeList(Long upperLimit) {
        LongStream longStream = rangeClosed(2, upperLimit).parallel();
        double maxDivisor = Math.sqrt(upperLimit);
        for(long d = 2; d < maxDivisor; d++){
            long divisor = d;
            longStream = longStream.filter(i -> (i <= divisor || i % divisor > 0));
        }
        return longStream.boxed()
                .collect(Collectors.toList());

    }


}
