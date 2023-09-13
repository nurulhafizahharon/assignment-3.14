package com.skillsunion.shoppingcartapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillsunion.shoppingcartapi.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

}
