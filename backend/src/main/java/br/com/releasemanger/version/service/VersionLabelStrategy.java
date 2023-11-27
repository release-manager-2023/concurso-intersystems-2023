package br.com.releasemanger.version.service;

import br.com.releasemanger.product.model.entity.Product;

public interface VersionLabelStrategy {

	public void setNewVersion(Product product);
}
