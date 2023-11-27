package br.com.releasemanger.version.service;

import br.com.releasemanger.product.model.entity.Product;

public class VersionLabelStrategyMinor implements VersionLabelStrategy {

	@Override
	public void setNewVersion(Product product) {
		product.setMinorVersion(product.getMinorVersion() + 1);
	}

}
