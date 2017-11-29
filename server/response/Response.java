package server.response;

import java.util.HashMap;
import java.util.Map;

import server.Constants;
import server.Resource;
import server.Utils;
import server.request.Request;

public class Response implements IResponse {

	int code;
	String reasonPhrase;
	Map<String, String> headers;
	Request request;
	
	public Response(Request request, Resource resource) {
		this.request = request;
		if (headers == null)
			headers = new HashMap<String, String>();
		
		headers.put(Constants.DATE, Utils.getDate());
		headers.put(Constants.SERVER, Constants.SERVER_NAME);
		headers.put(Constants.CONTENT_TYPE, ResponseHelper.getContentType(request));
		// headers.put(Constants.CONTENT_TYPE, request.getContentType() != null ? request.getContentType() : Constants.HTML_MIME_TYPE);
	}

	public String writeString() {
		return getStatusLine() + getHeaders() + getBody();
	}

	@Override
	public int getCode() {
		return 500;
	}

	@Override
	public String getReasonPhrase() {
		return ResponseCode._500.getResponseCodeStr();
	}

	@Override
	public String getHeaders() {
		headers.put(Constants.CONTENT_LENGTH, String.valueOf(getBody().length()));
		return Utils.getHeaderString(headers);
	}

	@Override
	public String getBody() {
		return "<h1>500</h1><h2>Internal Server Error</h2>";
	}

	public String getStatusLine() {
		return request.getHttpVersion() + " " + getCode() + " " + getReasonPhrase() + "\n";
	}

	@Override
	public byte[] getData() {
		return null;
	}

	@Override
	public boolean isFile() {
		return false;
	}
}
