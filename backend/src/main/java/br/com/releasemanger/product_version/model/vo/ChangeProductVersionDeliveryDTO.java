package br.com.releasemanger.product_version.model.vo;

public record ChangeProductVersionDeliveryDTO(
		Long versionId,
		Long versionStatus,
		String releaseNotes,
		String prerequisite) {

}
