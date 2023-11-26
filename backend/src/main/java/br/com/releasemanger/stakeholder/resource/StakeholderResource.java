package br.com.releasemanger.stakeholder.resource;

import br.com.releasemanger.stakeholder.model.entity.Stakeholder;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "stakeholder")
public interface StakeholderResource extends PanacheEntityResource<Stakeholder, Long> {

}
