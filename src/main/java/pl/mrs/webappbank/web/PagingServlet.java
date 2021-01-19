package pl.mrs.webappbank.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.mrs.webappbank.managers.ClientManager;
import pl.mrs.webappbank.model.users.Client;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet(name = "PagingServlet", urlPatterns = {"/employee/Paging"})
public class PagingServlet extends HttpServlet {

    private List<Client> currentClients;
    @Inject
    ClientManager clientManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        currentClients = clientManager.getAllClients();
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        int pageNo = Integer.parseInt(req.getParameter("pageNo"));
        List<Client> filteredClients = new ArrayList<>();
        for(int i= (pageNo-1 ) * pageSize; i< pageNo * pageSize; i++)
        {
            try {
                filteredClients.add(currentClients.get(i));
            }
            catch (Exception e) {
                break;
            }
        }
        resp.setContentType("application/json;charset=UTF-8");
        String json = "";
            ObjectMapper mapper = new ObjectMapper();
            try {
                json = mapper.writeValueAsString(filteredClients);
                System.out.println("ResultingJSONstring = " + json);
                //System.out.println(json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        try(PrintWriter out = resp.getWriter()) {
            out.println(json);
        }
    }
}
