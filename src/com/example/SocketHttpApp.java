package com.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketHttpApp {

	public static void main(String[] args) {
		Socket httpSocket = null;
		BufferedWriter os = null;
		BufferedReader is = null;
		
		try {
			httpSocket = new Socket("yahoo.co.jp", 80);	// 1
			is = new BufferedReader(new InputStreamReader(httpSocket.getInputStream()));	// 2
			os = new BufferedWriter(new OutputStreamWriter(httpSocket.getOutputStream()));	// 2
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: hostname");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: hostname");
		}
		
		if (httpSocket != null && os != null && is != null) {
			try {
				os.write("GET / HTTP/1.1\n");		// 3
				os.write("host: yahoo.co.jp\n");	// 3
				os.write("\n");						// 3
				os.flush();
				
				String responseLine;
				while ((responseLine = is.readLine()) != null) {	// 4
					System.out.println(responseLine);
				}
				os.close();
				is.close();
				httpSocket.close();
			} catch (UnknownHostException e) {
				System.err.println("Trying to connect to unknown host: " + e);
			} catch (IOException e) {
				System.err.println("IOException: " + e);
			}
		}
	}

}
