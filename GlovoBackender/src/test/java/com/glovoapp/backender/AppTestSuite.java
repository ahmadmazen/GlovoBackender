package com.glovoapp.backender;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.glovoapp.backender.model.CourierRepository;
import com.glovoapp.backender.shared.DistanceCalculator;

@RunWith(Suite.class)
@SuiteClasses({  OrderRepositoryTest.class, CourierRepository.class, DistanceCalculator.class, UtilsTest.class })
public class AppTestSuite {
	 
}
