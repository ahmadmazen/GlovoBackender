package com.glovoapp.backender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.glovoapp.backender.dto.Courier;
import com.glovoapp.backender.dto.Location;
import com.glovoapp.backender.model.CourierRepository;
import com.glovoapp.backender.shared.Vehicle;

public class CourierRepositoryTest {
	@Test
	void findOneExisting() {
		Courier courier = new CourierRepository().findById("courier-1");
		Courier expected = new Courier().withId("courier-1").withBox(true).withName("Manolo Escobar")
				.withVehicle(Vehicle.MOTORCYCLE).withLocation(new Location(41.3965463, 2.1963997));

		assertEquals(expected, courier);
	}

	@Test
	void findOneNotExisting() {
		Courier courier = new CourierRepository().findById("bad-courier-id");
		assertNull(courier);
	}

	@Test
	void findAll() {
		List<Courier> all = new CourierRepository().findAll();
		assertFalse(all.isEmpty());
	}
}