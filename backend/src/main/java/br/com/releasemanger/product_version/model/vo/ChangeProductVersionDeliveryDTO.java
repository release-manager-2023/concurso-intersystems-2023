package br.com.releasemanger.product_version.model.vo;

public record ChangeProductVersionDeliveryDTO(
		Long versionStatus,
		String releaseNotes,
		String prerequisite) {

}
