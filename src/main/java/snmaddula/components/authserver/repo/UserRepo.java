package snmaddula.components.authserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import snmaddula.components.authserver.entity.UserEntity;

/**
 * The User repository to run queries on user table.
 * 
 * @author snmaddula
 *
 */
@Repository
@Transactional
public interface UserRepo extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

	UserEntity findByEmail(String email);

}
