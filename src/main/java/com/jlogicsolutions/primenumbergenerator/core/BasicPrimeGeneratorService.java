package com.jlogicsolutions.primenumbergenerator.core;


import java.util.stream.LongStream;

import static java.util.stream.LongStream.rangeClosed;

/**
 * basic prime number generator
 */
public class BasicPrimeGeneratorService extends AbstractPrimeGeneratorService {

    /**
     * basic implementation gets a list of all numbers up to the given limit
     * @param upperLimit of prime range
     * @return LongStream of range candidates
     */
    protected LongStream getRangeCandidates(long upperLimit) {
        return rangeClosed(2, upperLimit);
    }
}
