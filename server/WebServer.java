package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import server.conf.Htaccess;
import server.conf.Htpassword;
import server.conf.HttpdConf;
import server.conf.MimeTypes;

public class WebServer {

	ServerSocket socket;
	
	static HttpdConf configuration;
	static Htaccess htaccess;
	static MimeTypes mimes;
	static Htpassword htpassword;
	
	int clientId = 0;

	public static void main(String[] args) {
		WebServer httpServer = new WebServer();
		httpServer.start();
	}
	
	public void start() {
		configuration = new HttpdConf(Constants.HTTPD_CONF_FILE_LOCATION);
		configuration.load();

		mimes = new MimeTypes(Constants.MIME_TYPES_FILE_LOCATION);
		mimes.load();

		htaccess = new Htaccess(configuration);
		htaccess.load();

		htpassword = new Htpassword();
		htpassword.load(htaccess.getUserFile());
		
		ServerLog serverLog = new ServerLog();
		serverLog.init(configuration.getLogFilePath());
		
		ServerLog.print("Server initialized successfully");
		ServerLog.print("Starting server on port " + configuration.getPort());

		try {
			socket = new ServerSocket(configuration.getPort());
			Socket client = null;
			while (true) {
				client = socket.accept();
				clientId++;
				ServerLog.print("Connected to client " + clientId);
				new Thread(new Worker(clientId, client, configuration, mimes)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
			ServerLog.print("Server error " + e.getMessage());
		}
	}
	
	public static Htaccess getHtaccess() {
		return htaccess;
	}
	
	public static Htpassword getHtpassword() {
		return htpassword;
	}
	
	public static MimeTypes getMimes() {
		return mimes;
	}
}
