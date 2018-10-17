package com.glovoapp.backender.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.glovoapp.backender.config.OrderProperties;
import com.glovoapp.backender.dto.Courier;
import com.glovoapp.backender.dto.Location;
import com.glovoapp.backender.dto.Order;
import com.glovoapp.backender.shared.FilterName;
import com.glovoapp.backender.shared.Utils;
import com.glovoapp.backender.shared.Vehicle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class OrderRepository extends OrderPredicates {
	private static final String ORDERS_FILE = "/orders.json";
	private static final List<Order> orders;

	@Autowired
	CourierRepository courierRepository;

	@Autowired
	OrderProperties orderProperties;
	

	static {
		try (Reader reader = new InputStreamReader(OrderRepository.class.getResourceAsStream(ORDERS_FILE))) {
			Type type = new TypeToken<List<Order>>() {
			}.getType();
			orders = new Gson().fromJson(reader, type);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	

	public Order findById(String orderId) {
		return orders.stream().filter(order -> orderId.equals(order.getId())).findFirst().orElse(null);
	}

	
	public List<Order> findAll() {
		return new ArrayList<>(orders);
	}

	public List<Order> findOrdersRequireGlovoBox(String[] itemsRequireBox) {

		return Utils.filter(glovoBoxRequired(itemsRequireBox), new ArrayList<Order>(orders));

	}

	public List<Order> findcloserOrders(Location courierLocation, Double closeDistanceRange) {

		System.out.println("before close orders : " + orders.size());
		System.out.println("number of close orders to add : " + Utils.filter(closerOrders(courierLocation, closeDistanceRange), new ArrayList<Order>(orders)).size());
		return Utils.filter(closerOrders(courierLocation, closeDistanceRange), new ArrayList<Order>(orders));

	}

	public List<Order> findOrdersOfVIPCustomer() {

		return Utils.filter(ordersOfVIPCustmoers(), new ArrayList<Order>(orders));

	}

	public List<Order> findFoodOrders() {

		return Utils.filter(foodOrders(), new ArrayList<Order>(orders));

	}

	public List<Order> findFurtherOrders(Location courierLocation, Double furtherDistance) {

		return Utils.filter(furtherOrders(courierLocation, furtherDistance), new ArrayList<Order>(orders));

	}

	public List<Order> findTheRestOfOrders(Location courierLocation, Double closeDistanceRange, Double furtherDistance) {

		return Utils.filter(otherOrders(courierLocation, closeDistanceRange, furtherDistance), new ArrayList<Order>(orders));
	}

	public void hideOrders(boolean hasBox, boolean eligibleToFurtherOrders, String[] itemsRequireBox, Location courierLocation, Double furtherDistance){
		
		if(!hasBox) {
			 orders.removeAll(findOrdersRequireGlovoBox(itemsRequireBox)); 
		}
		
		if(!eligibleToFurtherOrders) {
			
			orders.removeAll(findFurtherOrders(courierLocation, furtherDistance));
		}
		
	}
	
	/**
	 * @author Ahmed R. Mazen
	 * to get the orders of the courier according to the filter priorities 
	 * defined in the application.properties and guidelines mentioned in the WORDING file
	 *        
	 */
	public Set<Order> getOrdersForCourier(String courierId) {

		if(courierId == null || courierId.trim().toString().equals("")) {
		   return null;
		}

		Courier courier = courierRepository.findById(courierId);
		if (courier == null) {
			return null;
		}

		boolean hasBox = courier.getBox();
		boolean eligibleToFurtherOrders = (courier.getVehicle().equals(Vehicle.MOTORCYCLE)
				|| courier.getVehicle().equals(Vehicle.ELECTRIC_SCOOTER)) ? true : false;
		Location courierLocation = courier.getLocation();
		
		//Load the properties values
		String[] filters = orderProperties.getFilters();
		System.out.println(String.join(",", filters));
		String[] itemsRequireBox = orderProperties.getITEMS_REQUIRE_GLOVO_BOX();
		Double furtherDistance = orderProperties.getDistance_further_than();
		Double closeDistance = orderProperties.getCloser_distance_in_range();		
 
		
	  
		System.out.println("orders before hiding : " + orders.size());
		
		//start with hiding non eligible orders
		hideOrders(hasBox, eligibleToFurtherOrders, itemsRequireBox, courierLocation, furtherDistance);
		
		System.out.println("after hiding orders : " + orders.size());
		
		
		Set<Order> listOfCourierOrders = new LinkedHashSet<Order>();

		for (int x = 0; x < filters.length; x++) {

			String filterName = filters[x];
			if (filterName != null && !filterName.isEmpty()) {
				if (filterName.equalsIgnoreCase(FilterName.CLOSER_ORDERS.toString())) {
					System.out.println("CLOSER_ORDERS");

					listOfCourierOrders.addAll(findcloserOrders(courierLocation, closeDistance));

				} else if (filterName.equalsIgnoreCase(FilterName.ORDERS_OF_VIP_CUSTMOERS.toString())) {
					System.out.println("ORDERS_OF_VIP_CUSTMOERS");
					listOfCourierOrders.addAll(findOrdersOfVIPCustomer());

				} else if (filterName.equalsIgnoreCase(FilterName.FOOD_ORDERS.toString())) {
					System.out.println("FOOD_ORDERS");

					listOfCourierOrders.addAll(findFoodOrders());

				}

			}
			System.out.println("after if conditions = " + listOfCourierOrders.size());
		}

		// orders other than the previous criteria (which not food, not VIP, not closer, not further 
		// but in between the two ranges (closer distance(1.0) : fartherDistance(5.0))

		System.out.println("other : " + findTheRestOfOrders(courierLocation, closeDistance, furtherDistance).get(0).getId());
		listOfCourierOrders.addAll(findTheRestOfOrders(courierLocation, closeDistance, furtherDistance));
		
		System.out.println("after others conditions = " + listOfCourierOrders.size());
		
		
		
		// orders further than 5km come at the end of list since they are the farther
		if (eligibleToFurtherOrders) {
			listOfCourierOrders.addAll(findFurtherOrders(courierLocation, furtherDistance));
		}
	
		System.out.println("after futher orders conditions = " + listOfCourierOrders.size());

	

		return listOfCourierOrders;
		
	}

}
