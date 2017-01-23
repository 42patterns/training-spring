package com.example.servlet;

import org.springframework.web.context.support.HttpRequestHandlerServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/", name = "myRequestHandler")
public class AppServlet extends HttpRequestHandlerServlet {
}
