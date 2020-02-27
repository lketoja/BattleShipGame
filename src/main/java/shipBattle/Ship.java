package shipBattle;

import java.io.Serializable;
import java.util.List;

public class Ship implements Serializable{
	
	public final String name;
	public final int length;
	private List<Square> location;
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

	public List<Square> getLocation() {
		return location;
	}

	public void setLocation(List<Square> location) {
		this.location = location;
	}

	

}
