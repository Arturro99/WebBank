package pl.mrs.webappbank.web;

import lombok.Data;
import pl.mrs.webappbank.utils.IdentityUtils;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
@Data
public class AuthenticationController implements Serializable {

    @Inject
    IdentityUtils identityUtils;


}
