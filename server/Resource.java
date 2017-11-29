package server;

import java.io.File;
import java.net.URL;
import java.util.Map;

import server.conf.HttpdConf;

public class Resource {

	String uri;
	HttpdConf httpdConf;

	public Resource(String uri, HttpdConf config) {
		this.uri = uri;
		this.httpdConf = config;
		if (this.uri.length() == 1 && this.uri.equals("/"))
			this.uri = Constants.DEFAULT_INDEX_FILE;
	}

	public String getAbsolutePath(boolean isScript) {
		String absolutePath = resolveUriPath(isScript);
		return isFile(absolutePath) ? absolutePath : appendDirIndex(absolutePath);
	}

	public boolean isProtected() {
		URL path = HttpdConf.class.getResource(Constants.HTACCESS_FILE_LOCATION);
		File file = new File(path.getPath());
		return file.exists() ? true : false;
	}

	private boolean isUriAliased() {
		Map<String, String> aliases = httpdConf.getAliases();
		for (String key : aliases.keySet()) {
			if (uri.contains(key))
				return true;
		}
		return false;
	}

	public boolean isUriScriptAliased() {
		Map<String, String> scriptAliases = httpdConf.getScriptAliases();
		for (String key : scriptAliases.keySet()) {
			if (uri.contains(key))
				return true;
		}
		return false;
	}

	public boolean isFile(String path) {
		File file = new File(path);
		if(file.exists()) {
			if(file.isDirectory())
				return false;
			else if(file.isFile())
				return true;
		}
		return false;
	}

	private String resolveUriPath(boolean isScript) {
		String unmodifiedUri = getUnmodifiedUri();
		if(isScript)
			return unmodifiedUri;
		else {
			File file = new File(unmodifiedUri);
			if(file.exists())
				return unmodifiedUri;
			String documentRoot = httpdConf.getDocumentRoot();
			if(unmodifiedUri.startsWith("/") & documentRoot.endsWith("/"))
				return documentRoot + unmodifiedUri.substring(1, uri.length());
			return documentRoot + unmodifiedUri;
		}
	}

	private String getUnmodifiedUri() {
		if (isUriAliased()) {
			Map<String, String> aliases = httpdConf.getAliases();
			for (String key : aliases.keySet()) {
				if (uri.contains(key))
					return uri.replace(key, aliases.get(key));
			}
			return null;
		} else if (isUriScriptAliased()) {
			Map<String, String> scriptAliases = httpdConf.getScriptAliases();
			for (String key : scriptAliases.keySet()) {
				if (uri.contains(key))
					return uri.replace(key, scriptAliases.get(key));
			}
			return null;
		}
		return uri;
	}

	private String appendDirIndex(String path) {
		String content[] = path.split("/");
		String indexFile = httpdConf.getDirectoryIndex();
		content[content.length - 1] = indexFile == null ? Constants.DEFAULT_INDEX_FILE : indexFile;
		StringBuilder builder = new StringBuilder();
		builder.append("/");
		for (String str : content) {
			if (builder.length() > 0)
				builder.append("/");
			builder.append(str);
		}
		return builder.toString();
	}

	public HttpdConf getHttpConf() {
		return httpdConf;
	}
}
