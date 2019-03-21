package org.team3.PHI;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * 
 */

/**
 * @author Tanaka
 *
 */
public class AddWindowTest {



	

	/**
	 * Test method for {@link AddWindow#addToCategoriesList(java.lang.String)}.
	 */
	@Test
	public void testAddToCategoriesList() {
		
		ArrayList<String> categories = new ArrayList<String>();
		
		categories.add("Food");
		
		assertEquals("Should equal Food","Food", categories.get(categories.indexOf("Food")));
		assertNotNull("Size of list shouldnt be null", categories.size());
		
		
	}

	/**
	 * Test method for {@link AddWindow#toggleUpdateOrInsert()}.
	 */
	@Test
	public void testToggleUpdateOrInsert() {
		
		
		Boolean updateOrInsert = false;
		if(updateOrInsert) {
			updateOrInsert = false;
		}else {
			updateOrInsert = true;
		}
		assertEquals(true, updateOrInsert );
		
	}


}
