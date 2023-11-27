package br.com.releasemanger.version.model.vo;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record NewVersionOutputDTO(
		String version,
		String location,
		LocalDateTime timestamp) {

}
