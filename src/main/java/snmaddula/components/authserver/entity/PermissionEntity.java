package snmaddula.components.authserver.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The permission entity for the "permission" table
 * 
 * @author snmaddula
 *
 */
@Getter
@Setter
@Entity
@Table(name = "permission")
public class PermissionEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String name;
}
