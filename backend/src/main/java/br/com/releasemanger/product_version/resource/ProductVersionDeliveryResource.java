package br.com.releasemanger.product_version.resource;

import java.util.List;

import br.com.releasemanger.product_version.model.vo.ProductVersionDeliveryDTO;
import br.com.releasemanger.product_version.service.ProductVersionDeliveryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("products/{product-id}")
public class ProductVersionDeliveryResource {

	@Inject
	private ProductVersionDeliveryService versionService;

	/**
	 * User story US-007, in which the end user, of a software house, wants to list
	 * new versions of the software, throughout its own software upgrade tool.
	 * 
	 * @param productId
	 * @return
	 */
	@Path("versions")
	@GET
	public List<ProductVersionDeliveryDTO> getProductVersions(@PathParam("product-id") Long productId) {
		return versionService.getVersionsByProduct(productId);
	}

}
