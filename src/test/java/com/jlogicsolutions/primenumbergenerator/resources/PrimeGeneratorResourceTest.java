package com.jlogicsolutions.primenumbergenerator.resources;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.jlogicsolutions.primenumbergenerator.core.BasicPrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.core.EratosthenesSievePrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.core.PrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.core.SievedPrimeGeneratorService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class PrimeGeneratorResourceTest {
    private final BasicPrimeGeneratorService basic = mock(BasicPrimeGeneratorService.class);
    private final SievedPrimeGeneratorService optimised = mock(SievedPrimeGeneratorService.class);
    private final EratosthenesSievePrimeGeneratorService eratos = mock(EratosthenesSievePrimeGeneratorService.class);
    public static final String PRIMES = "/primes";
    private ArrayList<Long> expectedPrimes = Lists.newArrayList(2l, 3l, 5l, 7l, 11l);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new PrimeGeneratorResource(basic, optimised, eratos))
            .build();
    public static final Long HIGHEST_PRIME_REQUIRED = 1000l;

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {
        reset(basic);
    }

    @org.junit.Test
    public void fetchResultsWhenGenerationNotStarted() throws Exception {
        try {
            resources.client().target(PRIMES).request().get(List.class);
            fail();
        } catch (ForbiddenException ex) {
            assertThat(ex.getMessage()).isEqualTo("HTTP 403 Forbidden");
        }

    }

    @org.junit.Test
    public void fetchResultsWhenGenerationNotFinished() throws Exception {
        Future future = mock(Future.class);
        when(future.isDone()).thenReturn(false);
        when(basic.startPrimeGeneration(anyLong())).thenReturn(future);
        startGeneration();
        Response response = resources.client().target(PRIMES).request().get();
        assertThat(response.getStatus()).isEqualTo(202);
        assertThat(response.getStatusInfo().getReasonPhrase()).isEqualTo("Accepted");
    }

    @org.junit.Test
    public void fetchResultsWhenGenerationComplete() throws Exception {
        when(basic.startPrimeGeneration(anyLong())).thenReturn(Futures.immediateFuture(expectedPrimes));
        startGeneration();
        List<Integer> results = resources.client().target(PRIMES).request().get(List.class);
        assertThat(results).containsExactly(2, 3, 5, 7, 11);

    }

    @org.junit.Test
    public void startGeneration() throws Exception {
        resources.client().target(PRIMES + "/basic/" + HIGHEST_PRIME_REQUIRED).request().post(Entity.entity(null, MediaType.TEXT_PLAIN_TYPE));
        verify(basic).startPrimeGeneration(HIGHEST_PRIME_REQUIRED);
    }

    @org.junit.Test
    public void startOptimisedGeneration() throws Exception {
        resources.client().target(PRIMES + "/optimised/" + HIGHEST_PRIME_REQUIRED).request().post(Entity.entity(null, MediaType.TEXT_PLAIN_TYPE));
        verify(optimised).startPrimeGeneration(HIGHEST_PRIME_REQUIRED);
    }

    @org.junit.Test
    public void startEratosthenesGeneration() throws Exception {
        resources.client().target(PRIMES + "/eratosthenes/" + HIGHEST_PRIME_REQUIRED).request().post(Entity.entity(null, MediaType.TEXT_PLAIN_TYPE));
        verify(eratos).startPrimeGeneration(HIGHEST_PRIME_REQUIRED);
    }

    @org.junit.Test
    public void startGenerationWhenInProgress() throws Exception {
        when(basic.startPrimeGeneration(anyLong())).thenReturn(Futures.immediateFuture(expectedPrimes));
        startGeneration();
        verifyNoMoreInteractions(basic);
        Response response = resources.client().target(PRIMES + "/basic/" + HIGHEST_PRIME_REQUIRED).request().post(Entity.entity(null, MediaType.TEXT_PLAIN_TYPE));
        assertThat(response.getStatus()).isEqualTo(403);
    }
}