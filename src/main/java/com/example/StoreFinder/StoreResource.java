package com.example.StoreFinder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stores")
public class StoreResource {
	
	@Autowired
	private StoreGeoSearchService service;
	
	@RequestMapping(method = RequestMethod.POST)
	public void create(@RequestBody StoreRepresentation storeRep) {
		
		service.createStore(storeRep);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Store> findAll() {
		
		return service.findAll();
	}
	
	@RequestMapping(value = "/findByDistance", method = RequestMethod.GET)
	public List<Store> findByDistance(@RequestParam(value="long") float longitude,@RequestParam(value="lat") float latitude,
			@RequestParam(value="dist") int distance) {
		
		return service.findByDistance(longitude, latitude, distance);
	}

}
