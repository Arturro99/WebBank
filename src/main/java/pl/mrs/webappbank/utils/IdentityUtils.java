package pl.mrs.webappbank.utils;

import lombok.Data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.NoSuchElementException;
import java.util.Optional;

@ApplicationScoped
@Named
@Data
public class IdentityUtils {
    @Inject
    private HttpServletRequest request;

    public String getLogin() {
        Principal principal;
        try {
            principal = Optional.ofNullable(request.getUserPrincipal()).orElseThrow(NoSuchElementException::new);
        }
        catch (NoSuchElementException ex) {
            return "";
        }
        return principal.getName();
    }

    public void login(String username, String password) throws ServletException {
        request.login(username, password);
    }

    public String logout() throws ServletException {
        request.logout();
        return "index";
    }

    public boolean isAuthenticated() {
        return request.getUserPrincipal() != null;
    }
}
