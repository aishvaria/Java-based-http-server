package server.response;

import server.Resource;
import server.WebServer;
import server.request.Request;

public class ResponseFactory {

	/*
	 * Response codes
	 * 
	 * 200 OK 201 Created 204 No Content 304 Not Modified 400 Bad Request 401
	 * Unauthorized 403 Forbidden 404 Not Found 500 Internal Server Error
	 */

	public Response getResponse(Request request, Resource resource) {
		try {
			if (!validRequest(request))
				return new BadRequest(request, resource);

			// if (!serverAuthenticationAvailable(resource))
			// 	return new NotFound(request, resource);

			if (authenticationRequired(request, resource)) {
				if (!authHeaderAvailable(request))
					return new Unauthorized(request, resource);

				else if (!userAuthenticated(request))
					return new Forbidden(request, resource);
			}

			if (!scriptAliased(resource))
				return handleVerbResponses(request, resource);
			else
				return processScript(request, resource);
		} catch (Exception e) {
			e.printStackTrace();
			return new InternalServerError(request, resource);
		}
	}

	private boolean validRequest(Request request) {
		return request.isValid();
	}

	// private boolean serverAuthenticationAvailable(Resource resource) {
	// 	return resource.isProtected();
	// }

	private boolean authenticationRequired(Request request, Resource resource) {
		String accessFileName = resource.getHttpConf().getAccessFileName();
		if (request.getUri().contains(accessFileName))
			return true;
		return false;
	}

	private boolean authHeaderAvailable(Request request) {
		return request.isAuthHeaderAvailable();
	}

	private boolean userAuthenticated(Request request) {
		String authInfo = request.getAuthInfo();
		return authInfo != null ? WebServer.getHtpassword().isAuthorized(request.getAuthInfo()) : false;
	}

	private boolean scriptAliased(Resource resource) {
		return resource.isUriScriptAliased();
	}

	private Response handleVerbResponses(Request request, Resource resource) {
		switch (request.getVerb()) {
		case GET:
			return handleGet(request, resource);
		case PUT:
			return handlePut(request, resource);
		case DELETE:
			return handleDelete(request, resource);
		case POST:
			return handlePost(request, resource);
		case HEAD:
			return new OK(request, resource);
		default:
			return new InternalServerError(request, resource);
		}
	}

	private Response processScript(Request request, Resource resource) {
		ScriptHandler scriptHandler = new ScriptHandler();
		String response = scriptHandler.executeScript(request, resource);
		if (response == null)
			return new InternalServerError(request, resource);
		else {
			OK OK = new OK(request, resource);
			OK.setScript(true);
			OK.setScriptString(response);
			return OK;
		}
	}

	private Response handleGet(Request request, Resource resource) {
		if (ResponseHelper.needsUpdate(request, resource))
			return handlePost(request, resource);
		return new NotModified(request, resource);
	}

	private Response handlePut(Request request, Resource resource) {
		if (ResponseHelper.createFile())
			return new Created(request, resource);
		return new InternalServerError(request, resource);
	}

	private Response handleDelete(Request request, Resource resource) {
		if (ResponseHelper.deleteFile())
			return new NoContent(request, resource);
		return new InternalServerError(request, resource);
	}

	private Response handlePost(Request request, Resource resource) {
		String path = request.getAbsolutePath();
		if (path == null) {
			path = resource.getAbsolutePath(false);
			request.setAbsolutePath(path);
		}
		if (resource.isFile(path)) {
			OK OK = new OK(request, resource);
			OK.setFile(true);
			OK.setFilePath(path);
			return OK;
		} else
			return new NotFound(request, resource);
	}
}
