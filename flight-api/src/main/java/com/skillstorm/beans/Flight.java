package com.skillstorm.beans;

import java.sql.Date;
import java.util.Objects;

public class Flight implements Comparable<Flight>{
	
	//Mapping out the database.
	private int id;
	private String fromAirport;
	private String toAirport;
	private String departure;
	private String arrival;
	private String flightNumber;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFromAirport() {
		return fromAirport;
	}

	public void setFromAirport(String fromAirport) {
		this.fromAirport = fromAirport;
	}

	public String getToAirport() {
		return toAirport;
	}

	public void setToAirport(String toAirport) {
		this.toAirport = toAirport;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	//constructor
	public Flight() {

	}
	
	public Flight(int id) {
		super();
		this.id = id;
	}
	
	public Flight(int id, String fromAirport, String toAirport, String departure, String arrival, String flightNumber){
		super();
		this.id = id;
		this.fromAirport = fromAirport;
		this.toAirport = toAirport;
		this.departure = departure;
		this.arrival = arrival;
		this.flightNumber = flightNumber;
	}
	
	public Flight(String fromAirport, String toAirport, String departure, String arrival, String flightNumber){
		super();
		this.fromAirport = fromAirport;
		this.toAirport = toAirport;
		this.departure = departure;
		this.arrival = arrival;
		this.flightNumber = flightNumber;
	}

	@Override
	public String toString() {
		return "Flight [id=" + id + ", fromAirport=" + fromAirport + ", toAirport=" + toAirport + ", departure="
				+ departure + ", arrival=" + arrival + ", flightNumber=" + flightNumber + "]";
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(arrival, departure, flightNumber, fromAirport, id, toAirport);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		return Objects.equals(arrival, other.arrival) && Objects.equals(departure, other.departure)
				&& Objects.equals(flightNumber, other.flightNumber) && Objects.equals(fromAirport, other.fromAirport)
				&& id == other.id && Objects.equals(toAirport, other.toAirport);
	}

	@Override
	public int compareTo(Flight other) {
		// TODO Auto-generated method stub
		return this.id - other.id;
	}
	
	
}
