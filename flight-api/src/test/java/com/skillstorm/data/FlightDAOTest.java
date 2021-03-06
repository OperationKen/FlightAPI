package com.skillstorm.data;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.skillstorm.beans.Flight;

public class FlightDAOTest {

	//Use different schema to test table
	static String url = "jdbc:mysql://localhost:3306/flightapijdbc";
	static String username = "root";
	static String password = "root";
	
	FlightDAO dao = new FlightDAO();
	
	@BeforeClass //create
	public static void beforeTest() throws SQLException {
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "create table `flightapijdbc`.`flight` (id INT NOT NULL AUTO_INCREMENT, from_airport VARCHAR(50), to_airport VARCHAR(50), departure VARCHAR(50), arrival VARCHAR(50), flight_number VARCHAR(50), PRIMARY KEY (id) );";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			//Hardcoded for the intention to Retrieve data
			Statement stmt2 = conn.createStatement();
			stmt2.executeUpdate("INSERT INTO flight VALUES(1, 6, 7, 8, 9, 10);");
			Statement stmt3 = conn.createStatement();
			stmt3.executeUpdate("INSERT INTO flight VALUES(2, 6, 7, 8, 9, 10);");
			//Hardcoded for the intention to Delete data
			Statement stmt4 = conn.createStatement();
			stmt4.executeUpdate("INSERT INTO flight VALUES(3, 6, 7, 8, 9, 10);");
			//3 initial database values
		}catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void testCreate() throws SQLException {
		try (Connection conn = DriverManager.getConnection(url, username, password)){
		    String sql = "select count(*) from flight;";  
			Statement stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery(sql);
		    rs.next();
		    FlightDAO.create(new Flight("DFW", "DEN", "2020-05-06", "2020-05-07", "12345-6567"));
		    FlightDAO.create(new Flight("D2W", "DEN", "2020-05-06", "2020-05-07", "12345-6567"));
		    FlightDAO.create(new Flight("D3W", "DEN", "2020-05-06", "2020-05-07", "12345-6567"));
		    ResultSet rs2 = stmt.executeQuery(sql);
		    rs2.next();
		    //Last Row -> Checking if it inserted 3. Table is initially empty, but adding 3 should give us 6 total entries.
		    //Column Index 1 = id -> Checking the 6th id since it is auto-incremented.
		    int lastRow = rs2.getInt(1);
		    assertEquals(lastRow, 6);
		}catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void testRetrieve() throws SQLException{
		try {
		    assertTrue(FlightDAO.findByID(1).getId() == 1);
			
		}catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void testUpdate() throws SQLException{
		try(Connection conn = DriverManager.getConnection(url, username, password)){
		    String sql = "select count(*) from flight;";
		    Statement stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery(sql);
		    rs.next();
		    
		    //Using the last entry to test.
		    int newId = rs.getInt("count(*)");
		    Flight beforeUp = FlightDAO.findByID(newId);
		    FlightDAO.update(new Flight(newId, "KEN", "NEW", "6", "1", "5"));
		    Flight afterUp = FlightDAO.findByID(newId);
		    assertTrue(beforeUp != afterUp);
		    
		}catch(Exception e) {
			
		}
	}
	
	@Test
	public void testDelete() throws SQLException {
		try(Connection conn = DriverManager.getConnection(url, username, password)) {
		    String sql = "select count(*) from flight;";
			Statement stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery(sql);
		    rs.next();
		    //Moves cursor to last row
		    int noDel = rs.getInt("count(*)");
		    FlightDAO.delete(3);
		    ResultSet rs2 = stmt.executeQuery(sql);
		    rs2.next();
		    int delAfter = rs2.getInt("count(*)");
		    //Compare the size after deleting.
		    assertTrue(delAfter < noDel);
		    
		}catch(Exception e) {
			fail();
		}
	}
	
	@AfterClass //drop
	public static void afterTest() {
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "DROP TABLE flight";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.close();
		}catch(Exception e) {
			fail();
		}
	}
}
