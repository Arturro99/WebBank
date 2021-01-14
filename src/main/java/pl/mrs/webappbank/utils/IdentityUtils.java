package pl.mrs.webappbank.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@ApplicationScoped
@Named
public class IdentityUtils {
    @Inject
    private HttpServletRequest request;

    public String getLogin() {
        return Optional.ofNullable(request.getUserPrincipal().getName()).orElse("");
    }

    public boolean isInAdminRole() {
        return request.isUserInRole("ADMINS");
    }
}
