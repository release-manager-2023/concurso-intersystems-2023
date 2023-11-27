package br.com.releasemanger.version.resource;

import br.com.releasemanger.version.model.entity.Version;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "version")
public interface VersionResource extends PanacheEntityResource<Version, Long> {

}
