package com.glovoapp.backender;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ OrderRepositoryTest.class, OrderPredicatesTest.class, CourierRepositoryTest.class,
		DistanceCalculatorTest.class, UtilsTest.class })
public class AppTestSuite {

}
