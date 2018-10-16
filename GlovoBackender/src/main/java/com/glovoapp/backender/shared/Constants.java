/**
 * 
 */
package com.glovoapp.backender.shared;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ahmed
 * used for maintaining the constants of the application in one file
 */
public interface Constants {
	
	 static List<String> ITEMS_REQUIRE_GLOVO_BOX = Arrays.asList("pizza", "cake", "flamingo");
	 static String[] ITEMS_REQUIRE_GLOVO_BOX_ARR = {"pizza", "cake", "flamingo"};
	 static String[] FILTERS = {"CLOSER_ORDERS", "ORDERS_OF_VIP_CUSTMOERS", "FOOD_ORDERS"};
	 static Double DISTANCE_ONE_KM = 1.0; 
	 static Double DISTANCE_FIVE_KM = 5.0; 
	 
	 static Double DISTANCE_FURTHER_THAN = 5.0;

	 static Double CLOSER_DISTANCE_IN_RANGE = 1.0;

}
