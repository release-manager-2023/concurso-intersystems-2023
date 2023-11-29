package br.com.releasemanger.product_version.service;

import static br.com.releasemanger.version_status.model.vo.VersionStatusNotificationChannel.VERSION_STATUS_NOTIFICATION;

import br.com.releasemanger.product_version.model.entity.ProductVersionDelivery;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Dependent
public class VersionStatusNotificationService {

	@Inject
	private EventBus bus;

	public void publish(ProductVersionDelivery version) {
		bus.publish(VERSION_STATUS_NOTIFICATION, version);
	}

}
