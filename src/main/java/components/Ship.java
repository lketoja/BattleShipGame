package components;

import java.io.Serializable;
import java.util.List;

import helpers.Coordinate;

public class Ship implements Serializable{
	
	public final String name;
	public final int length;
	private List<Coordinate> location;
	boolean sunken;
	
	public boolean isSunken() {
		return sunken;
	}

	public void setSunken(boolean sunken) {
		this.sunken = sunken;
	}

	public Ship(String name, int length) {
		this.name=name;
		this.length=length;
	}

	public List<Coordinate> getLocation() {
		return location;
	}

	public void setLocation(List<Coordinate> location) {
		this.location = location;
	}

	

}
