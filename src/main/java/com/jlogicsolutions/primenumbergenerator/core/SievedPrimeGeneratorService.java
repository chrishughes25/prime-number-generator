package com.jlogicsolutions.primenumbergenerator.core;


import java.util.stream.LongStream;

import static java.util.stream.LongStream.rangeClosed;

/**
 * Sieved prime number generator removes multiples of common primes to reduce the number of candidates
 */
public class SievedPrimeGeneratorService extends AbstractPrimeGeneratorService {

    /**
     * Sieved implementation, optimisation to reduce candidates by removing multiples of most common primes
     * @param upperLimit stop generating at this point
     * @return LongStream of range candidates
     */
    protected LongStream getRangeCandidates(long upperLimit) {
        LongStream longStream = rangeClosed(2, upperLimit)
                .filter(i -> (i <= 2 || i % 2 > 0))
                .filter(i -> (i <= 3 || i % 3 > 0))
                .filter(i -> (i <= 5 || i % 5 > 0))
                .filter(i -> (i <= 7 || i % 7 > 0))
                .filter(i -> (i <= 11 || i % 11 > 0));
        return longStream;
    }
}
