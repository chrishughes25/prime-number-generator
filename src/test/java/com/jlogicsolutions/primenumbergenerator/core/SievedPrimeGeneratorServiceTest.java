package com.jlogicsolutions.primenumbergenerator.core;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SievedPrimeGeneratorServiceTest {

    SievedPrimeGeneratorService primeGeneratorService = new SievedPrimeGeneratorService();

    final Long[] primesTo50 = {2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L,
            31L, 37L, 41L, 43L, 47L};

    @Test
    public void checkPrimesToTenCalculated() throws Exception {
        Future<List<Long>> primesList = primeGeneratorService.startPrimeGeneration(10L);
        assertThat(primesList.get()).containsExactly(2L, 3L, 5L, 7L);
    }

    @Test
    public void startPrimeGeneration() throws Exception {
        Future<List<Long>> primesList = primeGeneratorService.startPrimeGeneration(50L);
        assertThat(primesList.get()).containsExactly(primesTo50);
    }

    @Test
    public void checkRangeCandidatesAreSieved(){
        LongStream rangeCandidates = primeGeneratorService.getRangeCandidates(50L);
        assertThat(rangeCandidates.boxed().collect(Collectors.toList())).containsExactly(primesTo50);
    }

}