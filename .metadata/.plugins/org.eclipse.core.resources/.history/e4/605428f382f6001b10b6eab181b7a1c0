package com.skillstorm.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.skillstorm.beans.Flight;

public class FlightDAO {
	
	//before
	//execute the SQL script
	static String url = "jdbc:mysql://localhost:3306/flightapijdbc";
	static String username = "root";
	static String password = "root";
	private static FlightDAO instance;
	
	public FlightDAO() {
		
	}
	public static FlightDAO getInstance() {
		if(instance == null) {
			instance = new FlightDAO();
		}
		return instance;
	}
	
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Error");
            e.printStackTrace();
        }
    }
    	
	public static Flight create(Flight flight) throws SQLException{
		Connection conn = DriverManager.getConnection(url, username, password);
		try {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO flight(from_airport, to_airport, departure, arrival, flight_number) VALUES(?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, flight.getFromAirport());
			stmt.setString(2, flight.getToAirport());
			stmt.setString(3, flight.getDeparture());
			stmt.setString(4, flight.getArrival());
			stmt.setString(5, flight.getFlightNumber());
			
			stmt.executeUpdate();
			conn.commit();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			flight.setId(id);
		}catch(SQLException e) {
			e.printStackTrace();
			conn.rollback();
		}
		
		return flight;
	}
	
	public static Flight findByID(int id) {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			String sql = "SELECT * FROM flight WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			Flight flight = resultSetFlight(rs);
			return flight;
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Set<Flight> findAll(){
		Set<Flight> flights = new HashSet<>();
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "SELECT * FROM flight";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Flight flight = resultSetFlight(rs);
				flights.add(flight);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return flights;
	}
	
	public static boolean update(Flight flight) {
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "UPDATE flight SET from_airport=?, to_airport=?, departure=?, arrival=?, flight_number=? WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, flight.getFromAirport());
			stmt.setString(2, flight.getToAirport());
			stmt.setString(3, flight.getDeparture());
			stmt.setString(4, flight.getArrival());
			stmt.setString(5, flight.getFlightNumber());
			stmt.setInt(6, flight.getId());
			int rows = stmt.executeUpdate();
			return rows > 0 ? true : false;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void delete(int id) {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
			String deleter = "DELETE FROM flight WHERE id=?";
			PreparedStatement stmt = conn.prepareStatement(deleter);
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private static Flight resultSetFlight(ResultSet rs) {
		try {
			int id = rs.getInt("id");
			String fromAirport = rs.getString("from_airport");
			String toAirport = rs.getString("to_airport");
			String departure = rs.getString("departure");
			String arrival = rs.getString("arrival");
			String flightNumber = rs.getString("flight_number");
			
			Flight flight = new Flight(id, fromAirport, toAirport, departure, arrival, flightNumber);
			return flight;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	//after
	//drop the table
}