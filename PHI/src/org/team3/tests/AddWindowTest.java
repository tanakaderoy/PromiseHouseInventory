package org.team3.tests;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

/**
 * @author Team 3
 *
 */
public class AddWindowTest {
	BufferedReader reader;
	ArrayList<String> categories = new ArrayList<String>(Arrays.asList("Food","Toiletries","Supplies"));
	
	@Before
	public void setUp() {
		
		
		try {
			reader = new BufferedReader(new FileReader("categories.txt"));
			String line = reader.readLine();
			while (line != null) {
				
				
				// read next line
				if((!line.equals("") && !categories.contains(line))) {
					System.out.println(line);
					categories.add(line);
				}
				line=reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void readerNotNull() {
		assertNotNull(reader);
	}


	@Test
	public void testAddFileLineToCat() {
		
		
		
		assertNotNull(categories);
	}
	@Test
	public void categoriesEquals() {
		assertEquals("Food", categories.get(0));
	}
	@Test
	public void clearCategories() {
		categories.clear();
		assertEquals(0, categories.size());
	}




	

	
	@Test
	public void testAddToCategoriesList() {
		
		ArrayList<String> categories = new ArrayList<String>();
		
		categories.add("Food");
		
		assertEquals("Should equal Food","Food", categories.get(categories.indexOf("Food")));
		assertNotNull("Size of list shouldnt be null", categories.size());
		
		
	}

	
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
