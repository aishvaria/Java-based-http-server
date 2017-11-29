package server.response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import server.Constants;
import server.Resource;
import server.request.Request;

public class ScriptHandler {

	ScriptType scriptType;
	String extension;

	public String executeScript(Request request, Resource resource) {
		String scriptPath = request.getAbsolutePath();
		if (scriptPath == null) {
			scriptPath = resource.getAbsolutePath(true);
			request.setAbsolutePath(scriptPath);
		}

		File file = new File(scriptPath);
		if (!file.exists())
			return null;

		init(scriptPath);

		Process process = null;
		try {
			ProcessBuilder processBuilder;
			if (extension == null) {
				String commands[] = { scriptType.getValue(), scriptPath };
				processBuilder = new ProcessBuilder(commands);
			} else {
				String commands[] = { scriptType.getValue(), extension, scriptPath };
				processBuilder = new ProcessBuilder(commands);
			}
			processBuilder.redirectErrorStream(true);
			process = processBuilder.start();
			process.waitFor();

			return getOutputString(process.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String getOutputString(InputStream in) {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void init(String filePath) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			String line = br.readLine();
			extractScriptType(line);
			if (scriptType == null)
				return;
			getExtension(line);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void extractScriptType(String text) {
		if (text.contains(Constants.PERL_EXTENSION) || text.contains(Constants.USR_PERL_EXTENSION))
			scriptType = ScriptType.PERL;
		else if (text.contains(Constants.BASH_EXTENSION))
			scriptType = ScriptType.BASH;
		else if (text.contains(Constants.SH_EXTENSION))
			scriptType = ScriptType.SH;
	}

	private void getExtension(String text) {
		String content[] = text.split(" ");
		if (content.length > 1)
			extension = content[1];

	}
}

enum ScriptType {
	PERL("Perl"), BASH("/bin/bash"), SH("/bin/sh");

	private String scriptType;

	ScriptType(String scriptType) {
		this.scriptType = scriptType;
	}

	public String getValue() {
		return scriptType;
	}
}
