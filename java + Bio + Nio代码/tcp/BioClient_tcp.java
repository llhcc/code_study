package com.llh.io.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BioClient_tcp {
	public static void main(String[] args) throws Exception {
		String host = "127.0.0.1";
		int port = 8080;
		Socket socket = new Socket(host, port);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader systemIn = new BufferedReader(new InputStreamReader(
				System.in));
		boolean flag = true;
		while (flag) {
			String command = systemIn.readLine();
			if (command == null || "quit".equalsIgnoreCase(command.trim())) {
				flag = false;
				System.out.println("Client quit!");
				out.println("quit");
				out.close();
				in.close();
				socket.close();
				continue;
			}
			out.println(command);
			String response = in.readLine();
			System.out.println(response);
		}
	}
}
