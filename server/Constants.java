package server;

public class Constants {

	// file locations
	public static final String HTTPD_CONF_FILE_LOCATION = "httpd.conf";
	public static final String MIME_TYPES_FILE_LOCATION = "mime.types";
	public static final String HTACCESS_FILE_LOCATION = "_.htaccess";
	public static final String HTPASSWORD_FILE_LOCATION = "_.htpasswd";
	public static final String CONFIG_FILES_LOCATION = "conf/";

	// httpd.conf tags
	public static final String SERVER_ROOT_TAG = "ServerRoot";
	public static final String DOCUMENT_ROOT_TAG = "DocumentRoot";
	public static final String PORT_TAG = "Listen";
	public static final String LOG_FILE_TAG = "LogFile";
	public static final String SCRIPT_ALIAS_TAG = "ScriptAlias";
	public static final String ALIAS_TAG = "Alias";
	public static final String ACCESS_FILE_NAME = "AccessFileName";
	public static final String DIRECTORY_INDEX = "DirectoryIndex";
	public static final int DEFAULT_PORT = 8080;

	// _.htaccess tags
	public static final String AUTH_USER_FILE_TAG = "AuthUserFile";
	public static final String AUTH_TYPE_TAG = "AuthType";
	public static final String AUTH_NAME_TAG = "AuthName";
	public static final String REQUIRE_TAG = "Require";

	// header identifiers
	public static final String CONTENT_LENGTH = "Content-Length";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String HTTP_VERSION_1_0 = "HTTP/1.0";
	public static final String HTTP_VERSION_1_1 = "HTTP/1.1";
	public static final String AUTHORIZATION = "Authorization";
	public static final String IF_MODIFIED_SINCE = "If-Modified-Since";
	public static final String DATE = "Date";
	public static final String SERVER = "Server";
	public static final String SERVER_NAME = "Gayatri Aishwarya Web Server 1.0";
	public static final String DEFAULT_MIME_TYPE = "application/octet-stream";
	public static final String LAST_MODIFIED = "Last-Modified";
	public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
	public static final String CACHE_CONTROL = "Cache-Control";
	public static final String NO_CACHE = "no-cache";
	public static final String PRAGMA = "Pragma";
	public static final String SIMPLE_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

	// resource constants
	public static final String DEFAULT_INDEX_FILE = "index.html";
	public static final String LOGGER_NAME = "gayatri-aishwarya-web-server";
	public static final String TEST_FILE_PATH = "src/server/output/test.file";
	
	// CGI
	public static final String USR_PERL_EXTENSION = "#!/usr/bin/perl";
    public static final String PERL_EXTENSION = "#!/bin/perl";
    public static final String BASH_EXTENSION = "#!/bin/bash";
    public static final String SH_EXTENSION = "#!/bin/sh";
    public static final String RUBY_EXTENSION = "rb";
    public static final String PHP_EXTENSION = "php";
}
