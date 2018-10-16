package com.glovoapp.backender.model;

import com.glovoapp.backender.dto.Courier;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class CourierRepository {
	private static final String COURIERS_FILE = "/couriers.json";
	private static final List<Courier> couriers;

	static {
		try (Reader reader = new InputStreamReader(CourierRepository.class.getResourceAsStream(COURIERS_FILE))) {
			Type type = new TypeToken<List<Courier>>() {
			}.getType();
			couriers = new Gson().fromJson(reader, type);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	List<Courier> filter(Predicate<Courier> predicate, List<Courier> listOfCouriers) {

		return listOfCouriers.stream().filter(predicate).collect(Collectors.<Courier>toList());
	}

	public Courier findById(String courierId) {
		return couriers.stream().filter(courier -> courierId.equals(courier.getId())).findFirst().orElse(null);
	}

	public List<Courier> findAll() {
		return new ArrayList<>(couriers);
	}
}
