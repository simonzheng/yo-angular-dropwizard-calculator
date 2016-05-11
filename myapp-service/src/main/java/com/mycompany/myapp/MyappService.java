package com.mycompany.myapp;

import com.mycompany.myapp.config.*;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Optional;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyappService extends Application<MyappConfiguration> {
    private static final Logger LOG = LoggerFactory.getLogger(MyappService.class);

    public static void main(String[] args) throws Exception {
        new MyappService().run(args);
    }

    private final HibernateBundle<MyappConfiguration> hibernateBundle = new HibernateBundle<MyappConfiguration>(
            
            Void.class
        ) {
        @Override
        public DataSourceFactory getDataSourceFactory(MyappConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public String getName() {
        return "myapp";
    }

    @Override
    public void initialize(Bootstrap<MyappConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/app/", "/", "index.html"));
        bootstrap.addBundle(hibernateBundle);
        ObjectMapper mapper = bootstrap.getObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void run(MyappConfiguration configuration,
                    Environment environment) throws Exception {
        environment.jersey().setUrlPattern("/myapp/*");
        environment.jersey().disable();
        
        
    }
}
