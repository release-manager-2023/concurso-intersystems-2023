package br.com.releasemanger.version.model.entity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "RELEASE_MANAGER", name = "VERSION")
@Data
@EqualsAndHashCode(callSuper = false)
public class Version extends PanacheEntityBase {

	@Id
	@GeneratedValue(generator = "native", strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(name = "product_id", nullable = false)
	private Long product;

	@Column(name = "version_status_id", nullable = false)
	private Long versionStatus = 1L;

	@Column(name = "artifact_location")
	private String artifactLocation;

	@Column(name = "username")
	private String username;

	@Column(name = "version_created_timestamp")
	private LocalDateTime versionCreatedTimestamp;

	@Column(name = "major_version")
	private Integer majorVersion;

	@Column(name = "minor_version")
	private Integer minorVersion;

	@Column(name = "patch_version")
	private Integer patchVersion;

	@Column(name = "revision_version")
	private Integer revisionVersion;

}