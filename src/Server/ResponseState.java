package server;

import java.io.Serializable;

public class ResponseState implements Serializable {
	private static final long serialVersionUID = 1L;

	// private static final long serialVersionUID = -7386258182412348165L;

	private String dashedWord;
	private int failAttempts;// fail attempt counter
	private int score;// score of the player
	private String message;// replied message to the letter or word that client sends


	public String getDashedWord() {
		return dashedWord;
	}

	public void setDashedWord(String dashedWord) {
		this.dashedWord = dashedWord;
	}

	public ResponseState() {
		super();
	}

	public ResponseState(ResponseState rState) {
		super();
		this.failAttempts = rState.failAttempts;
		this.score = rState.score;
		this.message = rState.message;
		this.dashedWord = rState.dashedWord;
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

	public ResponseState buildResponse() {
		return new ResponseState(this);
	}
}
