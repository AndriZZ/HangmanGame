
package server;

import java.io.Serializable;

public class ServerState implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum State {
		START, PICKCATEGORY, PLAY, GAMEOVER, GAMEWON
	}

	private String dashedWord;
	private int failAttempts;// fail attempt counter
	private int score;// score of the player
	private String message;// replied message to the letter or word that client sends
	private State gameStates;

	public String getDashedWord() {
		return dashedWord;
	}

	public void setDashedWord(String dashedWord) {
		this.dashedWord = dashedWord;
	}

	public State getState() {
		return gameStates;
	}

	public void setState(State state) {
		this.gameStates = state;
	}

	public ServerState() {
		super();
	}

	public ServerState(ServerState sToC) {
		super();
		this.failAttempts = sToC.failAttempts;
		this.score = sToC.score;
		this.message = sToC.message;
		this.dashedWord = sToC.dashedWord;
		this.gameStates = sToC.gameStates;
	}

	public int getFailAttempts() {
		return failAttempts;
	}

	public void setFailAttempts(int FailAttempts) {
		this.failAttempts = FailAttempts;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
