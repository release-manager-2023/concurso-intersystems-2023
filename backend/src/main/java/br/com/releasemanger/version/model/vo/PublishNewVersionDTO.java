package br.com.releasemanger.version.model.vo;

public record PublishNewVersionDTO(
		String username,
		Long productId,
		byte[] artifact,
		VersionLabel versionLabel) {

}
