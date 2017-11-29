package server.response;

public enum ResponseCode {

	_200("OK"), _201("Created"), _204("No Content"), _304("Not Modified"), _400("Bad Request"), _401(
			"Unauthorized"), _403("Forbidden"), _404("Not Found"), _500("Internal Server Error");

	private String responseCode;

	ResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseCodeStr() {
		return responseCode;
	}
}
