package com.jlogicsolutions.primenumbergenerator.resources;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.jlogicsolutions.primenumbergenerator.core.BasicPrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.core.PrimeGeneratorService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class BasicPrimeGeneratorResourceTest {
    private static final PrimeGeneratorService service = mock(BasicPrimeGeneratorService.class);
    private ArrayList<Long> expectedPrimes = Lists.newArrayList(2l, 3l, 5l, 7l, 11l);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new BasicPrimeGeneratorResource(service))
            .build();
    public static final Long HIGHEST_PRIME_REQUIRED = 1000l;

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {
        reset(service);
    }

    @org.junit.Test
    public void fetchResultsWhenGenerationNotStarted() throws Exception {
        try {
            resources.client().target("/generate").request().get(List.class);
            fail();
        }catch (ForbiddenException ex){
            assertThat(ex.getMessage()).isEqualTo("HTTP 403 Forbidden");
        }

    }

    @org.junit.Test
    public void fetchResultsWhenGenerationNotFinished() throws Exception {
        Future future = mock(Future.class);
        when(future.isDone()).thenReturn(false);
        when(service.startPrimeGeneration(anyLong())).thenReturn(future);
        startGeneration();
        try {
            resources.client().target("/generate").request().get(List.class);
            fail();
        }catch (NotFoundException ex){
            assertThat(ex.getMessage()).isEqualTo("HTTP 404 Not Found");
        }

    }

    @org.junit.Test
    public void fetchResultsWhenGenerationComplete() throws Exception {
        when(service.startPrimeGeneration(anyLong())).thenReturn(Futures.immediateFuture(expectedPrimes));
        startGeneration();
        List<Integer> results = resources.client().target("/generate").request().get(List.class);
        assertThat(results).containsExactly(2, 3, 5, 7, 11);

    }

    @org.junit.Test
    public void startGeneration() throws Exception {
        resources.client().target("/generate/"+HIGHEST_PRIME_REQUIRED).request().post(Entity.entity(null, MediaType.TEXT_PLAIN_TYPE));
        verify(service).startPrimeGeneration(HIGHEST_PRIME_REQUIRED);
    }
}