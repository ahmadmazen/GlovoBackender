package com.glovoapp.backender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import com.glovoapp.backender.dto.Courier;
import com.glovoapp.backender.dto.Location;
import com.glovoapp.backender.model.CourierRepository;
import com.glovoapp.backender.shared.Vehicle;

public class CourierRepositoryTest {
	@Test
	public void findOneExisting() {
		Courier courier = new CourierRepository().findById("courier-1");
		Courier expected = new Courier().withId("courier-1").withBox(true).withName("Manolo Escobar")
				.withVehicle(Vehicle.MOTORCYCLE).withLocation(new Location(41.3965463, 2.1963997));

		assertEquals(expected, courier);
	}

	@Test
	public void findOneNotExisting() {
		Courier courier = new CourierRepository().findById("bad-courier-id");
		assertNull(courier);
	}

	@Test
	public void findAll() {
		List<Courier> all = new CourierRepository().findAll();
		assertFalse(all.isEmpty());
	}
}