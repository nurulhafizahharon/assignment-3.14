package com.skillsunion.shoppingcartapi.controller;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.skillsunion.shoppingcartapi.entity.Catalogue;
import com.skillsunion.shoppingcartapi.repository.CatalogueRepository;

@RestController
public class CatalogueController {
	
	@Autowired CatalogueRepository repo; // Added
    
	// Updated (produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value="/catalogues", method = RequestMethod.GET ,consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Catalogue>> list(@RequestParam(defaultValue = "") String search){
        List<Catalogue> results = repo.findByNameContaining(search);
        System.out.println("Results Size: "+results.size());
        if(results.size() == 0) {
        	return ResponseEntity.notFound().build();
        }else {
        	return ResponseEntity.ok(results);
        }
    }

    /*
        Switch between @PathVariable and @RequestParam to help learners understand
        the difference.

        @PathVariable => /catalogues/1
        @RequestParam => /catalogues?id=1
    */
    @GetMapping(value = "/catalogues/{id}")
    public ResponseEntity<Optional<Catalogue>> get(@PathVariable int id){
        Optional<Catalogue> result = (Optional<Catalogue>) repo.findById(id);
        if(result.isPresent()) return ResponseEntity.ok(result);
        
        return ResponseEntity.notFound().build();
    }

    /*
        By default, a @PostMapping assumes application/json content type.
    */
    @PostMapping(value = "/catalogues")
    public ResponseEntity<Catalogue> create(@RequestBody RequestBodyTempData data){
        Catalogue newRecord = new Catalogue();
        newRecord.setName(data.getName());
        newRecord.setPrice(data.getPrice());
        
        try {
        	Catalogue created = repo.save(newRecord);
            return ResponseEntity.created(null).body(created);
        }catch(Exception e) {
        	System.out.println(e);
        	return ResponseEntity.internalServerError().build();
        }
        
    }
}

/*
    This is a private class, not accessible outside this java file.
    We will use this for now. In the future, the request body
*/
class RequestBodyTempData {
    String name;
    float price;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}