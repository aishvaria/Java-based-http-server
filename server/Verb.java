package server;

public enum Verb {

	GET("GET"), PUT("PUT"), DELETE("DELETE"), POST("POST"), HEAD("HEAD"), OPTIONS("OPTIONS"), PATCH("PATCH");

	private String verb;

	Verb(String verb) {
		this.verb = verb;
	}

	public String getVerb() {
		return verb;
	}

	public static boolean isValid(String verb) {
		for (Verb v : Verb.values()) {
			if (verb.equalsIgnoreCase(v.getVerb()))
				return true;
		}
		return false;
	}
}
