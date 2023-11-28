package br.com.releasemanger.version.resource;

import java.io.File;
import java.io.IOException;

import br.com.releasemanger.version.model.vo.ChangeVersionDTO;
import br.com.releasemanger.version.model.vo.NewVersionInputDTO;
import br.com.releasemanger.version.service.VersionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Path("products/{product-id}/versions")
@Slf4j
public class VersionResource {

	@Inject
	private VersionService versionService;

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(NewVersionInputDTO newVersionDTO) throws IOException {
		return Response.ok(this.versionService.publishNewVersion(newVersionDTO))
				.build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeVersionStatus(ChangeVersionDTO changeVersionDTO) {
		log.info("changeVersionStatus");
		return Response.ok(versionService.changeVersion(changeVersionDTO)).build();
	}

	@Path("{versionId}")
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public File findVersionById(@PathParam("versionId") Long versionId) {
		return versionService.getFile(versionId);
	}
}

