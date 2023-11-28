package br.com.releasemanger.version.model.vo;

public record ChangeVersionDTO(
		Long versionId,
		Long versionStatus,
		String releaseNotes,
		String prerequisite) {

}
