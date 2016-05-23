package com.jlogicsolutions.primenumbergenerator.resources;

import com.jlogicsolutions.primenumbergenerator.core.BasicPrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.core.EratosthenesSievePrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.core.PrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.core.SievedPrimeGeneratorService;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Path("/primes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.TEXT_PLAIN)
public class PrimeGeneratorResource {
    private final PrimeGeneratorService basicPGS;
    private final SievedPrimeGeneratorService optimisedPGS;
    private final EratosthenesSievePrimeGeneratorService eratosPGS;
    /**
     * for demo purposes we will limit this server to one job at a time
     */
    private  Future<List<Long>> primeResults;

    public PrimeGeneratorResource(BasicPrimeGeneratorService basicPGS, SievedPrimeGeneratorService optimisedPGS, EratosthenesSievePrimeGeneratorService eratosPGS) {
        this.basicPGS = basicPGS;
        this.optimisedPGS = optimisedPGS;
        this.eratosPGS = eratosPGS;
    }

    @GET
    public Response fetchResults() {
        if(primeResults == null){
            throw new WebApplicationException("Number Generation not initiated", Response.Status.FORBIDDEN);
        }
        if(primeResults.isDone()){
            try {
                return Response.ok(primeResults.get()).build();
            } catch (InterruptedException e) {
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            } catch (ExecutionException e) {
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            }finally {
                primeResults = null;
            }
        }
        return Response.accepted().build();
    }

    @POST
    @Path("basic/{upperLimit}")
    public Response startBasicGeneration(@PathParam("upperLimit") LongParam upperLimit) {
        if(primeResults != null){
            throw new WebApplicationException("Number Generation In Progress", Response.Status.FORBIDDEN);
        }
        primeResults = basicPGS.startPrimeGeneration(upperLimit.get());
        return Response.created(UriBuilder.fromResource(PrimeGeneratorResource.class).build())
                .build();
    }

    @POST
    @Path("optimised/{upperLimit}")
    public Response startOptimisedGeneration(@PathParam("upperLimit") LongParam upperLimit) {
        if(primeResults != null){
            throw new WebApplicationException("Number Generation In Progress", Response.Status.FORBIDDEN);
        }
        primeResults = optimisedPGS.startPrimeGeneration(upperLimit.get());
        return Response.created(UriBuilder.fromResource(PrimeGeneratorResource.class).build())
                .build();
    }

    @POST
    @Path("eratosthenes/{upperLimit}")
    public Response startEratosGeneration(@PathParam("upperLimit") LongParam upperLimit) {
        if(primeResults != null){
            throw new WebApplicationException("Number Generation In Progress", Response.Status.FORBIDDEN);
        }
        primeResults = eratosPGS.startPrimeGeneration(upperLimit.get());
        return Response.created(UriBuilder.fromResource(PrimeGeneratorResource.class).build())
                .build();
    }
}


