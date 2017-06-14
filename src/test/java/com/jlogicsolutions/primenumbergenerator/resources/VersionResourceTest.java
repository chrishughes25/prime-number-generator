package com.jlogicsolutions.primenumbergenerator.resources;

import com.jlogicsolutions.primenumbergenerator.PngWebApplication;
import com.jlogicsolutions.primenumbergenerator.Version;
import io.dropwizard.Configuration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class VersionResourceTest {
    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE =
            new DropwizardAppRule<>(PngWebApplication.class, ResourceHelpers.resourceFilePath("myConfig.yml"));

    @Test
    public void checkBuildInfo() {
        Client client = new JerseyClientBuilder().build();

        Response response = client.target(
                String.format("http://localhost:%d/info", RULE.getLocalPort()))
                .request()
                .get();

        Version version = response.readEntity(Version.class);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(version.getGroupid()).isEqualTo("com.jlogicsolutions.primenumbergenerator");
    }

}