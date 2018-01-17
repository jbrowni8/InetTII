import java.io.*;
import java.net.*;

public class InetClient {

	public static void main(String[] args) {
		String serverName;
		if (args.length < 1) {
			serverName = "localhost";
		} else {
			serverName = args[0];
		}

		System.out.println("James Browning's InetClient, 1.0");
		System.out.println("Using server: " + serverName + ", Port: 56000");

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try {
			String name;

			do {
				//get host name (some kind of website address like www.google.com)
				System.out.print("Enter a hostname or an IP address, (quit) to end: ");
				//flush current buffer
				System.out.flush();
				//new hostname, or IP is assigned to name
				name = in.readLine();

				if (name.indexOf("quit") < 0) {
					//while name not quit, get address of hostname or IP address given
					getRemoteAddress(name, serverName);
				}

			} while (name.indexOf("quit") < 0);
			System.out.println("Cancelled by user's request.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//to text method is not used in this program.
	public static String toText(byte[] ip) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < ip.length; ++i) {
			if (i > 0)
				result.append(".");
			result.append(0xff & ip[i]);
		}
		return result.toString();
	}

	/*get remote address method converts the hostname into the ip address.
	* The for loop in the method is responsible for printing out the ip address of the remote address.
	*/
	private static void getRemoteAddress(String name, String serverName) {
		
		Socket sock;
		BufferedReader fromServer;
		PrintStream toServer;
		String textFromServer;

		try {
			sock = new Socket(serverName, 56000);

			//I/O streams for the socket
			fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));			
			toServer = new PrintStream(sock.getOutputStream());
			
			//sends user input to server
			toServer.println(name);
			toServer.flush();

			for (int idx = 1; idx <= 3; idx++) {
				textFromServer = fromServer.readLine();
				if (textFromServer != null) {
					System.out.println(textFromServer);
				}
			}
			//close socket
			sock.close();
		} catch (IOException e) {
			System.out.println("Socket error. ");
			e.printStackTrace();
		}
	}

}
