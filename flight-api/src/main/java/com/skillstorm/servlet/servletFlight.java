package com.skillstorm.servlet;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.beans.Flight;
import com.skillstorm.data.FlightDAO;

@WebServlet(name="flight-Servlet", urlPatterns = "/flight")
public class servletFlight extends HttpServlet{

	private CopyOnWriteArrayList<Flight> flightList = new CopyOnWriteArrayList<>();
	
	FlightDAO dao = new FlightDAO();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("id") != null) {
			String param = req.getParameter("id");
			int id = Integer.parseInt(param);
			Flight flight = dao.findByID(id);
			String json = new ObjectMapper().writeValueAsString(flight);
			System.out.println(json);
			resp.getWriter().print(json);
		}
		else {
			Set<Flight> flights = dao.findAll();
			String json = new ObjectMapper().writeValueAsString(flights);
			resp.getWriter().print(json);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream requestBody = req.getInputStream();
		Flight flight = new ObjectMapper().readValue(requestBody, Flight.class);
		System.out.println(flight);
		try {
			Flight updated = dao.create(flight);
			resp.getWriter().print(new ObjectMapper().writeValueAsString(updated));
			resp.setStatus(201);
			resp.setContentType("application/json");
		}catch(SQLException e) {
			e.printStackTrace();
			resp.getWriter().print(new Flight());
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Flight flightDel = new ObjectMapper().readValue(req.getReader(), Flight.class);
		dao.delete(flightDel.getId());
		
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Put this");
		ObjectMapper mapper = new ObjectMapper();
		Flight flight = mapper.readValue(req.getInputStream(), Flight.class);
		System.out.println(flight);
		if(dao.update(flight)) {
			resp.setStatus(201);
			System.out.println("updated");
		}
		else {
			resp.setStatus(500);
		}
	}
}
