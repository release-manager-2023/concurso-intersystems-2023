package br.com.releasemanger.stakeholder_notification.service;

import static br.com.releasemanger.version_status.model.vo.VersionStatusNotificationChannel.VERSION_STATUS_NOTIFICATION;

import java.util.List;

import br.com.releasemanger.stakeholder.model.entity.Stakeholder;
import br.com.releasemanger.version.model.entity.Version;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.Dependent;
import lombok.extern.slf4j.Slf4j;

@Dependent
@Slf4j
public class StakeholderNotificationService {

	@ConsumeEvent(VERSION_STATUS_NOTIFICATION)
	@Blocking
	public void consume(Version version) {
		log.info("Version status changed: " + version.getVersionString() + " to " + version.getVersionStatus());
		List<Stakeholder> stakeholders = Stakeholder.listAll();
		if (stakeholders.size() == 0) {
			log.info("No Stakeholders to notify");
		}
		stakeholders.stream()
				.filter(stakeholder -> stakeholder.getVersionStatuses().stream()
						.anyMatch(vs -> vs.getId().equals(version.getVersionStatus())))
				.forEach(this::sendStakeholderNotification);
	}

	public void sendStakeholderNotification(Stakeholder stakeholder) {
		log.info("Stakeholder " + stakeholder.getName() + " notified");
	}
}
