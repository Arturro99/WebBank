package pl.mrs.webappbank.web;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.websocket.OnOpen;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Named
@ManagedBean
@SessionScoped
public class InternationalizationController implements Serializable {
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    private static Map<String,Object> countries;
    static {
        countries = new LinkedHashMap<String, Object>();
        countries.put("en", Locale.ENGLISH);
        countries.put("pl", new Locale("pl"));
    }
    private final static String[] langs = {"en", "pl"};

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void changeLanguage() {
        if(locale.getLanguage().equals(langs[0]))
            locale = new Locale(langs[1]);
        else
            locale = new Locale(langs[0]);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
    public void onLoad(){
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
