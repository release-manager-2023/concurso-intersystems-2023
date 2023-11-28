package br.com.releasemanger.version.service;

import static br.com.releasemanger.version_status.model.vo.VersionStatusNotificationChannel.VERSION_STATUS_NOTIFICATION;

import java.io.File;
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
import br.com.releasemanger.version.model.vo.ChangeVersionDTO;
import br.com.releasemanger.version.model.vo.NewVersionInputDTO;
import br.com.releasemanger.version.model.vo.NewVersionOutputDTO;
import br.com.releasemanger.version.model.vo.VersionLabel;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class VersionService {

	private static final String DOWNLOAD_URL = "https://release-manager.io/products/%d/versions/%d";

	@ConfigProperty(name = "release_manager.file_root_path")
	private String fileRootPath;

	@Inject
	private EventBus bus;

	@Inject
	private UsernameFaker usernameFaker;

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
		this.publishVersionStatusNotification(version);
		return this.buildNewVersionOutputDTO(version);
	}

	private void publishVersionStatusNotification(Version version) {
		bus.publish(VERSION_STATUS_NOTIFICATION, version);
	}

	private Version buildVersionEntry(NewVersionInputDTO newVersionDTO, Product product, Path fileVersion) {
		Version version = Version.builder()
				.artifactLocation(fileVersion.toAbsolutePath().toString())
				.username(usernameFaker.getUsername())
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
				.versionId(version.getId())
				.versionString(version.getVersionString())
				.location(DOWNLOAD_URL.formatted(version.getProductId(), version.getId()))
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

	@Transactional
	public Version changeVersion(ChangeVersionDTO changeVersionStatusDTO) {
		Version version = Version.findById(changeVersionStatusDTO.versionId());
		version.setVersionStatus(changeVersionStatusDTO.versionStatus());
		version.setReleaseNotes(changeVersionStatusDTO.releaseNotes());
		version.setPrerequisite(changeVersionStatusDTO.prerequisite());
		version.persist();
		this.publishVersionStatusNotification(version);
		return version;
	}

	public File getFile(Long versionId) {
		Version version = this.findVersionById(versionId);
		return new File(version.getArtifactLocation());
	}

	public Version findVersionById(Long versionId) {
		return Version.findById(versionId);
	}

	public List<Version> getVersionsByProduct(Long productId) {
		return Version.list("productId = ?1", productId);
	}
}
