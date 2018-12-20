package com.example.StoreFinder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class StoreGeoSearchService {
	
	@Autowired
	private MongoOperations mongoOperations;
	
	public void createStore(StoreRepresentation store) {
		
		Store storePresistance = new Store();
		storePresistance.setName(store.getName());
		storePresistance.setAddress(store.getAddress());		
		mongoOperations.save(storePresistance);
	}
	
	public List<Store> findAll() {
		return mongoOperations.findAll(Store.class);
		
	}
	
	public List<Store> findByDistance(float longitude, float latitude, int distance) {
		
		Point basePoint = new Point(longitude,latitude);
		Distance radius = new Distance(distance,Metrics.MILES);
		Circle area = new Circle(basePoint,radius);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("address.geoLocation").withinSphere(area));
		
		return mongoOperations.find(query, Store.class);
		
	}

}
