package com.glovoapp.backender;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.glovoapp.backender.shared.Constants;
import com.glovoapp.backender.shared.Utils;

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

}
