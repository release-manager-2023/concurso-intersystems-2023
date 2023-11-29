package br.com.releasemanger.product_version.model.vo;

import java.net.URI;
import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record NewProductVersionDeliveryOutputDTO(
		Long versionId,
		String versionString,
		URI artifact,
		LocalDateTime timestamp) {

}
