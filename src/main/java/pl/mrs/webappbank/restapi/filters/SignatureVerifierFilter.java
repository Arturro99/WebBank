package pl.mrs.webappbank.restapi.filters;

import pl.mrs.webappbank.restapi.utils.EntityIdentitySignerVerifier;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@SignatureVerifierFilterBinding
public class SignatureVerifierFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
            String eTagValue = containerRequestContext.getHeaderString("If-Match");
            if(null == eTagValue || eTagValue.isEmpty()) {
                containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
            }
            else if(!EntityIdentitySignerVerifier.verifySignature(eTagValue)) {
                containerRequestContext.abortWith(Response.status(Response.Status.PRECONDITION_FAILED).build());
            }
    }
}
