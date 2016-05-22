package com.jlogicsolutions.primenumbergenerator.core;


import java.util.List;
import java.util.concurrent.Future;

public interface PrimeGeneratorService {
    Future<List<Long>> startPrimeGeneration(Long highestPrimeRequired);
}
