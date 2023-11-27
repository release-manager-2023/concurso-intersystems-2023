package br.com.releasemanger.version.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.releasemanger.business_exception.BusinessException;
import br.com.releasemanger.product.model.entity.Product;
import br.com.releasemanger.version.model.entity.Version;
import br.com.releasemanger.version.model.exceptions.MajorVersionCantBePublishedException;
import br.com.releasemanger.version.model.vo.NewVersionInputDTO;
import br.com.releasemanger.version.model.vo.NewVersionOutputDTO;
import br.com.releasemanger.version.model.vo.VersionLabel;
import jakarta.enterprise.context.Dependent;
import jakarta.transaction.Transactional;

@Dependent
public class VersionService {

	private static final String DOWNLOAD_URL = "https://release-manager.io/product-id/%s/version/%s";

	@ConfigProperty(name = "release_manager.file_root_path")
	private String fileRootPath;

	public List<Version> listAllVersions() {
		return Version.listAll();
	}

	@Transactional
	public NewVersionOutputDTO publishNewVersion(NewVersionInputDTO newVersionInputDTO) throws IOException {
		this.validate(newVersionInputDTO);
		Product product = this.getProduct(newVersionInputDTO.getProductId());
		this.getVersionLabelStrategy(newVersionInputDTO.getVersionLabel()).setNewVersion(product);
		Path storedFile = this.storeFile(newVersionInputDTO, product);
		Version version = this.buildVersionEntry(newVersionInputDTO, product, storedFile);
		product.persist();
		version.persist();
		return this.buildNewVersionOutputDTO(version);
	}

	private Version buildVersionEntry(NewVersionInputDTO newVersionDTO, Product product, Path fileVersion) {
		Version version = Version.builder()
				.artifactLocation(fileVersion.toAbsolutePath().toString())
				.username(newVersionDTO.getUsername())
				.versionCreatedTimestamp(LocalDateTime.now())
				.productId(product.getId())
				.build();
		version.setVersionFromProduct(product);
		return version;
	}

	private Path storeFile(NewVersionInputDTO newVersionInputDTO, Product product) throws IOException {
		Path fileDirectory = Path.of(fileRootPath,
				"product-id", product.getId().toString(),
				"version", product.getVersionString());

		Files.createDirectories(fileDirectory);

		Path fileVersion = Paths.get(fileDirectory.toString(), newVersionInputDTO.getFile().getName());

		Files.copy(newVersionInputDTO.getFile().toPath(), fileVersion, StandardCopyOption.COPY_ATTRIBUTES);
		return fileVersion;
	}

	private NewVersionOutputDTO buildNewVersionOutputDTO(Version version) {
		return NewVersionOutputDTO.builder()
				.version(version.getVersionString())
				.location(DOWNLOAD_URL.formatted(version.getProductId(), version.getVersionString()))
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

	private void validate(NewVersionInputDTO publishNewVersionDTO) {
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
