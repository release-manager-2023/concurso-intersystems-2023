package br.com.releasemanger.version.model.vo;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record VersionDTO(
		Long id,
		Long versionStatus,
		String artifact,
		LocalDateTime versionCreatedTimestamp,
		String version,
		String releaseNotes,
		String prerequisite) {

}
