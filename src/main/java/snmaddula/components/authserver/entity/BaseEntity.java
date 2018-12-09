package snmaddula.components.authserver.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * The base entity with the common attributes.
 * 
 * @author snmaddula
 *
 */
@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

	protected static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@CreationTimestamp
	protected LocalDateTime createdTime;

	@UpdateTimestamp
	protected LocalDateTime updatedTime;

}
