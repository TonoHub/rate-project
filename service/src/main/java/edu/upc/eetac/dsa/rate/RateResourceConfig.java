package edu.upc.eetac.dsa.rate;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by tono on 05/10/2015.
 */
public class RateResourceConfig extends ResourceConfig {
    public RateResourceConfig() {
        packages("edu.upc.eetac.dsa.rate");
        packages("edu.upc.eetac.dsa.rate.auth");
        packages("edu.upc.eetac.dsa.rate.cors");
        register(RolesAllowedDynamicFeature.class);
        register(DeclarativeLinkingFeature.class);
    }
}
