package org.team3.tests;

import static org.junit.Assert.*;

import javax.swing.JTable;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;
import org.team3.PHI.TableColumnAdjuster;

import junit.framework.Assert;

public class TableColumnAdjusterTest {
	JTable tester = new JTable(3,3);
	TableColumnAdjuster test = new TableColumnAdjuster(tester);
	

	@Test
	public void testgetColumnWidth() {
		if(SystemUtils.IS_OS_MAC) {
			assertEquals(12, test.getColumnHeaderWidth(1));
		}else if (SystemUtils.IS_OS_WINDOWS) {
			assertEquals(10, test.getColumnHeaderWidth(1));
		}
	}
	@Test
	public void testAdjustColumn() {
		test.adjustColumn(1);
		
		if(SystemUtils.IS_OS_MAC) {
			assertEquals(12, test.getColumnHeaderWidth(1));
		}else if (SystemUtils.IS_OS_WINDOWS) {
			assertEquals(10, test.getColumnHeaderWidth(1));
		}
	}

	
	
}
