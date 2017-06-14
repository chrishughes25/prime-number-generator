package com.jlogicsolutions.primenumbergenerator.resources;

import com.jlogicsolutions.primenumbergenerator.Version;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/info")
@Produces(MediaType.APPLICATION_JSON)
public class VersionResource {
    @GET
    public Version info() {
        return new Version();
    }
}


