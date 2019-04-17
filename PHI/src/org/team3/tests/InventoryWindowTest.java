package org.team3.tests;
import static org.junit.Assert.*;

import org.junit.Test;
import org.team3.PHI.Item;

public class InventoryWindowTest {

	Item item = new Item(0, "12345","Doritos",3.00,9,0, "chips","2019-03-19", null);
	Item text = new Item(0, "123", "Drink", 0, 0, 0, null, null, null);
	
	@Test
	public void testItemList() {
		assertNotNull(item);
	}

	@Test
	public void testShowItem() {
		Item itemTester = new Item(0, "12345","Doritos",3.00,9,0, "chips","2019-03-19", null);
		assertEquals(itemTester.getProductName(), item.getProductName());
	}


	@Test
	public void testFilterItemList() {
		Item textTester = new Item(0, "123", "Drink", 0, 0, 0, null, null, null);
		assertEquals(textTester.getUPC(), text.getUPC());
	}

}
