package br.com.releasemanger;

import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("echo")
public class Echo {

	@POST
	public JsonObject echo(JsonObject json) {
		return json;
	}
}
