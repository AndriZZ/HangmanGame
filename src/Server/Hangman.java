
package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

public class Hangman {
	DictionaryClass dictionaryClass = new DictionaryClass();
	private final String userName = "root";
	private final String password = "root";
	private final String serverName = "localhost";
	private final int portNumber = 3306;
	private final String dbName = "java";
	Connection conn = null;
	SQLClass sqlClass = new SQLClass();

	public Connection getConnection() throws SQLException {

		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		conn = DriverManager.getConnection(
				"jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName, connectionProps);

		return conn;
	}

	Hangman() {
		try {
			conn = this.getConnection();
			System.out.println("Connected to DB");
		} catch (SQLException e) {
			System.out.println("Couldn't connect to DB");
		}
	}

	public ServerState determineState(String clientInput, ServerState oldState, ServerSideInfo serverSideInfo)
			throws SQLException {
		ServerState newState = new ServerState();

		if (oldState.getState() == ServerState.State.START) {
			JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet();
			sqlClass.getCategoriesFromDB(rowSet);

			dictionaryClass.setCategories("");
			while (rowSet.next()) {
				dictionaryClass.setCategories(dictionaryClass.getCategories()
						+ (rowSet.getString(1) + ": " + rowSet.getString(2).substring(2) + "\n"));
			}

			newState.setFailAttempts(10);
			newState.setMessage(dictionaryClass.getCategories());
			newState.setState(ServerState.State.PICKCATEGORY);

		} else if (oldState.getState() == ServerState.State.PICKCATEGORY) {

			newState.setState(ServerState.State.PLAY);

			serverSideInfo.setChosenWord(sqlClass.pickWordFromDB(conn, clientInput));

			String dashed = dictionaryClass.dashString(serverSideInfo.getChosenWord());
			newState.setDashedWord(dashed);
			newState.setFailAttempts(oldState.getFailAttempts());
			newState.setMessage(oldState.getMessage());
			newState.setScore(oldState.getScore());
		} else if (oldState.getState() == ServerState.State.PLAY) {
			newState.setState(ServerState.State.PLAY);

			if (serverSideInfo.getInfo().equals("Game won!")) {
				newState.setFailAttempts(oldState.getFailAttempts());
				newState.setScore(oldState.getScore());
				serverSideInfo.setInfo("New game");
				newState.setState(ServerState.State.GAMEWON);
				newState.setMessage(dictionaryClass.getCategories());

			} else if (serverSideInfo.getInfo().equals("Game over!")) {
				newState.setFailAttempts(10);
				newState.setScore(oldState.getScore());
				serverSideInfo.setInfo("New game");
				newState.setState(ServerState.State.GAMEOVER);
				newState.setMessage(dictionaryClass.getCategories());

			} else {
				return oldState;
			}

		}

		return newState;
	}

	public ServerState playGame(String clientInput, ServerState oldState, ServerSideInfo serverSideInfo) {
		ServerState newServerState = new ServerState();
		newServerState.setState(ServerState.State.PLAY);
		newServerState.setFailAttempts(oldState.getFailAttempts());
		newServerState.setScore(oldState.getScore());

		if (clientInput.isEmpty()) {
			newServerState.setMessage("");
			serverSideInfo.setInfo(GameMessages.emptyMessage.getMessage());

		} else if (clientInput.length() == 1) {

			if (serverSideInfo.getChosenWord().toLowerCase().indexOf(clientInput.toLowerCase()) == -1) {
				newServerState.setMessage(GameMessages.letterNotExist.getMessage());
				newServerState.setFailAttempts(oldState.getFailAttempts() - 1);

				serverSideInfo.setInfo(String.valueOf(oldState.getFailAttempts()) + " attempts are left!");
				newServerState.setDashedWord(oldState.getDashedWord());

				if (oldState.getFailAttempts() == 0) {
					serverSideInfo.setInfo(gameOver(newServerState, serverSideInfo));
					newServerState.setScore(0);
				}
			} else {
				newServerState = calculateLetter(clientInput, newServerState, oldState, serverSideInfo);
			}
		}
		return newServerState;
	}

	public ServerState calculateLetter(String clientInput, ServerState newServerState, ServerState oldState,
			ServerSideInfo serverSideInfo) {

		if ((oldState.getDashedWord().toString()).indexOf(clientInput) != -1) {
			newServerState.setMessage(GameMessages.letterAlreadyGuessed.getMessage());
			serverSideInfo.setInfo(GameMessages.tryAgain.getMessage());
			newServerState.setDashedWord(oldState.getDashedWord());

		} else {
			serverSideInfo.setInfo(calculateLetterPresent(clientInput, oldState, serverSideInfo.getChosenWord()));
			
			newServerState.setDashedWord(oldState.getDashedWord());
			newServerState.setMessage(GameMessages.correctLetter.getMessage());
			if (oldState.getDashedWord().indexOf("_") == -1) {// No underscore -> word is whole!
				serverSideInfo.setInfo(calculateWordCorrect(newServerState, oldState));
				serverSideInfo.setGames(serverSideInfo.getGames() + 1);
			}
		}
		return newServerState;
	}

	private String gameOver(ServerState newServerState, ServerSideInfo serverSideInfo) {
		newServerState.setMessage(GameMessages.youLost.getMessage());
		newServerState.setDashedWord(serverSideInfo.getChosenWord()); // server reveals to client the actual word
		serverSideInfo.setGames(serverSideInfo.getGames() + 1);
		newServerState.setScore(0);
		newServerState.setDashedWord(null);
		return GameMessages.gameOver.getMessage();
	}

	private String calculateLetterPresent(String word, ServerState oldState, String chosenWord) {
		String info = "";
		char[] c = wordToCharacterArray(word);
		for (int i = 0; i < chosenWord.length(); i++) {
			if (String.valueOf(chosenWord.charAt(i)).equalsIgnoreCase(String.valueOf(c[0]))) {
				oldState.setDashedWord(replaceUnderscore(oldState.getDashedWord(), c, i));
				info = GameMessages.keepGuessing.getMessage();
			}
		}
		return info;

	}

	private String replaceUnderscore(String word, char[] c, int i) {
		StringBuilder stringBuilder = new StringBuilder(word);
		stringBuilder.setCharAt(i, c[0]);
		word = stringBuilder.toString();
		return word;
	}

	private char[] wordToCharacterArray(String word) {
		char[] c = new char[word.length()];
		c = word.toCharArray();
		return c;
	}

	private String calculateWordCorrect(ServerState newServerState, ServerState oldState) {
		newServerState.setMessage(GameMessages.youWin.getMessage());
		newServerState.setScore(oldState.getScore() + 1);
		newServerState.setDashedWord(null);
		return GameMessages.gameWon.getMessage();
	}

}