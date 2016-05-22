package com.jlogicsolutions.primenumbergenerator.resources;

import com.jlogicsolutions.primenumbergenerator.core.PrimeGeneratorService;
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
public class BasicPrimeGeneratorResource {
    private final PrimeGeneratorService primeGeneratorService;
    /**
     * for demo purposes we will limit this server to one job at a time
     */
    private  Future<List<Long>> primeResults;

    public BasicPrimeGeneratorResource(PrimeGeneratorService primeGeneratorService) {
        this.primeGeneratorService = primeGeneratorService;
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
    @Path("{upperLimit}")
    public Response startGeneration(@PathParam("upperLimit") LongParam upperLimit) {
        if(primeResults != null){
            throw new WebApplicationException("Number Generation In Progress", Response.Status.FORBIDDEN);
        }
        primeResults = primeGeneratorService.startPrimeGeneration(upperLimit.get());
        return Response.created(UriBuilder.fromResource(BasicPrimeGeneratorResource.class).build())
                .build();
    }
}


