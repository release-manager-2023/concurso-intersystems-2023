package br.com.releasemanger.version.resource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import br.com.releasemanger.version.model.entity.Version;
import br.com.releasemanger.version.model.vo.ChangeVersionDTO;
import br.com.releasemanger.version.model.vo.DownloadVersion;
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

@Path("version")
@Slf4j
public class VersionResource {

	@Inject
	private VersionService versionService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Version> getVersion() {
		return versionService.listAllVersions();
	}

	@Path("publish")
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

	@Path("{versionId}/download")
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public File download(@PathParam("versionId") Long versionId) {
		DownloadVersion downloadVersion = new DownloadVersion(versionId, "higorrg");
		return versionService.getFile(downloadVersion);
	}
}

