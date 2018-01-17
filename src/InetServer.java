import java.io.IOException;
import java.net.*;

public class InetServer {

	public static void main(String[] args) throws IOException {
		
		int q_length = 6;
		//very high port number, so it is unlikely to be occupied. 
		int port = 56000;
		Socket sock;

		//Starting a new socket at port 56000
		@SuppressWarnings("resource")
		ServerSocket servsock = new ServerSocket(port, q_length);

		System.out.println(
				"James Browning's Inet server 1.0 "
				+ "starting up, listening at port " 
				+ port + ".");

		while (true) {
			
			//waits to accept connection from client
			sock = servsock.accept();
			//starts the new worker thread, as discussed in class.
			new Worker(sock).start();
		}
	}
}
