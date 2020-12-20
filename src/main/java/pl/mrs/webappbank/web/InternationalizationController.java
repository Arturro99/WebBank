package pl.mrs.webappbank.web;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named
@ManagedBean
@SessionScoped
public class InternationalizationController implements Serializable {
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    private final static String[] langs = {"en", "pl"};

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }


    public String changeLanguage() {
        if(locale.getLanguage().equals(langs[0]))
            locale = new Locale(langs[1]);
        else
            locale = new Locale(langs[0]);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        return "index";
    }
}
