package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import java.sql.SQLException;

import server.Hangman;
import server.Client2Server;

class ServerHandler extends Thread implements Serializable {

	private static final long serialVersionUID = 1L;
	private Socket clientSocket;
	ServerSideInfo serverSideInfo = new ServerSideInfo();
	Client2Server client2server = new Client2Server();
	Hangman hangman = new Hangman();

	DictionaryClass dictionaryClass = new DictionaryClass();
	ResponseState response = new ResponseState();

	public ServerHandler(Socket socket) throws IOException {

		this.clientSocket = socket;
	}

	@Override
	public void run() {

		ObjectOutputStream out = null;
		ObjectInputStream in = null;

		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();

		}
		serverSideInfo.initialize();
		ServerState state = new ServerState();
		state.setState(ServerState.State.START);

		while (true) {
			try {
				client2server = (Client2Server) in.readObject();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
				break;
			} catch (IOException e1) {
				e1.printStackTrace();
				break;
			}
			ServerState newState = null;
			try {
				newState = hangman.determineState(client2server.getclientword(), state, serverSideInfo);
			} catch (SQLException e) {
				System.out.println("Could not determine state");
			}
			if ((state.getState() == ServerState.State.PLAY) && (newState.getState() == ServerState.State.PLAY)) {
				newState = hangman.playGame(client2server.getclientword(), state, serverSideInfo);
			}
			response = getResponse(newState, state);
			if ((newState.getState() == ServerState.State.GAMEWON)
					|| (newState.getState() == ServerState.State.GAMEOVER)) {
				newState.setState(ServerState.State.PICKCATEGORY);
			}
			state = newState;
			try {
				out.writeObject(response.buildResponse());
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}

		}

	}

	private ResponseState getResponse(ServerState newState, ServerState oldstate) {
		if ((oldstate.getState() == ServerState.State.START)
				&& (newState.getState() == ServerState.State.PICKCATEGORY)) {
			response.setMessage(newState.getMessage());
		}
		if ((oldstate.getState() == ServerState.State.PICKCATEGORY)
				&& (newState.getState() == ServerState.State.PLAY)) {
			response.setDashedWord(newState.getDashedWord());
		}
		if ((oldstate.getState() == ServerState.State.PLAY) && (newState.getState() == ServerState.State.PLAY)) {
			response.setDashedWord(newState.getDashedWord());
			response.setFailAttempts(newState.getFailAttempts());
			response.setScore(newState.getScore());
			response.setMessage(newState.getMessage());
		}

		if ((oldstate.getState() == ServerState.State.PLAY) && (newState.getState() == ServerState.State.GAMEWON)) {

			response.setMessage(newState.getMessage());

		}
		if ((oldstate.getState() == ServerState.State.PLAY) && (newState.getState() == ServerState.State.GAMEOVER)) {

			response.setMessage(newState.getMessage());
			response.setScore(0);
		}

		return response;
	}

}
