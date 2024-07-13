/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.util;

/**
 * The SortedList class implements the ISortedList interface and knows its size and
 * front fields. It is able to add and remove elements, and keeps them in alphabetical order.
 *
 * @author Carolina Adri Lima
 * @param <E> the generic type
 */
public class SortedList<E extends Comparable<E>> implements ISortedList<E> {

	/** Field that holds the size of the sorted list */
	private int size;
	/** Field that holds the ListNode located at the front */
	private ListNode front;
	
	/**
	 * Constructor for SortedList object 
	 */
	public SortedList() {
		front = null;
		size = 0;
	}
	
	/**
	 * Adds the element to the list in sorted order.
	 * @param element element to add
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element cannot be added 
	 */
	@Override
	public void add(E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		if (contains(element)) {
			throw new IllegalArgumentException("Cannot add duplicate element.");
		}
		if (front == null || element.compareTo(front.data) < 0) {
			front = new ListNode(element, front);
		} else {
			ListNode current = front;
			while (current.next != null && current.next.data.compareTo(element) < 0) {
				current = current.next;
			}
			current.next = new ListNode(element, current.next);
		}
		size++;
	}

	/**
	 * Returns the element from the given index.  The element is
	 * removed from the list.
	 * @param idx index to remove element from
	 * @return element at given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds
	 * 		for the list
	 */
	@Override
	public E remove(int idx) {
		checkIndex(idx);
		E element = null;
		if (idx == 0) {
			element = front.data;
			front = front.next;
		} else {
			ListNode current = front;
			for (int i = 0; i < idx - 1; i++) {
				current = current.next;
			}
			element = current.next.data;
			current.next = current.next.next;
		}
		size--;
		return element;
	}
	
	/**
	 * Method used to check if a given index is valid in the array
	 * @param idx the index to check
	 * @throws IndexOutOfBoundsException if the index is not within a valid range in the array
	 */
	private void checkIndex(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
	}

	/**
	 * Returns true if the element is in the list.
	 * @param element element to search for
	 * @return true if element is found
	 */
	@Override
	public boolean contains(E element) {
		ListNode duplicate = front;
		for (int i = 0; i < size; i++) {
			if(element.equals(duplicate.data)) {
				return true;
			} else {
				duplicate = duplicate.next;
			}
		}
		return false;
	}

	/**
	 * Returns the element at the given index.
	 * @param idx index of the element to retrieve
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds
	 * 		for the list
	 */
	@Override
	public E get(int idx) {
		checkIndex(idx);
		ListNode current = front;
		for (int i = 0; i < idx; i++) {
			current = current.next;
		}
		return current.data;
	}

	/**
	 * Returns the number of elements in the list.
	 * @return number of elements in the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * The ListNode inner class has a Constructor that is used in SortedList
	 * SortedList class
	 * @author Carolina Adri Lima
	 *
	 */
	private class ListNode {
		/** the data the node contains */
		private E data;
		/** the next node in the list of nodes */
		public ListNode next;

		/**
		 * Constructor of the ListNode which uses both data and next as parameters
		 * @param data object of type E to be added to the node
		 * @param next ListNode that symbolizes the next node in the list
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
}
