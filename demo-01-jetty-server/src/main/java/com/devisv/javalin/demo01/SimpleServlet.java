package com.devisv.javalin.demo01;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleServlet extends HttpServlet {

    public static final String PATCH = "/status";

    private static final AtomicInteger count = new AtomicInteger(0);

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("{ \"status\": \"ok\", \"count\": " + count.incrementAndGet() + "}");
    }
}
