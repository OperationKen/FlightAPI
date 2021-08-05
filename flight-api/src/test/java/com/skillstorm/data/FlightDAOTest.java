package com.skillstorm.data;

import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.skillstorm.beans.Flight;

class FlightDAOTest {

	//Use different schema to test table
	static String url = "jdbc:mysql://localhost:3306/flightapijdbctest";
	static String username = "root";
	static String password = "root";
	
	FlightDAO dao = new FlightDAO();
	
	@Test
	public void test() {
		try {
			String sql = "SELECT COUNT(*) FROM flight";
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int rowsBefore = rs.getInt(1);
			
			//dao.create(new Flight(""))
		}catch(Exception e) {
			fail();
		}
	}
	
	@BeforeEach //create
	public void beforeTest() {
		try {
			String ddl = "create table `flightapijdbctest`.`flight` (id INT NOT NULL AUTO_INCREMENT, from_airport VARCHAR(50), to_airport VARCHAR(50), departure VARCHAR(50), arrival VARCHAR(50), flight_number VARCHAR(50), PRIMARY KEY (id) );";
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(ddl);
			System.out.println("Test table created");
			conn.close();
		}catch(Exception e) {
			fail();
		}
	}
	
	@AfterEach //drop
	public void afterTest() {
		try{
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("drop table flight"); //Careful, should assume least privilege... This line is intended for admins. DB user should never drop table.
			System.out.println("Test table dropped");
			conn.close();
		}catch(Exception e) {
			fail();
		}
	}
}