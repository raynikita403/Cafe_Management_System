package in.nikita.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nikita.entity.UserRegisterEntity;
import java.util.Optional;

public interface UserRegisterRepository extends JpaRepository<UserRegisterEntity, Integer> {

	boolean existsByUserEmail(String email);

	Optional<UserRegisterEntity> findByUserEmail(String email);
	 

}
