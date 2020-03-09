package logic;

import java.io.Serializable;

import components.Player;

public class GameInfo implements Serializable{
	
	
	public final Player player1;
	public final Player player2;
	
	public GameInfo(Player player1, Player player2) {
		super();
		
		this.player1 = player1;
		this.player2 = player2;
	}
}
