package org.team3.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	/*INSERT TESTS HERE*/
	AddWindowTest.class,
	WindowMainTest.class,
	InventoryWindowTest.class
})

public class RunAllTests {
	public void printAllTests() {
		System.out.println("All Tests");
	}
}
