package br.com.releasemanger.version.resource;

import java.io.IOException;
import java.util.List;

import br.com.releasemanger.version.model.entity.Version;
import br.com.releasemanger.version.model.vo.PublishNewVersionDTO;
import br.com.releasemanger.version.service.VersionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("publish-version")
public class VersionResource {

	@Inject
	private VersionService versionService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Version> getVersion() {
		return versionService.listAllVersions();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(PublishNewVersionDTO newVersionDTO) throws IOException {
		return Response.ok(this.versionService.publishNewVersion(newVersionDTO))
				.build();
	}

}
