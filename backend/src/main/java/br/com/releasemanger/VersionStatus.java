package br.com.releasemanger;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "RELEASE_MANAGER", name = "VERSION_STATUS")
@Data
@EqualsAndHashCode(callSuper = false)
public class VersionStatus extends PanacheEntity {

	private String name;

}
