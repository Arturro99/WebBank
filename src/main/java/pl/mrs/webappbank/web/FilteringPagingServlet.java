package pl.mrs.webappbank.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.managers.EventManager;
import pl.mrs.webappbank.model.accounts.CommonAccount;
import pl.mrs.webappbank.model.events.Event;
import pl.mrs.webappbank.model.events.LoansLedger;
import pl.mrs.webappbank.model.events.SafeBoxRent;
import pl.mrs.webappbank.model.resources.Loan;
import pl.mrs.webappbank.model.users.Client;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@WebServlet(name = "PagingServlet", urlPatterns = {"/employee/Paging", "/employee/Filtering"})
@Named
public class FilteringPagingServlet extends HttpServlet {

    private List<Event> filteredEvents;
    private int pageSize = 1;
    private int pageNo = 1;
    private String column = "";
    private String pattern = "";
    @Inject
    EventManager loansLedgerManager;

    private void filterEvents(HttpServletRequest req) {
        if (null != req.getParameter("column")) {
            column = req.getParameter("column");
        }
        if (null != req.getParameter("pattern")) {
            pattern = req.getParameter("pattern");
        }
        if ("".equals(req.getParameter("pattern"))) {
            return;
        }
        switch (column) {
            case "rID":
                filteredEvents = getByResourceId(pattern);
                break;
            case "rDesc":
                filteredEvents = getByResourceDescription(pattern);
                break;
            case "cLog":
                filteredEvents = getByClientLogin(pattern);
                break;
        }
    }

    private List<Event> pageEvents(HttpServletRequest req) {
        List<Event> eventsPage = new ArrayList<>();
        if (null != req.getParameter("pageSize"))
            pageSize = Integer.parseInt(req.getParameter("pageSize"));
        if (null != req.getParameter("pageNo"))
            pageNo = Integer.parseInt(req.getParameter("pageNo"));
        for (int i = (pageNo - 1) * pageSize; i < pageNo * pageSize; i++) {
            try {
                eventsPage.add(filteredEvents.get(i));
            } catch (Exception e) {
                break;
            }
        }
        return eventsPage;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        filteredEvents = loansLedgerManager.getAll();
        filterEvents(req);
        filteredEvents = pageEvents(req);
        resp.setContentType("application/json;charset=UTF-8");
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(filteredEvents);
            System.out.println("ResultingJSONstring = " + json);
            //System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try (PrintWriter out = resp.getWriter()) {
            out.println(json);
        }
    }

    public List<Event> getByResourceId(String uuid) {
        return loansLedgerManager.getAll().stream()
                .filter(x -> x.getResource().getId().toString().toLowerCase().contains(uuid))
                .collect(Collectors.toList());
    }

    public List<Event> getByResourceDescription(String desc) {
        return loansLedgerManager.getAll().stream()
                .filter(x -> x.getResource().getDescription().toLowerCase().contains(desc.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Event> getByAccountNumber(String num) {
        return loansLedgerManager.getAll().stream()
                .filter(x -> x.getClass().equals(LoansLedger.class))
                .map(x -> (LoansLedger) x)
                .filter(x -> x.getAccount().getAccountNumber().contains(num))
                .collect(Collectors.toList());
    }

    public List<Event> getByClientId(String id) {
        return loansLedgerManager.getAll().stream()
                .filter(x -> x.getClass().equals(SafeBoxRent.class))
                .map(x -> (SafeBoxRent) x)
                .filter(x -> x.getClient().getPid().toString().toLowerCase().contains(id.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Event> getByClientLogin(String login) {
        return loansLedgerManager.getAll().stream()
                .filter(x -> x.getClient().getLogin().toLowerCase().contains(login.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void setPageSize(int pageSize) {
        if (pageSize != 0)
            this.pageSize = pageSize;
    }

    public void setPageNo(int pageNo) {
        if (pageNo != 0)
            this.pageNo = pageNo;
    }


}
