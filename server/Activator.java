package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import server.conf.HttpdConf;
import server.conf.MimeTypes;
import server.request.Request;
import server.response.Response;
import server.response.ResponseFactory;

public class Worker extends Thread {

	Socket client;
	HttpdConf config;
	MimeTypes mimes;

	int clientId;

	public HashMap<String, String> default_headers = new HashMap<String, String>();

	public Worker(int clientId, Socket socket, HttpdConf config, MimeTypes mimes) {
		this.clientId = clientId;
		this.client = socket;
		this.config = config;
		this.mimes = mimes;
	}

	@Override
	public void run() {
		PrintWriter out = null;
		BufferedReader in = null;
		OutputStream outputStream = null;

		try {
			outputStream = client.getOutputStream();
			out = new PrintWriter(outputStream, true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			Request request = new Request(in);
			Resource resource = null;
			if (request.isValid()) {
				ServerLog.print("Request from " + clientId + ":\n" + request.printRequest());
				resource = new Resource(request.getUri(), config);
			}

			ResponseFactory responseFactory = new ResponseFactory();
			Response response = responseFactory.getResponse(request, resource);
			if (response.isFile()) {
				String headers = response.getStatusLine() + response.getHeaders();
				outputStream.write(headers.getBytes());
				outputStream.write(response.getData());
				outputStream.flush();
				ServerLog.print("Response from " + clientId + ":\n" + headers);
			} else {
				String responseStr = response.writeString();
				ServerLog.print("Response from " + clientId + ":\n" + responseStr);
				out.print(responseStr);
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				outputStream.close();
				in.close();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
