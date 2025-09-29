package in.nikita.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nikita.entity.CafePruduct;

public interface CafeProductRepository extends JpaRepository<CafePruduct,Integer>  {

}
