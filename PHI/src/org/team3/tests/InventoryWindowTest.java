
package org.team3.tests;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.team3.PHI.InventoryWindow;
import org.team3.PHI.Item;

public class InventoryWindowTest {

	@Test
	public void testItemList() {
		ArrayList<Item> itemlist = InventoryWindow.itemList();
		Item item = new Item(12345, "Doritos", 3.00,4, "Food", "3-21-2019");
		itemlist.add(item);
		/*Because of there already being items in the itemList from adding them in the 
		actual program, the index of the item we add in this test is 10.*/
		assertEquals("UPC did not match",item.getUPC(),itemlist.get(10).getUPC());
		
		
	}

	@Test
	public void testItemList2() {
		ArrayList<Item> itemlist = InventoryWindow.itemList();
		Item item = new Item(12345, "Doritos", 3.00,4, "Food", "3-21-2019");
		itemlist.add(item);
		assertNotEquals("Price is an int",3,itemlist.get(10).getPrice());
		
		
	}
	
	@Test
	public void testItemList3() {
		ArrayList<Item> itemlist = InventoryWindow.itemList();
		Item item = new Item(12345, "Doritos", 3.00,4, "Food", "3-21-2019");
		Item item2 = new Item(125, "Lays", 3.50,7, "Food", "3-21-2019");
		itemlist.add(item);
		itemlist.add(item2);

		assertEquals("Not the correct amount of items stored",12, itemlist.size());
	}
	
	

	

	

}

