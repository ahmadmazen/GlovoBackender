package com.glovoapp.backender;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import com.glovoapp.backender.dto.Location;
import com.glovoapp.backender.dto.Order;
import com.glovoapp.backender.model.OrderPredicates;
import com.glovoapp.backender.shared.Constants;

public class OrderPredicatesTest extends OrderPredicates {
	@Test
	public void testGlovoBoxRequiredPredicate() {
		Order orderWithBox = new Order().withId("order-1b77aed4a0ff")
				.withDescription("2x Hot dog with Salad\n2x Pork bao with Fries\n1x Pizza with Fries").withFood(true)
				.withVip(false).withPickup(new Location(41.398979243214484, 2.1765247141510846))
				.withDelivery(new Location(41.40052857611856, 2.17474693857396));

		assertTrue(glovoBoxRequired(Constants.ITEMS_REQUIRE_GLOVO_BOX_ARR).test(orderWithBox));

		Order orderWithoutBox = new Order().withId("order-a788acb9d1bc")
				.withDescription("1x Pork bao with Fries\\n1x Pork bao with Salad\\n2x Hot dog with Fries")
				.withFood(true).withVip(false).withPickup(new Location(41.407290103152775, 2.1737712291996045))
				.withDelivery(new Location(41.387576370461375, 2.1842450379999554));

		assertFalse(glovoBoxRequired(Constants.ITEMS_REQUIRE_GLOVO_BOX_ARR).test(orderWithoutBox));

	}

	@Test
	public void testCloserOrdersPredicate() {
		Order closeOrder = new Order().withId("order-1b77aed4a0ff")
				.withDescription("2x Hot dog with Salad\n2x Pork bao with Fries\n1x Pizza with Fries").withFood(true)
				.withVip(false).withPickup(new Location(41.40200262885847, 2.1827656816430068))
				.withDelivery(new Location(41.40052857611856, 2.17474693857396));
		Location courierLocation = new Location(41.40668166914191, 2.1794396752388514);

		// since the distance is 0.5896161034852299 less than 1 km

		assertTrue(closerOrders(courierLocation, Constants.CLOSER_DISTANCE_IN_RANGE).test(closeOrder));

		Order furtherOrder = new Order().withId("order-2bd588da22c4")
				.withDescription("1x Hot dog with Fries\n2x Hot dog with Fries\n2x Pizza with Salad").withFood(true)
				.withVip(false).withPickup(new Location(41.37818190242933, 2.1697510915413))
				.withDelivery(new Location(41.40052857611856, 2.17474693857396));
		Location courierLocationF = new Location(41.40668166914191, 2.1794396752388514);

		// since the distance is 3.2704652092197555 further than 1 km

		assertFalse(closerOrders(courierLocationF, Constants.CLOSER_DISTANCE_IN_RANGE).test(furtherOrder));

	}

	@Test
	public void testVIPCustomerPredicate() {
		Order vipOrder = new Order().withId("order-1b77aed4a0ff")
				.withDescription("2x Hot dog with Salad\n2x Pork bao with Fries\n1x Pizza with Fries").withFood(true)
				.withVip(true).withPickup(new Location(41.40200262885847, 2.1827656816430068))
				.withDelivery(new Location(41.40052857611856, 2.17474693857396));

		assertTrue(ordersOfVIPCustmoers().test(vipOrder));

		Order notVIPOrder = new Order().withId("order-1b77aed4a0ff")
				.withDescription("2x Hot dog with Salad\n2x Pork bao with Fries\n1x Pizza with Fries").withFood(true)
				.withVip(false).withPickup(new Location(41.40200262885847, 2.1827656816430068))
				.withDelivery(new Location(41.40052857611856, 2.17474693857396));

		assertFalse(ordersOfVIPCustmoers().test(notVIPOrder));
	}

	@Test
	public void testfoodOrdersPredicate() {
		Order foodOrder = new Order().withId("order-1b77aed4a0ff")
				.withDescription("2x Hot dog with Salad\n2x Pork bao with Fries\n1x Pizza with Fries").withFood(true)
				.withVip(true).withPickup(new Location(41.40200262885847, 2.1827656816430068))
				.withDelivery(new Location(41.40052857611856, 2.17474693857396));

		assertTrue(foodOrders().test(foodOrder));

		Order notFoodOrder = new Order().withId("order-1b77aed4a0ff")
				.withDescription("2x Hot dog with Salad\n2x Pork bao with Fries\n1x Pizza with Fries").withFood(false)
				.withVip(true).withPickup(new Location(41.40200262885847, 2.1827656816430068))
				.withDelivery(new Location(41.40052857611856, 2.17474693857396));

		assertFalse(foodOrders().test(notFoodOrder));

	}

	@Test
	public void testfurtherOrdersPredicate() {
		Order furtherOrder = new Order().withId("order-2bd588da22c4")
				.withDescription("1x Hot dog with Fries\n2x Hot dog with Fries\n2x Pizza with Salad").withFood(true)
				.withVip(false).withPickup(new Location(41.37818190242933, 2.1697510915413))
				.withDelivery(new Location(41.40052857611856, 2.17474693857396));
		Location courierLocation = new Location(41.40668166914191, 2.1794396752388514);

		assertTrue(furtherOrders(courierLocation, 3.0).test(furtherOrder));
		assertFalse(furtherOrders(courierLocation, 5.0).test(furtherOrder));

	}

	@Test
	public void testTheRestOfOrdersPredicate() {

		Order otherOrder = new Order().withId("order-2bd588da22c4")
				.withDescription("1x Hot dog with Fries\n2x Hot dog with Fries\n2x with Salad").withFood(false)
				.withVip(false).withPickup(new Location(41.38736991028418, 2.184394515823623))
				.withDelivery(new Location(41.40052857611856, 2.17474693857396));

		Location courierLocation = new Location(41.39267394624044, 2.16265389480811);

		assertFalse(closerOrders(courierLocation, Constants.CLOSER_DISTANCE_IN_RANGE).test(otherOrder));
		assertFalse(furtherOrders(courierLocation, Constants.DISTANCE_FURTHER_THAN).test(otherOrder));
		assertFalse(foodOrders().test(otherOrder));
		assertFalse(ordersOfVIPCustmoers().test(otherOrder));
		assertFalse(glovoBoxRequired(Constants.ITEMS_REQUIRE_GLOVO_BOX_ARR).test(otherOrder));

		assertTrue(otherOrders(courierLocation, Constants.CLOSER_DISTANCE_IN_RANGE, Constants.DISTANCE_FURTHER_THAN)
				.test(otherOrder));

	}

}
