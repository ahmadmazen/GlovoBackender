package com.glovoapp.backender.model;

import java.util.Arrays;
import java.util.function.Predicate;

import com.glovoapp.backender.dto.Location;
import com.glovoapp.backender.dto.Order;
import com.glovoapp.backender.shared.DistanceCalculator;
import com.glovoapp.backender.shared.Utils;

public class OrderPredicates {
	
	protected Predicate<Order> glovoBoxRequired(String[] itemsRequireBox) {

		return order -> Utils.contains(order.getDescription(), Arrays.asList(itemsRequireBox));

	}

	protected Predicate<Order> closerOrders(Location courierLocation, Double closeDistanceRange) {

		return order -> {
			Double distance = DistanceCalculator.calculateDistance(courierLocation, order.getPickup());
//			System.out.println("distance = " + distance);
//			System.out.println(closeDistanceRange.compareTo(distance) >= 0 ? true : false);
			return closeDistanceRange.compareTo(distance) >= 0 ? true : false;
		};

	}

	protected Predicate<Order> ordersOfVIPCustmoers() {

		return order -> order.getVip() == true;

	}

	protected Predicate<Order> foodOrders() {

		return order -> order.getFood() == true;

	}

	protected Predicate<Order> furtherOrders(Location courierLocation, Double furtherDistance) {

		return order -> {
			Double distance = DistanceCalculator.calculateDistance(courierLocation, order.getPickup());
//			System.out.println("distance from further predicate= " + distance);
//			System.out.println(furtherDistance.compareTo(distance) < 0 ? true : false);

			return furtherDistance.compareTo(distance) < 0 ? true : false;
		};
	}

	protected Predicate<Order> otherOrders(Location courierLocation, Double closeDistanceRange, Double furtherDistance) {

		// the negation of other priorities's predicates will be the rest of orders..
		
		return (closerOrders(courierLocation, closeDistanceRange).or(ordersOfVIPCustmoers()).or(foodOrders())
				.or(furtherOrders(courierLocation, furtherDistance))).negate();
	}


}
