package junit;

import server.Client2Server;
import server.Hangman;
import server.ServerSideInfo;
import server.ServerState;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;

import server.Alternator;
import server.Client2Server;
import server.Hangman;
import server.ServerSideInfo;
import server.DictionaryDealer;
import server.Selector;
public class hangtest {
	

	Client2Server client2Server = new Client2Server();

	ServerSideInfo serverSideInfo = new ServerSideInfo();

	ServerState state = new ServerState();
	
	Hangman hangman= new Hangman();
	@Before
	public void setUp() {

		client2Server.setclientword("");
		serverSideInfo.setChosenWord("Artistic"); 
	}

	@Test
	public void testHangman() throws IOException {

	
	//	List<char[]> words = new ArrayList<char[]>();

		char[] letters = { 'A', 'r', 't', 'i', 's', 't', 'i', 'c' };

		for (int i = 0; i < 8; i++) {
			client2Server.setclientword(client2Server.getclientword() + letters[i]);
			state.setDashedWord(client2Server.getclientword());
			hangman.playGame( client2Server.getclientword(),state, serverSideInfo);

		}
		
		assertEquals(state.getDashedWord(), serverSideInfo.getChosenWord());
	}
	@Test
	public void testCalculateLetter() {
		String chosenWord = "Artistic";
		String clientInput = "A";
		state.setDashedWord("__t_____");
		hangman.calculateLetter(clientInput, state, serverSideInfo);
	assertEquals(state.getDashedWord().charAt(0), chosenWord.charAt(0));
	}

}
