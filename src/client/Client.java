package client;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import server.Alternator;
import server.Client2Server;
import server.ResponseState;

public class Client {

	public static void main(String[] args) throws IOException {

		InetAddress ip = InetAddress.getByName("127.0.0.1");
		Socket socket = new Socket(ip, 5056);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		Alternator alternator = new Alternator();
		String word;

		System.out.println("Welcome to Hangman, try your luck!");
		System.out.println("Do you want to play?");
		if (buffer.read() != 'y') {
			socket.close();
		}
		while (true) {

			try {
				Client2Server client2server = new Client2Server();
				String input = buffer.readLine();
				client2server.setclientword(input);
				out.writeObject(client2server);

				ResponseState server2client = (ResponseState) in.readObject();

				// if (server2client.getState() == ResponseState.State.PICKCATEGORY) {
				if (server2client.getDashedWord() == null) {
					// getMessage should be category ->!
					System.out.println(server2client.getMessage());
					if (server2client.getMessage().equals("You win!!!")
							|| server2client.getMessage().equals("You lost!!!")) {
						System.out.println("Do you want to continue?");
						char readChar = (char) buffer.read();
						if (readChar != 'y') {
							break;
						}
					}

				} else if (server2client.getMessage() != null && !server2client.getMessage().isEmpty()) {
					if (server2client.getMessage().equals("Invalid dictionary")) {
						System.out.println(server2client.getMessage());
						break;
					} else {
						word = alternator.makeStringSpreadbySpaces(server2client.getDashedWord());

						System.out.println("Attempts: " + server2client.getFailAttempts());
						System.out.println(server2client.getMessage());
						System.out.println(word);
						System.out.println("score: " + server2client.getScore());

					}
				} else {

					System.out.println("Input character: ");
				}
			} catch (Throwable ex) {
				System.err.println("Uncaught exception - " + ex.getMessage());
				ex.printStackTrace(System.err);
				break;
			}

		}
		socket.close();

	}

}
