package com.hotel.servlet;

import com.hotel.config.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/testdb")
public class TestDBServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h2>Database Connection Test</h2>");

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            if (conn != null) {
                out.println("<h3 style='color:green'>Connection Successful!</h3>");
                out.println("<p>Connected to: " + conn.getMetaData().getURL() + "</p>");
                conn.close();
            } else {
                out.println("<h3 style='color:red'>Connection Failed: Connection object is null</h3>");
            }
        } catch (Exception e) {
            out.println("<h3 style='color:red'>Connection Error</h3>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }

        out.println("</body></html>");
    }
}
