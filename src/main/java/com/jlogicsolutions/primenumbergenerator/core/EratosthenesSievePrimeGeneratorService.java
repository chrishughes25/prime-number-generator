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
     * @param highestPrimeRequired
     * @return
     */
    @Override
    @Timed
    public Future<List<Long>> startPrimeGeneration(final Long highestPrimeRequired) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        return CompletableFuture.supplyAsync(() -> getPrimeList(highestPrimeRequired), forkJoinPool);
    }

    private List<Long> getPrimeList(Long highestPrimeRequired) {
        LongStream longStream = rangeClosed(2, highestPrimeRequired).parallel();
        double maxDivisor = Math.sqrt(highestPrimeRequired);
        for(long d = 2; d < maxDivisor; d++){
            long divisor = d;
            longStream = longStream.filter(i -> (i <= divisor || i % divisor > 0));
        }
        return longStream.boxed()
                .collect(Collectors.toList());

    }


}
