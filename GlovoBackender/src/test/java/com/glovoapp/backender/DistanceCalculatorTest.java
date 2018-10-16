package com.glovoapp.backender;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.glovoapp.backender.dto.Location;
import com.glovoapp.backender.shared.DistanceCalculator;

public class DistanceCalculatorTest {
	@Test
	public void smokeTest() {
		Location francescMacia = new Location(41.3925603, 2.1418532);
		Location placaCatalunya = new Location(41.3870194, 2.1678584);

		// More or less 2km from Francesc Macia to Placa Catalunya
		assertEquals(2.0, DistanceCalculator.calculateDistance(francescMacia, placaCatalunya), 0.5);
		
		
		
	}
	
	@Test
	public void distanceLessThan_one_km() {
		Location courierLocation = new Location(41.40668166914191, 2.1794396752388514);
		Location pickupLocation = new Location(41.40200262885847, 2.1827656816430068);
        double distance = DistanceCalculator.calculateDistance(courierLocation, pickupLocation);
        System.out.println(distance);
		assertEquals(1.0, distance, 0.5);
	
		
	}
	
	@Test
	public void distanceFurtherThan_one_km() {
		// the delta to 3 km
		
		Location courierLocation = new Location(41.40668166914191, 2.1794396752388514);
		Location pickupLocation = new Location(41.37818190242933, 2.1697510915413);
        double distance = DistanceCalculator.calculateDistance(courierLocation, pickupLocation);
		
        System.out.println(distance);
		assertEquals(1.0, distance, 3.0);
		
		
//		Location courierLocation = new Location(41.39267394624044, 2.16265389480811);
//		Location pickupLocation = new Location(41.38736991028418, 2.184394515823623);
//        double distance = DistanceCalculator.calculateDistance(courierLocation, pickupLocation);
//		
//        System.out.println(distance);
		
		
	}

}