package br.com.releasemanger.version.resource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import br.com.releasemanger.version.model.entity.Version;
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

@Slf4j
@Path("products/{product-id}")
public class VersionResource {

	@Inject
	private VersionService versionService;

	/**
	 * User story US-007, in which the end user, of a software house, wants to list
	 * new versions of the software, throughout its own software upgrade tool.
	 * 
	 * @param productId
	 * @return
	 */
	@Path("versions")
	@GET
	public List<Version> getProductVersions(@PathParam("product-id") Long productId) {
		return versionService.getVersionsByProduct(productId);
	}

	/**
	 * User story US-007, in which the end user, of a software house, wants to
	 * download the new version, throughout its own software upgrade tool.
	 * 
	 * @param versionId
	 * @return
	 */
	@Path("{versionId}/artifact")
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public File downloadVersion(@PathParam("versionId") Long versionId) {
		return versionService.getFile(versionId);
	}

	/**
	 * User story US-004, in which a Continuous Delivery tools like Jenkins, upload
	 * the software artifact, setting the version number according to its DevOps
	 * (SDLC) flow.
	 * 
	 * @param newVersionDTO
	 * @return
	 * @throws IOException
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadNewVersion(NewVersionInputDTO newVersionDTO) throws IOException {
		return Response.ok(this.versionService.publishNewVersion(newVersionDTO))
				.build();
	}

	/**
	 * User story US-014, in which the user wants to set the version status, release
	 * notes and prerequisites
	 * 
	 * @param changeVersionDTO
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changeVersionStatus(ChangeVersionDTO changeVersionDTO) {
		log.info("changeVersionStatus");
		return Response.ok(versionService.changeVersion(changeVersionDTO)).build();
	}
}
