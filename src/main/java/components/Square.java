package components;

import java.io.Serializable;

public class Square implements Serializable{
	
	private Ship ship;
	private boolean shot;
	
	public Ship getShip() {
		return ship;
	}
	public boolean isShot() {
		return shot;
	}
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	public void setShot(boolean shot) {
		this.shot = shot;
	}
	
	

}
