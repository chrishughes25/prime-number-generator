package com.jlogicsolutions.primenumbergenerator.core;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicPrimeGeneratorServiceTest {

    BasicPrimeGeneratorService basicPrimeGeneratorService = new BasicPrimeGeneratorService();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void checkPrimesToTenCalculated() throws Exception {
        Future<List<Long>> primesList = basicPrimeGeneratorService.startPrimeGeneration(10L);

        assertThat(primesList.get()).containsExactly(2L, 3L, 5L, 7L);
    }

    @Test
    public void startPrimeGeneration() throws Exception {
        Future<List<Long>> primesList = basicPrimeGeneratorService.startPrimeGeneration(50L);

        assertThat(primesList.get()).containsExactly(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L,
                31L, 37L, 41L, 43L, 47L);

    }

}