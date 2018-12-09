package snmaddula.components.authserver.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

/**
 * The user entity for the "user" table
 * 
 * @author snmaddula
 *
 */
@Setter
@Getter
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String email;
	private String username;
	private String password;
	private boolean enabled;
	
	private boolean accountLocked;
	private boolean accountExpired;
	private boolean credentialsExpired;
	
	@JoinTable(
			name = "role_user", 
			joinColumns = {
					@JoinColumn(name = "user_id", referencedColumnName = "id") }, 
			inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	@ManyToMany(fetch = FetchType.EAGER)
	private List<RoleEntity> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			role.getPermissions()
				.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));
		});
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}
	
	
}
