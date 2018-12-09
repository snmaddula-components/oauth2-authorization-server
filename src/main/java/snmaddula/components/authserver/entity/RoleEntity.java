package snmaddula.components.authserver.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The role entity for the "role" table
 * 
 * @author snmaddula
 *
 */
@Getter
@Setter
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String name;

	@ManyToMany 
	@JoinTable(
			name = "role_permission", 
			joinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") }, 
			inverseJoinColumns = {
					@JoinColumn(name = "permission_id", referencedColumnName = "id") })
	private List<PermissionEntity> permissions;
}
