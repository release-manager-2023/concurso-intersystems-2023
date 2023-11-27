package br.com.releasemanger.version.model.vo;

public class VersionString {

	private static final String FORMAT_VERSION = "%d.%d.%d.%d";

	public static final String formatVersion(int major, int minor, int patch, int revision) {
		return FORMAT_VERSION.formatted(major, minor, patch, revision);
	}
}
