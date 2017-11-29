package server.response;

import server.Constants;
import server.Resource;
import server.Utils;
import server.request.Request;

public class NotModified extends Response {

	Request request;
	
	public NotModified(Request request, Resource resource) {
		super(request, resource);
		this.request = request;
	}

	@Override
	public int getCode() {
		return 304;
	}

	@Override
	public String getReasonPhrase() {
		return ResponseCode._304.getResponseCodeStr();
	}

	@Override
	public String getHeaders() {
		headers.put(Constants.LAST_MODIFIED, request.getLastModified());
		headers.put(Constants.CONTENT_LENGTH, String.valueOf(getBody().length()));
		return Utils.getHeaderString(headers);
	}

	@Override
	public String getBody() {
		return "<h1>304</h1><h2>Not Modified</h2>";
	}
}
