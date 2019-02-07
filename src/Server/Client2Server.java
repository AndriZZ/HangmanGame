
package server;

import java.io.Serializable;

public class Client2Server implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clientword;

	public void execute() {
	}

	public void setclientword(String word) {
		clientword = word;
	}

	public String getclientword() {
		return clientword;
	}

}
