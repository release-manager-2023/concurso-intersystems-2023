package br.com.releasemanger.version.model.vo;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record NewVersionOutputDTO(
		Long versionId,
		String versionString,
		String location,
		LocalDateTime timestamp) {

}
