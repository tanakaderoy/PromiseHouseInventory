package org.team3.tests;
import static org.junit.Assert.*;

import org.junit.Test;
import org.team3.PHI.Item;

public class InventoryWindowTest {

	Item item = new Item(12345,"Doritos",3.00,9,"chips","2019-03-19");
	Item text = new Item(123, "Drink", null, 0, null, null);
	
	@Test
	public void testItemList() {
		assertNotNull(item);
	}

	@Test
	public void testShowItem() {
		Item itemTester = new Item(12345,"Doritos",3.00,9,"chips","2019-03-19");
		assertEquals(itemTester.getProductName(), item.getProductName());
	}


	@Test
	public void testFilterItemList() {
		Item textTester = new Item(123, "Drink", null, 0, null, null);
		assertEquals(textTester.getUPC(), text.getUPC());
	}

}
