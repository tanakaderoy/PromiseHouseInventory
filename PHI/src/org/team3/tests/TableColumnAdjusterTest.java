package org.team3.tests;

import static org.junit.Assert.*;

import javax.swing.JTable;

import org.junit.Test;
import org.team3.PHI.TableColumnAdjuster;

import junit.framework.Assert;

public class TableColumnAdjusterTest {
	JTable tester = new JTable(3,3);
	TableColumnAdjuster test = new TableColumnAdjuster(tester);
	

	@Test
	public void testgetColumnWidth() {
		assertEquals(10, test.getColumnHeaderWidth(1));
	}
	@Test
	public void testAdjustColumn() {
		test.adjustColumn(1);
		assertEquals(10, test.getColumnHeaderWidth(1));
	}

	
	
}
