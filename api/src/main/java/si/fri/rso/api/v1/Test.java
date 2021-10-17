package si.fri.rso.api.v1;

import si.fri.rso.services.UsersBean;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api")
public class Test extends HttpServlet {

    @Inject
    UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) {
        try {
            res.getWriter().write("OK");
            res.getWriter().write(Integer.toString(usersBean.getUser(1).getAge()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
