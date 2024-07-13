/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test file that tests the SortedList class
 * @author caahadri
 *
 */
public class SortedListTest {

	/**
	 * Tests add method
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		list.add("Apple");
		list.add("Lemon");
		list.add("Banana");
		assertEquals(3, list.size());
		assertEquals("Apple", list.get(0));
		assertEquals("Banana", list.get(1));
		assertEquals("Lemon", list.get(2));
		
		//Test invalid adding, null and duplicates
		assertThrows(NullPointerException.class, () -> list.add(null));
		assertThrows(IllegalArgumentException.class, () -> list.add("Lemon"));

	}
	
	/**
	 * Tests remove method
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		list.add("Apple");
		list.add("Lemon");
		list.add("Banana");
		assertEquals(3, list.size());
		assertEquals("Banana", list.remove(1));
		assertEquals(2, list.size());
		assertEquals("Apple", list.remove(0));
		assertEquals(1, list.size());

		
	}
	
	/**
	 * Tests checkIndex method
	 */
	@Test
	public void testCheckIndex() {
		SortedList<String> list = new SortedList<String>();
		list.add("Apple");
		list.add("Lemon");
		list.add("Banana");
		assertEquals(3, list.size());
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(4));

	}
	
}
