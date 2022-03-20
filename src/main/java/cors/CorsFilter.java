package cors;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    private static boolean preFlightRequest(ContainerRequestContext containerRequestContext){
        return containerRequestContext.getHeaderString("Origin") !=null && containerRequestContext.getMethod().equalsIgnoreCase("OPTIONS");
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext,
                       ContainerResponseContext containerResponseContext) throws IOException {
        //This one must always be present
        containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        containerRequestContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        if (containerRequestContext.getHeaderString("Origin") == null){
            return;
        }
        if (preFlightRequest(containerRequestContext)) {
            containerResponseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
            containerResponseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            // Whatever other non-standard/safe headers (see list above)
            // you want the client to be able to send to the server,
            // put it in this list (v:). And remove the ones you don't want.
            containerResponseContext.getHeaders().add("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Authorization, x-access-token");

        }


    }
}

