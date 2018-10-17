package com.glovoapp.backender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.glovoapp.backender.config.OrderProperties;
import com.glovoapp.backender.dto.Courier;
import com.glovoapp.backender.dto.Location;
import com.glovoapp.backender.dto.Order;
import com.glovoapp.backender.model.CourierRepository;
import com.glovoapp.backender.model.OrderRepository;
import com.glovoapp.backender.shared.Constants;
import com.glovoapp.backender.shared.DistanceCalculator;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = { DemoApplication.class }, loader = AnnotationConfigContextLoader.class)
public class OrderRepositoryTest {
	private OrderRepository orderRepository = new OrderRepository();
	private CourierRepository courierRepository = new CourierRepository();

	private List<Order> orders;
	@Autowired
	private OrderProperties orderProperties;

	/*
	 * @Configuration public static class ContextConfiguration {
	 * 
	 * // this bean will be injected into the OrderRepositoryTest class
	 * 
	 * @Bean public OrderProperties orderProperties() { OrderProperties
	 * orderProperties = new OrderProperties();
	 * orderProperties.setITEMS_REQUIRE_GLOVO_BOX((String[])
	 * Constants.ITEMS_REQUIRE_GLOVO_BOX.toArray());
	 * orderProperties.setFilters(Constants.FILTERS);
	 * orderProperties.setCloser_distance_in_range(Constants.
	 * CLOSER_DISTANCE_IN_RANGE);
	 * orderProperties.setDistance_further_than(Constants.DISTANCE_FURTHER_THAN);
	 * 
	 * return orderProperties; } }
	 */

	@Test
	public void findAll() {
		List<Order> orders = new OrderRepository().findAll();

		assertFalse(orders.isEmpty());

		Order firstOrder = orders.get(0);

		Order expected = new Order().withId("order-1").withDescription("I want a pizza cut into very small slices")
				.withFood(true).withVip(false).withPickup(new Location(41.3965463, 2.1963997))
				.withDelivery(new Location(41.407834, 2.1675979));

		assertEquals(expected, firstOrder);
	}

	@Test
	public void findOrdersRequireGlovoBox() {

		Order expected = new Order().withId("order-1b77aed4a0ff")
				.withDescription("2x Hot dog with Salad\n2x Pork bao with Fries\n1x Pizza with Fries").withFood(true)
				.withVip(false).withPickup(new Location(41.398979243214484, 2.1765247141510846))
				.withDelivery(new Location(41.40052857611856, 2.17474693857396));

		orders = orderRepository.findOrdersRequireGlovoBox(Constants.ITEMS_REQUIRE_GLOVO_BOX_ARR);
		assertTrue(orders.contains(expected));

		Order notExpected = new Order().withId("order-a788acb9d1bc")
				.withDescription("1x Pork bao with Fries\\n1x Pork bao with Salad\\n2x Hot dog with Fries")
				.withFood(true).withVip(false).withPickup(new Location(41.407290103152775, 2.1737712291996045))
				.withDelivery(new Location(41.387576370461375, 2.1842450379999554));

		assertFalse(orders.contains(notExpected));

	}

	@Test
	public void findcloserOrders() {

		// for courier courier-2918f28800f7 there is order closer

		Courier c1 = courierRepository.findById("courier-2918f28800f7");
		orders = orderRepository.findcloserOrders(c1.getLocation(), Constants.CLOSER_DISTANCE_IN_RANGE);
		orders.forEach(order -> System.out.println(order.getId()));
		assertFalse(orders.isEmpty());
		// System.out.println(orders.size());
		orders.forEach(order -> {
			Double distance = DistanceCalculator.calculateDistance(c1.getLocation(), order.getPickup());
			// System.out.println("distance from inside forEach : " + distance);
			assertEquals(Constants.CLOSER_DISTANCE_IN_RANGE, distance, 0.9);

		}

		);

		// for courier courier-31c0b957f89b the orders in test/order.json are further
//			Courier c = courierRepository.findById("courier-31c0b957f89b");
//			orders = orderRepository.findcloserOrders(c.getLocation(), Constants.CLOSER_DISTANCE_IN_RANGE);
//			orders.forEach(order-> System.out.println(order.getId()));
//			
//		    //System.out.println(orders.size());
//			orders.forEach( order-> 
//					 {
//					 Double distance = DistanceCalculator.calculateDistance(c.getLocation(), order.getPickup());
//					 System.out.println("distance from inside forEach : " + distance);
//					  assertNotEquals(Constants.CLOSER_DISTANCE_IN_RANGE, distance, 0.5);
//						
//					}
//					
//					);

	}

	@Test
	public void findOrdersOfVIPCustomer() {

		List<Order> nonVIP = orderRepository.findAll().stream().filter(order -> order.getVip() == false)
				.collect(Collectors.toList());
		System.out.println(nonVIP.size());
		List<Order> VIPorders = orderRepository.findOrdersOfVIPCustomer();
		// System.out.println(VIPorders.size());

		nonVIP.forEach(order -> assertFalse(order.getVip() == true));
		VIPorders.forEach(order -> assertTrue(order.getVip() == true));

	}

	@Test
	public void findFoodOrders() {
		List<Order> nonFood = orderRepository.findAll().stream().filter(order -> order.getFood() == false)
				.collect(Collectors.toList());
		System.out.println(nonFood.size());
		List<Order> foodOrders = orderRepository.findFoodOrders();
		// System.out.println(foodOrders.size());

		nonFood.forEach(order -> assertFalse(order.getFood() == true));
		foodOrders.forEach(order -> assertTrue(order.getFood() == true));

	}

	@Test
	public void findFurtherOrders() {
		// for courier courier-31c0b957f89b the orders in test/order.json are very less
		// than the defined further distance 5.0
		Courier c = courierRepository.findById("courier-31c0b957f89b");
		orders = orderRepository.findFurtherOrders(c.getLocation(), Constants.DISTANCE_FURTHER_THAN);
		orders.forEach(order -> System.out.println(order.getId()));
		assertTrue(orders.isEmpty());

		// System.out.println(orders.size());
		orders.forEach(order -> {
			Double distance = DistanceCalculator.calculateDistance(c.getLocation(), order.getPickup());
			// System.out.println("distance from inside forEach : " + distance);
			assertNotEquals(Constants.DISTANCE_FURTHER_THAN, distance, 0.5);

		}

		);

		// for courier courier-2918f28800f7 we will test the orders in further distance
		// 2.0

		Courier c1 = courierRepository.findById("courier-2918f28800f7");
		orders = orderRepository.findFurtherOrders(c1.getLocation(), 2.0);
		orders.forEach(order -> System.out.println(order.getId()));
		assertFalse(orders.isEmpty());
		// System.out.println(orders.size());
		orders.forEach(order -> {
			Double distance = DistanceCalculator.calculateDistance(c1.getLocation(), order.getPickup());
			// System.out.println("distance from inside forEach : " + distance);
			assertEquals(2.0, distance, 1.0);

		}

		);
	}

	@Test
	public void findTheRestOfOrders() {

		Order expected = orderRepository.findById("order-bd6e82ccee4c");
		Courier c = courierRepository.findById("courier-2918f28800f7");

		orders = orderRepository.findTheRestOfOrders(c.getLocation(), Constants.CLOSER_DISTANCE_IN_RANGE,
				Constants.DISTANCE_FURTHER_THAN);
		// orders.forEach(order-> System.out.println(order.getId()));
		assertTrue(orders.contains(expected));
	}

	@Test
	public void getOrdersForCourier() {

		assertNull(orderRepository.getOrdersForCourier(null));
		assertNull(orderRepository.getOrdersForCourier(""));
		assertNull(orderRepository.getOrdersForCourier(" "));

	}

}