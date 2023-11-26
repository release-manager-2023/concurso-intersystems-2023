package br.com.releasemanger.stakeholder.model.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(schema = "RELEASE_MANAGER", name = "STAKEHOLDER")
@Data
@EqualsAndHashCode(callSuper = false)
public class Stakeholder extends PanacheEntityBase {

	@Id
	@GeneratedValue(generator = "native", strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	@NotBlank(message = "Stakenolder name may not be blank")
	private String name;
	@Column(name = "stakeholder_role")
	private String stakeholderRole;
	@Email
	private String email;
}
