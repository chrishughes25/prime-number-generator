package com.jlogicsolutions.primenumbergenerator;

import com.jlogicsolutions.primenumbergenerator.core.BasicPrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.core.EratosthenesSievePrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.core.SievedPrimeGeneratorService;
import com.jlogicsolutions.primenumbergenerator.resources.PrimeGeneratorResource;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.servlets.tasks.GarbageCollectionTask;
import io.dropwizard.setup.Environment;

public class PngWebApplication extends Application<Configuration> {

    public static void main(final String[] args) throws Exception {
        new PngWebApplication().run(args);
    }

    @Override
    public String getName() {
        return "pngWeb";
    }

    @Override
    public void run(final Configuration configuration,
                    final Environment environment) {
        environment.jersey().register(
                new PrimeGeneratorResource(
                        new BasicPrimeGeneratorService(),
                        new SievedPrimeGeneratorService(),
                        new EratosthenesSievePrimeGeneratorService()
                ));

        environment.admin().addTask(new GarbageCollectionTask());
    }

}
