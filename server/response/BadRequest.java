package server.response;

import server.Constants;
import server.Resource;
import server.Utils;
import server.request.Request;

public class BadRequest extends Response {

	public BadRequest(Request request, Resource resource) {
		super(request, resource);
	}

	@Override
	public int getCode() {
		return 400;
	}

	@Override
	public String getReasonPhrase() {
		return ResponseCode._400.getResponseCodeStr();
	}

	@Override
	public String getHeaders() {
		headers.put(Constants.CONTENT_LENGTH, String.valueOf(getBody().length()));
		return Utils.getHeaderString(headers);
	}

	@Override
	public String getBody() {
		return "<h1>400</h1><h2>Bad Request</h2>";
	}
}
