package server.response;

import server.Constants;
import server.Resource;
import server.Utils;
import server.request.Request;

public class NoContent extends Response {

	public NoContent(Request request, Resource resource) {
		super(request, resource);
	}

	@Override
	public int getCode() {
		return 204;
	}

	@Override
	public String getReasonPhrase() {
		return ResponseCode._204.getResponseCodeStr();
	}

	@Override
	public String getHeaders() {
		headers.put(Constants.CONTENT_LENGTH, String.valueOf(getBody().length()));
		return Utils.getHeaderString(headers);
	}

	@Override
	public String getBody() {
		return "<h1>204</h1><h2>No Content</h2>";
	}
}
