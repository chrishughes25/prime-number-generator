package com.jlogicsolutions.primenumbergenerator;

import com.jlogicsolutions.primenumbergenerator.core.BasicPrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.resources.BasicPrimeGeneratorResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class pngwebApplication extends Application<pngwebConfiguration> {

    public static void main(final String[] args) throws Exception {
        new pngwebApplication().run(args);
    }

    @Override
    public String getName() {
        return "pngweb";
    }

    @Override
    public void initialize(final Bootstrap<pngwebConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final pngwebConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new BasicPrimeGeneratorResource(new BasicPrimeGeneratorService()));
    }

}
