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
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@Named
@Data
public class IdentityUtils {
    Logger logger = Logger.getLogger(getClass().getName());
    private String password;
    private String username;

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

    public String logIn(String username, String password) throws ServletException {
        try {
            request.login(username, password);
            if (isAuthenticated()) {
                loginAccomplishedLog();
                return "index";
            }
        }
        catch(ServletException ex) {
            loginErrorLog();
        }
        return "Error";
    }

    public String logout() throws ServletException {
        request.logout();
        logger.log(Level.INFO, "Logging out accomplished");
        return "index";
    }

    public void loginErrorLog() {
        logger.log(Level.WARNING, "Error in logging in");
    }

    public void loginAccomplishedLog() {
        logger.log(Level.INFO, "Logging in accomplished for " + request.getUserPrincipal().getName());
    }


    public boolean isAuthenticated() {
        return request.getUserPrincipal() != null;
    }
}
