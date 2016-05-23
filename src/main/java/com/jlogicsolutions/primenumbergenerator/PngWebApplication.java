package com.jlogicsolutions.primenumbergenerator;

import com.jlogicsolutions.primenumbergenerator.core.BasicPrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.core.EratosthenesSievePrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.core.SievedPrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.resources.PrimeGeneratorResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class PngWebApplication extends Application<PngWebConfiguration> {

    public static void main(final String[] args) throws Exception {
        new PngWebApplication().run(args);
    }

    @Override
    public String getName() {
        return "pngWeb";
    }

    @Override
    public void initialize(final Bootstrap<PngWebConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final PngWebConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(
                new PrimeGeneratorResource(
                        new BasicPrimeGeneratorService(),
                        new SievedPrimeGeneratorService(),
                        new EratosthenesSievePrimeGeneratorService()
                ));
    }

}
