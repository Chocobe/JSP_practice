package com.edu.erorrHandler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/erorrTest1")
public class ErrorTest1Servlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
					throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		String getquery = req.getQueryString();
		out.print("Query : " + getquery + "<br/>");
		out.print("Query 길이 : " + getquery.length() + "<br/>");
		out.print("Done !");
		
		out.close();
	}
}
