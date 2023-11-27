package br.com.releasemanger.version.service;

import java.time.LocalDateTime;
import java.util.List;

import br.com.releasemanger.business_exception.BusinessException;
import br.com.releasemanger.product.model.entity.Product;
import br.com.releasemanger.version.model.entity.Version;
import br.com.releasemanger.version.model.exceptions.MajorVersionCantBePublishedException;
import br.com.releasemanger.version.model.vo.NewVersionDTO;
import br.com.releasemanger.version.model.vo.PublishNewVersionDTO;
import br.com.releasemanger.version.model.vo.VersionLabel;
import jakarta.enterprise.context.Dependent;
import jakarta.transaction.Transactional;

@Dependent
public class VersionService {

	private static final String FAKE_URL = "https://release-manager.io/download/product-id/%s/version/%s";

	public List<Version> listAllVersions() {
		return Version.listAll();
	}

	@Transactional
	public NewVersionDTO publishNewVersion(PublishNewVersionDTO newVersionDTO) {
		validate(newVersionDTO);
		Product product = this.getProduct(newVersionDTO.getProductId());
		VersionLabelStrategy versionLabelStrategy = this
				.getVersionLabelStrategy(newVersionDTO.getVersionLabel());
		versionLabelStrategy.setNewVersion(product);
		product.persist();
		Version version = Version.builder()
				.artifactLocation(FAKE_URL.formatted(product.getId(), product.getVersionString()))
				.username(newVersionDTO.getUsername())
				.versionCreatedTimestamp(LocalDateTime.now())
				.productId(product.getId())
				.build();
		version.setVersionFromProduct(product);
		version.persist();
		NewVersionDTO newVersion = this.buildNewVersion(version);
		return newVersion;
	}

	private NewVersionDTO buildNewVersion(Version version) {
		return NewVersionDTO.builder()
				.version(version.getVersionString())
				.location(version.getArtifactLocation())
				.timestamp(version.getVersionCreatedTimestamp())
				.build();
	}

	private VersionLabelStrategy getVersionLabelStrategy(VersionLabel versionLabel) {
		switch (versionLabel) {
		case MINOR:
			return new VersionLabelStrategyMinor();
		case PATCH:
			return new VersionLabelStrategyPatch();
		case REVISION:
			return new VersionLabelStrategyRevision();
		default:
			throw new MajorVersionCantBePublishedException();
		}
	}

	private Product getProduct(Long productId) {
		return Product.findById(productId);
	}

	private void validate(PublishNewVersionDTO publishNewVersionDTO) {
		if (publishNewVersionDTO.getProductId() == null) {
			throw new BusinessException("ProductId may not be null");
		}
		if (publishNewVersionDTO.getVersionLabel() == null) {
			throw new BusinessException("Version label may not be null");
		}
		if (VersionLabel.MAJOR.equals(publishNewVersionDTO.getVersionLabel())) {
			throw new MajorVersionCantBePublishedException();
		}
	}
}
