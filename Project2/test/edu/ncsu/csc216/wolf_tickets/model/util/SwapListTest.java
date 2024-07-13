package edu.ncsu.csc216.wolf_tickets.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test file that tests the SwapList class
 * @author Lalitha Edupuganti
 *
 */
class SwapListTest {
	
	/**
	 * Tests add method
	 */
	@Test
	void testAdd() {
		SwapList<String> list = new SwapList<String>();
		list.add("Mango");
		list.add("Watermelon");
		list.add("Tomato");
		list.add("Lettuce");
		assertEquals(4, list.size());
		assertEquals("Mango", list.get(0));
		assertEquals("Watermelon", list.get(1));
		assertEquals("Tomato", list.get(2));
		assertEquals("Lettuce", list.get(3));
		
		//Test invalid adding, null 
		assertThrows(NullPointerException.class, () -> list.add(null));
	}

	/**
	 * Tests remove method
	 */
	@Test
	void testRemove() {
		SwapList<String> list = new SwapList<String>();
		list.add("Mango");
		list.add("Watermelon");
		list.add("Tomato");
		list.add("Lettuce");
		assertEquals(4, list.size());
		assertEquals("Mango", list.get(0));
		assertEquals("Watermelon", list.get(1));
		assertEquals("Tomato", list.get(2));
		assertEquals("Lettuce", list.get(3));
		
		list.remove(1);
		assertEquals(3, list.size());
		assertEquals("Mango", list.get(0));
		assertEquals("Tomato", list.get(1));
		assertEquals("Lettuce", list.get(2));
		
		list.remove(2);
		assertEquals(2, list.size());
		assertEquals("Mango", list.get(0));
		assertEquals("Tomato", list.get(1));
		
		list.remove(0);
		assertEquals(1, list.size());
		assertEquals("Tomato", list.get(0));
		
	}

	/**
	 * Tests MoveUp method
	 */
	@Test
	void testMoveUp() {
		SwapList<String> list = new SwapList<String>();
		list.add("Mango");
		list.add("Watermelon");
		list.add("Tomato");
		list.add("Lettuce");
		
		assertEquals(4, list.size());
		assertEquals("Mango", list.get(0));
		assertEquals("Watermelon", list.get(1));
		assertEquals("Tomato", list.get(2));
		assertEquals("Lettuce", list.get(3));
		
		list.moveUp(1);
		assertEquals(4, list.size());
		assertEquals("Watermelon", list.get(0));
		assertEquals("Mango", list.get(1));
		assertEquals("Tomato", list.get(2));
		assertEquals("Lettuce", list.get(3));
		
		list.moveUp(0);
		assertEquals(4, list.size());
		assertEquals("Watermelon", list.get(0));
		assertEquals("Mango", list.get(1));
		assertEquals("Tomato", list.get(2));
		assertEquals("Lettuce", list.get(3));
		
		
		list.moveUp(3);
		assertEquals(4, list.size());
		assertEquals("Watermelon", list.get(0));
		assertEquals("Mango", list.get(1));
		assertEquals("Lettuce", list.get(2));
		assertEquals("Tomato", list.get(3));
		
	}

	/**
	 * Tests MoveDown method
	 */
	@Test
	void testMoveDown() {
		SwapList<String> list = new SwapList<String>();
		list.add("Mango");
		list.add("Watermelon");
		list.add("Tomato");
		list.add("Lettuce");
		
		assertEquals(4, list.size());
		assertEquals("Mango", list.get(0));
		assertEquals("Watermelon", list.get(1));
		assertEquals("Tomato", list.get(2));
		assertEquals("Lettuce", list.get(3));
		
		list.moveDown(0);
		assertEquals(4, list.size());
		assertEquals("Watermelon", list.get(0));
		assertEquals("Mango", list.get(1));
		assertEquals("Tomato", list.get(2));
		assertEquals("Lettuce", list.get(3));
		
		list.moveDown(3);
		assertEquals(4, list.size());
		assertEquals("Watermelon", list.get(0));
		assertEquals("Mango", list.get(1));
		assertEquals("Tomato", list.get(2));
		assertEquals("Lettuce", list.get(3));
		
		
		list.moveDown(2);
		assertEquals(4, list.size());
		assertEquals("Watermelon", list.get(0));
		assertEquals("Mango", list.get(1));
		assertEquals("Lettuce", list.get(2));
		assertEquals("Tomato", list.get(3));
	}

	/**
	 * Tests MoveToFront method
	 */
	@Test
	void testMoveToFront() {
		SwapList<String> list = new SwapList<String>();
		list.add("Mango");
		list.add("Watermelon");
		list.add("Tomato");
		list.add("Lettuce");
		
		assertEquals(4, list.size());
		assertEquals("Mango", list.get(0));
		assertEquals("Watermelon", list.get(1));
		assertEquals("Tomato", list.get(2));
		assertEquals("Lettuce", list.get(3));
		
		list.moveToFront(3);
		assertEquals(4, list.size());
		assertEquals("Lettuce", list.get(0));
		assertEquals("Mango", list.get(1));
		assertEquals("Watermelon", list.get(2));
		assertEquals("Tomato", list.get(3));
		
		list.moveToFront(2);
		assertEquals(4, list.size());
		assertEquals("Watermelon", list.get(0));
		assertEquals("Lettuce", list.get(1));
		assertEquals("Mango", list.get(2));
		assertEquals("Tomato", list.get(3));
		
		list.moveToFront(0);
		assertEquals(4, list.size());
		assertEquals("Watermelon", list.get(0));
		assertEquals("Lettuce", list.get(1));
		assertEquals("Mango", list.get(2));
		assertEquals("Tomato", list.get(3));
		
		
	}

	/**
	 * Tests MoveToBack method
	 */
	@Test
	void testMoveToBack() {
		SwapList<String> list = new SwapList<String>();
		list.add("Mango");
		list.add("Watermelon");
		list.add("Tomato");
		list.add("Lettuce");
		
		assertEquals(4, list.size());
		assertEquals("Mango", list.get(0));
		assertEquals("Watermelon", list.get(1));
		assertEquals("Tomato", list.get(2));
		assertEquals("Lettuce", list.get(3));
		
		list.moveToBack(0);
		assertEquals(4, list.size());
		assertEquals("Watermelon", list.get(0));
		assertEquals("Tomato", list.get(1));
		assertEquals("Lettuce", list.get(2));
		assertEquals("Mango", list.get(3));
		
		list.moveToBack(3);
		assertEquals(4, list.size());
		assertEquals("Watermelon", list.get(0));
		assertEquals("Tomato", list.get(1));
		assertEquals("Lettuce", list.get(2));
		assertEquals("Mango", list.get(3));
		
		list.moveToBack(1);
		assertEquals(4, list.size());
		assertEquals("Watermelon", list.get(0));
		assertEquals("Lettuce", list.get(1));
		assertEquals("Mango", list.get(2));
		assertEquals("Tomato", list.get(3));
		
		
	}

	
	/**
	 * Tests add method, checks if capacity is increased
	 * If list is at 10 elements, capacity should accomodate and increase
	 * for the 11th element
	 */
	@Test
	void testAddCheckCapacity() {
		SwapList<Integer> list = new SwapList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		list.add(10);
		
		assertEquals(11, list.size());
		
		//Test invalid adding, null 
		assertThrows(NullPointerException.class, () -> list.add(null));
	}

}
