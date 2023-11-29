package br.com.releasemanger.product_version.service;

import br.com.releasemanger.product_version.model.entity.ProductVersionDelivery;
import br.com.releasemanger.product_version.model.vo.ChangeProductVersionDeliveryDTO;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class ProductVersionDeliveryPromoteService {

	@Inject
	private VersionStatusNotificationService publishVersionStatusNotification;

	@Transactional
	public ProductVersionDelivery promoteVersion(ChangeProductVersionDeliveryDTO changeVersionStatusDTO) {
		ProductVersionDelivery version = ProductVersionDelivery.findById(changeVersionStatusDTO.versionId());
		version.setVersionStatus(changeVersionStatusDTO.versionStatus());
		version.setReleaseNotes(changeVersionStatusDTO.releaseNotes());
		version.setPrerequisite(changeVersionStatusDTO.prerequisite());
		version.persist();
		this.publishVersionStatusNotification.publish(version);
		return version;
	}

}
