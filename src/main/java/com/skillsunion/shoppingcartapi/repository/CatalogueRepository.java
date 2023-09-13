package com.skillsunion.shoppingcartapi.repository;

import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.skillsunion.shoppingcartapi.entity.Catalogue;

@Repository
public interface CatalogueRepository extends JpaRepository <Catalogue, Integer>{

	List<Catalogue> findByNameContaining(@Param("name") String name);

}
