package br.com.releasemanger.version.model.vo;

import java.io.File;

import jakarta.ws.rs.FormParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublishNewVersionDTO {

	@FormParam("username")
	private String username;

	@FormParam("productId")
	private Long productId;

	@FormParam("file")
	private File file;

	@FormParam("versionLabel")
	private VersionLabel versionLabel;

}
