package br.com.releasemanger;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "version-status")
public interface VersionStatusResource extends PanacheEntityResource<VersionStatus, Long> {

}
