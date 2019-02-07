package server;

import java.io.Serializable;

public class ServerSideInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7600353571804851515L;
	private int games;// total games played
	private String info;// hint message
	private String chosenWord;
	

	public void initialize() {
		info="";
	}
	public String getChosenWord() {
		return chosenWord;
	}
	public void setChosenWord(String chosenWord) {
		this.chosenWord = chosenWord;
	}

	public int getGames() {
		return games;
	}

	public void setGames(int games) {
		this.games = games;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}


}
