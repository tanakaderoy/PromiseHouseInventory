 package org.team3.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.team3.PHI.WindowMain;

public class WindowMainTest {
	@Before
	public void setUp() {
		WindowMain.MainWindow();
	}
	@Test
	public void testScanProductBtnClick() {
		WindowMain.btnScanProduct.doClick();
		assertEquals("Main Window was not closed.", false, WindowMain.frame.isVisible());
	}
	@Test
	public void testViewInventoryBtnClick() {
		WindowMain.btnViewInventory.doClick();
		assertEquals("Main Window was not closed.", false, WindowMain.frame.isVisible());
	}
	@Test
	public void testInventoryReportsBtnClick() {
		WindowMain.btnInventoryReports.doClick();
		assertEquals("Main Window was not closed.", false, WindowMain.frame.isVisible());
	}

}
