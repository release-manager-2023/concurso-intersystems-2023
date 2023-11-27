package br.com.releasemanger.version.resource;

import java.util.List;

import br.com.releasemanger.version.model.entity.Version;
import br.com.releasemanger.version.model.vo.NewVersionResponseDTO;
import br.com.releasemanger.version.model.vo.PublishNewVersionDTO;
import br.com.releasemanger.version.service.VersionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("publish-version")
public class VersionResource {

	@Inject
	private VersionService versionService;

	@GET
	public List<Version> getVersion() {
		return versionService.listAllVersions();
	}

	@POST
	public Response publishNewVersion(PublishNewVersionDTO publishNewVersionDTO) {
		NewVersionResponseDTO newVersionResponse = versionService.publishNewVersion(publishNewVersionDTO);
		return Response.status(Status.CREATED).entity(newVersionResponse).build();
	}
}
