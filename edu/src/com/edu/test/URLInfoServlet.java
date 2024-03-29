package com.edu.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/urlInfo")
public class URLInfoServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
					throws ServletException, IOException {
		res.setContentType("text/html;charset=EUC-KR");
		PrintWriter out = res.getWriter();
		
		out.print("<html>");
		out.print("<head><title>Request 정보 출력</title></head>");
		
		out.print("<body>");
		out.print("<h3>요청 방식과 프로토콜 정보</h3>");
		out.print("Request URI : " + req.getRequestURI() + "<br />");
		out.print("Request URL : " + req.getRequestURL() + "<br />");
		out.print("Context Path : " + req.getContextPath() + "<br />");
		out.print("Request Protocol : " + req.getProtocol() + "<br />");
		out.print("Servlet Path : " + req.getServletPath() + "<br />");
		out.print("</body></html>");
		
		out.close();
	}
}