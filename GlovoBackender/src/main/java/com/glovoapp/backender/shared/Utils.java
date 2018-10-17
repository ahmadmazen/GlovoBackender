package com.glovoapp.backender.shared;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.glovoapp.backender.config.OrderProperties;
import com.glovoapp.backender.dto.Order;

/**
 * @author Ahmed R. Mazen Some helper methods
 */
public class Utils {
	@Autowired
	

	public static boolean in(Object value, List<String> valueList) {
		if ((value == null) || (valueList == null)) {
			return false;
		}
		AtomicInteger check = new AtomicInteger(0);
		valueList.forEach(val -> {
			if (val != null && value.toString().equalsIgnoreCase(val)) {
				check.getAndIncrement();
			}
		});

		return check.intValue() > 0;
	}

	public static boolean notIn(Object value, List<String> valueList) {
		return !in(value, valueList);
	}

	public static boolean contains(Object value, List<String> valueList) {
		if ((value == null) || (valueList == null)) {
			return false;
		}
		AtomicInteger check = new AtomicInteger(0);
		valueList.forEach(val -> {
			if (val != null && value.toString().toUpperCase().contains(val.toUpperCase())) {
				check.getAndIncrement();
			}
		});
		return check.intValue() > 0;
	}

	public static boolean containsNot(Object value, List<String> valueList) {
		return !contains(value, valueList);
	}
	
	public static <T> List<T> filter(Predicate<T> predicate, List<T> list) {

		return list.stream().filter(predicate).collect(Collectors.<T>toList());
	}

}
