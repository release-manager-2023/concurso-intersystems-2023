package br.com.releasemanger.version.service;

import java.time.LocalDateTime;
import java.util.List;

import br.com.releasemanger.common.BusinessException;
import br.com.releasemanger.product.model.entity.Product;
import br.com.releasemanger.version.model.entity.Version;
import br.com.releasemanger.version.model.vo.NewVersionResponseDTO;
import br.com.releasemanger.version.model.vo.PublishNewVersionDTO;
import br.com.releasemanger.version.model.vo.VersionLabel;
import jakarta.enterprise.context.Dependent;
import jakarta.transaction.Transactional;

@Dependent
public class VersionService {

	public List<Version> listAllVersions() {
		return Version.listAll();
	}

	@Transactional
	public NewVersionResponseDTO publishNewVersion(PublishNewVersionDTO publishNewVersionDTO) {
		validate(publishNewVersionDTO);
		Product product = this.getProduct(publishNewVersionDTO.productId());
		VersionLabelStrategy versionLabelStrategy = this.getVersionLabelStrategy(publishNewVersionDTO.versionLabel());
		versionLabelStrategy.setNewVersion(product);
		product.persist();
		NewVersionResponseDTO newVersionResponse = NewVersionResponseDTO.builder()
				.version(String.format("%d.%d.%d.%d", product.getMajorVersion(), product.getMinorVersion(),
						product.getPatchVersion(), product.getRevisionVersion()))
				.location("/usr/share/bla").timestamp(LocalDateTime.now()).build();
		return newVersionResponse;
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
			throw new BusinessException("Unexpected version label: " + versionLabel);
		}
	}

	private Product getProduct(Long productId) {
		return Product.findById(productId);
	}

	private void validate(PublishNewVersionDTO publishNewVersionDTO) {
		if (publishNewVersionDTO.productId() == null) {
			throw new BusinessException("ProductId may not be null");
		}
		if (publishNewVersionDTO.versionLabel() == null) {
			throw new BusinessException("Version label may not be null");
		}
		if (VersionLabel.MAJOR.equals(publishNewVersionDTO.versionLabel())) {
			throw new BusinessException("Major version can't be published");
		}
	}
}
