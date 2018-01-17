import java.net.*;
import java.io.*;

public class Worker extends Thread {

	public Socket sock;

	public Worker(Socket s) {
		this.sock = s;
	}

	public void run() {

		PrintStream out;
		BufferedReader in;

		try {
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintStream(sock.getOutputStream());

			try {
				String name;
				name = in.readLine();
				System.out.println("Looking up " + name);
				printRemoteAddress(name, out);
			} catch (IOException e) {
				System.out.println("Server read error");
				e.printStackTrace();
			}

			sock.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	private static void printRemoteAddress(String name, PrintStream out) {
		try {
			out.println("Looking up " + name + "...");
			InetAddress machine = InetAddress.getByName(name);
			out.println("Host name : " + machine.getHostName()); // To client...
			out.println("Host IP : " + toText(machine.getAddress()));
		} catch (UnknownHostException ex) {
			out.println("Failed in atempt to look up " + name);
		}
	}

	private static String toText(byte[] ip) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < ip.length; ++i) {
			if (i > 0)
				result.append(".");
			result.append(0xff & ip[i]);
		}
		return result.toString();
	}
}
