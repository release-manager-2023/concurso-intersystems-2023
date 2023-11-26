package br.com.releasemanger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("ping")
public class PingPong {

	@GET
	public String echo() {
		return "pong";
	}
	
}
