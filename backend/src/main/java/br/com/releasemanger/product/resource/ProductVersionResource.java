package br.com.releasemanger.product.resource;

import java.util.List;

import br.com.releasemanger.version.model.entity.Version;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("products/{product-id}")
public class ProductVersionResource {

	@Path("versions")
	@GET
	public List<Version> getProductVersions(@PathParam("product-id") Long productId) {
		return Version.list("productId = ?1", productId);
	}

	@Path("versions/{version-id}")
	@GET
	public Version getVersionById(@PathParam("version-id") Long versionId) {
		return Version.findById(versionId);
	}

}
