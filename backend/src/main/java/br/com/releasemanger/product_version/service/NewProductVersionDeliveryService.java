package br.com.releasemanger.product_version.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.releasemanger.business_exception.BusinessException;
import br.com.releasemanger.product.model.entity.Product;
import br.com.releasemanger.product_version.model.entity.ProductVersionDelivery;
import br.com.releasemanger.product_version.model.exceptions.MajorVersionCantBePublishedException;
import br.com.releasemanger.product_version.model.vo.NewProductVersionDeliveryInputDTO;
import br.com.releasemanger.product_version.model.vo.NewProductVersionDeliveryOutputDTO;
import br.com.releasemanger.version_label.model.vo.VersionLabel;
import br.com.releasemanger.version_label.service.VersionLabelFactory;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class NewProductVersionDeliveryService {

	@Inject
	private UsernameFaker usernameFaker;
	@ConfigProperty(name = "release_manager.file_root_path")
	private String fileRootPath;
	@Inject
	private VersionStatusNotificationService publishVersionStatusNotification;
	@Inject
	private DownloadUrlBuilder downloadUrlBuilder;
	@Inject
	private VersionLabelFactory versionLabelFactory;

	@Transactional
	public NewProductVersionDeliveryOutputDTO publishNewVersion(Long productId, NewProductVersionDeliveryInputDTO newVersionInputDTO)
			throws IOException {
		this.validate(newVersionInputDTO);
		Product product = this.getProduct(productId);
		this.versionLabelFactory.getVersionLabelStrategy(newVersionInputDTO.getVersionLabel()).setNewVersion(product);
		Path storedFile = this.storeFile(newVersionInputDTO, product);
		ProductVersionDelivery version = this.buildVersionEntry(newVersionInputDTO, product, storedFile);
		product.persist();
		version.persist();
		this.publishVersionStatusNotification.publish(version);
		return this.buildNewVersionOutputDTO(version);
	}

	private ProductVersionDelivery buildVersionEntry(NewProductVersionDeliveryInputDTO newVersionDTO, Product product, Path fileVersion) {
		ProductVersionDelivery version = ProductVersionDelivery.builder()
				.artifactLocation(fileVersion.toAbsolutePath().toString())
				.username(usernameFaker.getUsername())
				.versionCreatedTimestamp(LocalDateTime.now())
				.productId(product.getId())
				.build();
		version.setVersionFromProduct(product);
		return version;
	}

	private Path storeFile(NewProductVersionDeliveryInputDTO newVersionInputDTO, Product product) throws IOException {
		Path fileDirectory = Path.of(fileRootPath,
				"product-id", product.getId().toString(),
				"version", product.getVersionString());
	
		Files.createDirectories(fileDirectory);
	
		Path fileVersion = Paths.get(fileDirectory.toString(), newVersionInputDTO.getFile().getName());
	
		Files.copy(Paths.get(newVersionInputDTO.getFile().getPath()), fileVersion,
				StandardCopyOption.COPY_ATTRIBUTES);
		return fileVersion;
	}

	private NewProductVersionDeliveryOutputDTO buildNewVersionOutputDTO(ProductVersionDelivery version) {
		return NewProductVersionDeliveryOutputDTO.builder()
				.versionId(version.getId())
				.versionString(version.getVersionString())
				.artifact(downloadUrlBuilder.buildUrl(version))
				.timestamp(version.getVersionCreatedTimestamp())
				.build();
	}

	private Product getProduct(Long productId) {
		return Product.findById(productId);
	}

	private void validate(NewProductVersionDeliveryInputDTO publishNewVersionDTO) {
		if (publishNewVersionDTO.getVersionLabel() == null) {
			throw new BusinessException("Version label may not be null");
		}
		if (VersionLabel.MAJOR.equals(publishNewVersionDTO.getVersionLabel())) {
			throw new MajorVersionCantBePublishedException();
		}
	}

}
