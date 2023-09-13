package com.skillsunion.shoppingcartapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.skillsunion.shoppingcartapi.entity.Cart;
import com.skillsunion.shoppingcartapi.entity.Catalogue;
import com.skillsunion.shoppingcartapi.repository.CartRepository;

@RestController
public class CartController {
	
	@Autowired CartRepository repo;
    
	@GetMapping("/carts")
    public ResponseEntity<List<Cart>> list(){
    	List<Cart> cartItems = repo.findAll();
    	if(cartItems.size() == 0) {
    		return ResponseEntity.notFound().build();
    	}else {
    		return ResponseEntity.ok(cartItems);
    	}
    	
    }

	@GetMapping("/carts/{id}")
    public ResponseEntity<Optional<Cart>> get(@PathVariable int id){
        Optional<Cart> cartItem = repo.findById(id);
        if(cartItem.isEmpty()) {
        	return ResponseEntity.notFound().build();
        }else {
        	return ResponseEntity.ok(cartItem);
        }
        
    }

	@PostMapping("/carts")
    public ResponseEntity<Cart> create(@RequestBody CreateCartModel data){
		
        Catalogue catalogue = new Catalogue();
        catalogue.setId(data.getItemId());
        Cart cart = new Cart();
        cart.setItem(catalogue);
        cart.setQuantity(data.getQuantity());
        
        Cart created = repo.save(cart);
        if(created == null) {
        	return ResponseEntity.internalServerError().build();
        }else {
        	return ResponseEntity.created(null).body(created);
        }
    }
}

class CreateCartModel {
	int itemId;
	int quantity;
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
