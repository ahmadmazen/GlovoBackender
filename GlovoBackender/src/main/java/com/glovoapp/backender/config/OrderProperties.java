/**
 * 
 */
package com.glovoapp.backender.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Ahmed R. Mazen this is the bean Mapped to order properties in
 *         application.properties
 *
 */
@Component
@PropertySource("classpath:application.properties")
@Configuration
@ConfigurationProperties("order")
public class OrderProperties {

	private String[] filters;
	public String[] getFilters() {
		return filters;
	}

	public void setFilters(String[] filters) {
		this.filters = filters;
	}


	private String[] ITEMS_REQUIRE_GLOVO_BOX;
	private Double distance_further_than;
	private Double closer_distance_in_range;

	public Double getDistance_further_than() {
		return distance_further_than;
	}

	public void setDistance_further_than(Double distance_further_than) {
		this.distance_further_than = distance_further_than;
	}

	public Double getCloser_distance_in_range() {
		return closer_distance_in_range;
	}

	public void setCloser_distance_in_range(Double closer_distance_in_range) {
		this.closer_distance_in_range = closer_distance_in_range;
	}

	public String[] getITEMS_REQUIRE_GLOVO_BOX() {
		return ITEMS_REQUIRE_GLOVO_BOX;
	}

	public void setITEMS_REQUIRE_GLOVO_BOX(String[] iTEMS_REQUIRE_GLOVO_BOX) {
		ITEMS_REQUIRE_GLOVO_BOX = iTEMS_REQUIRE_GLOVO_BOX;
	}

	
	@Override
	public String toString() {
		return "Order Properties{" +String.join(",",  filters) + "}, Box_Items{"
				+ String.join(",", ITEMS_REQUIRE_GLOVO_BOX) + "}" + ",DistanceParameter{distance_further_than : "
				+ distance_further_than + ", closer_distance_in_range : " + closer_distance_in_range + "}}";
	}

}
