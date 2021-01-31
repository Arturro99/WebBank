package pl.mrs.webappbank.restapi.seurity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("authenticate")
public class AuthenticationService {

    public AuthenticationService() {
    }

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response authenticate(@NotNull LoginData loginData) {
        Credential credential = new UsernamePasswordCredential(loginData.getLogin(), loginData.getPassword());
        CredentialValidationResult result = identityStoreHandler.validate(credential);
        if (result.getStatus() == CredentialValidationResult.Status.VALID) {
            return Response.accepted()
                    .type("application/jwt")
                    .entity(JWTGeneratorVerifier.generateJWTString(result))
                    .build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/refresh")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public Response refreshToken(@HeaderParam("Authentication") @NotNull @NotEmpty String auth) {
        String jwtSerializedToken = auth.substring(("Bearer".length())).trim();
        return Response.accepted()
                .type("application/jwt")
                .entity(JWTGeneratorVerifier.refreshJWT(jwtSerializedToken))
                .build();
    }

    @Data
    @NoArgsConstructor
    public static class LoginData {
        private String login;
        private String password;
    }
}
