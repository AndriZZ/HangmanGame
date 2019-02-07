package server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = new ServerSocket(5056);

		while (true) {

			Socket socket = null;

			try {
				socket = serverSocket.accept();

				System.out.println("A new client is connected : " + socket);
				System.out.println("Assigning new thread for this client");
				Thread t = new ServerHandler(socket);
				t.start();

			} catch (Exception e) {
				socket.close();
				e.printStackTrace();
				break;
			}
		}
		serverSocket.close();

	}
}
