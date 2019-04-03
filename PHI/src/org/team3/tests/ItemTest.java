package org.team3.tests;
import static org.junit.Assert.*;

import org.junit.Test;
import org.team3.PHI.Item;

public class ItemTest {
	Item item = new Item("12345","Doritos",3.00,9,"chips","2019-03-19");
	@Test
	public void itemNotNull() {
		assertNotNull(item);
	}
	@Test
	public void testGetUPC() {
		assertEquals("12345", item.getUPC());
	}

	@Test
	public void testGetProductName() {
		assertEquals("Doritos", item.getProductName());
	}

	@Test
	public void testGetQuantinty() {
		assertEquals(9, item.getQuantity());
	}

	@Test
	public void testGetCategory() {
		assertEquals("chips", item.getCategory());
	}

	@Test
	public void testGetdate() {
		assertEquals("2019-03-19", item.getDate());
	}

	@Test
	public void testGetPrice() {
		assertEquals(Double.valueOf(3.00), item.getPrice());
	}

}
