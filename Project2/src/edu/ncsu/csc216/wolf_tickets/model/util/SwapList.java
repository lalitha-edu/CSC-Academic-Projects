/**
 * 
 */
package edu.ncsu.csc216.wolf_tickets.model.util;

/**
 * Child class SwapList that works to build a list funtionality
 * that can add, remove, move an element up, down, front or backside.
 * Child class also contains ability to get an element and get the size of the list.
 * 
 * @param <E> the generic type
 * @author Lalitha Edupuganti
 *
 */
public class SwapList<E> implements ISwapList<E> {

	/**
	 * The constant used to set the list's original capacity and size
	 */
	private static final int INITIAL_CAPACITY = 10;
	
	/**
	 * The generic list used in the methods to add, remove elements
	 * along with the move methods
	 */
	private E[] list;
	
	/**
	 * The field value for the size of the list
	 */
	private int size;
	
	/**
	 * Constructor for the swapList class that creates a list with the given inital capacity
	 */
	@SuppressWarnings("unchecked")
	public SwapList() {
		list = (E[]) new Object[INITIAL_CAPACITY];
		size = 0;
	}
	
	/**
	 * Adds the element to the end of the list.
	 * @param element element to add
	 * @throws NullPointerException if element is null
	 */
	@Override
	public void add(E element) {
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		checkCapacity();
		list[size++] = element;
		
	}
	
	/**
	 * Private method to check the capacity of the list and increase
	 * the capacity of the list as needed
	 */
	@SuppressWarnings("unchecked")
	private void checkCapacity() {
		if (size == list.length) {
			E[] growArray = (E[]) new Object[list.length * 2];
			
			for (int i = 0; i < list.length; i++) {
				growArray[i] = list[i];
			}
			
			list = growArray;
		}
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
		checkindex(idx);
		E element = list[idx];
		for (int i = idx; i < size - 1; i++) {
			list[i] = list[i + 1];
		}
		
		size--;
		return element;
	}
	
	/**
	 * Private method to check if the selected index is in bounds and is valid
	 * @param idx the idx that is to be checked for the lower and upper bounds
	 */
	private void checkindex(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
	}

	/**
	 * Moves the element at the given index to index-1.  If the element is
	 * already at the front of the list, the list is not changed.
	 * @param idx index of element to move up
	 * @throws IndexOutOfBoundsException if the idx is out of bounds
	 * 		for the list
	 */
	@Override
	public void moveUp(int idx) {
		checkindex(idx);
		
		if(idx != 0) {
			E previousElement = list[idx - 1];
			
			list[idx - 1] = list[idx];
			list[idx] = previousElement;
		}
	}

	/**
	 * Moves the element at the given index to index+1.  If the element is
	 * already at the end of the list, the list is not changed.
	 * @param idx index of element to move down
	 * @throws IndexOutOfBoundsException if the idx is out of bounds
	 * 		for the list
	 */
	@Override
	public void moveDown(int idx) {
		checkindex(idx);
		
		if (idx != size - 1) {
			E previousElement = list[idx + 1];
			
			list[idx + 1] = list[idx];
			list[idx] = previousElement;
		}
		
	}

	/**
	 * Moves the element at the given index to index 0.  If the element is
	 * already at the front of the list, the list is not changed.
	 * @param idx index of element to move to the front
	 * @throws IndexOutOfBoundsException if the idx is out of bounds
	 * 		for the list
	 */
	@Override
	public void moveToFront(int idx) {
		checkindex(idx);
		
		E element = list[idx];
		
		for (int i = idx - 1; i >= 0; i--) {
			list[i + 1] = list[i];
		}
		
		list[0] = element;
		
	}

	/**
	 * Moves the element at the given index to size-1.  If the element is
	 * already at the end of the list, the list is not changed.
	 * @param idx index of element to move to the back
	 * @throws IndexOutOfBoundsException if the idx is out of bounds
	 * 		for the list
	 */
	@Override
	public void moveToBack(int idx) {
		checkindex(idx);
		
		E element = list[idx];
		for (int i = idx; i < size - 1; i++) {
			list[i] = list[i + 1];
		}
		
		list[size - 1] = element;
		
	}

	/**
	 * Returns the element at the given index.
	 * @param idx index of the element to retrieve
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if the idx is out of bounds
	 * for the list
	 */
	@Override
	public E get(int idx) {
		
		checkindex(idx);
		
		return list[idx];
	}

	/**
	 * Returns the number of elements in the list.
	 * @return number of elements in the list
	 */
	@Override
	public int size() {
		
		return this.size;
	}
	
}
