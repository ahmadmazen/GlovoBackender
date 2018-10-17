package com.glovoapp.backender;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.glovoapp.backender.shared.Constants;
import com.glovoapp.backender.shared.Utils;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class UtilsTest {

	@Test
	public void in() {
		assertTrue(Utils.in("pizza", Constants.ITEMS_REQUIRE_GLOVO_BOX));

	}

	@Test
	public void notIn() {
		assertFalse(Utils.notIn("pizza", Constants.ITEMS_REQUIRE_GLOVO_BOX));

	}

	@Test
	public void contains() {

		assertTrue(Utils.contains("An adult flamingo", Constants.ITEMS_REQUIRE_GLOVO_BOX));
	}

	@Test
	public void containsNot() {
		assertFalse(Utils.containsNot("An adult flamingo", Constants.ITEMS_REQUIRE_GLOVO_BOX));
	}

	@Test
	public void testFilter() {

		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
		List<Integer> filterList = Utils.filter(p -> p > 3, list);
		assertTrue(filterList.contains(4));
		assertFalse(filterList.contains(1));

	}
}
