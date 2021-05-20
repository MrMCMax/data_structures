package mrmcmax.data_structures.linear;

import java.util.Iterator;

public class EraserLinkedList<E> {
	
	public static class Node<E> {
		public E data;
		public Node<E> previous;
		public Node<E> next;
		
		public Node(E data) {
			this(data, null, null);
		}
		
		public Node(E data, Node<E> previous) {
			this(data, previous, null);
		}
		
		public Node(E data, Node<E> previous, Node<E> next) {
			this.data = data;
			this.previous = previous;
			this.next = next;
		}
		
		public void setNext(Node<E> next) {
			this.next = next;
		}
		
		@Override
		public String toString() {
			return data.toString();
		}
	}
	
	private Node<E> first, last;
	private int size;
	
	public EraserLinkedList() {
		first = last = null;
		size = 0;
	}
	
	public void add(E item) {
		addAndReturnPointer(item);
	}
	
	public Node<E> addAndReturnPointer(E item) {
		Node<E> next;
		if (last == null) {
			//first is null too
			next = new Node<>(item);
			first = next;
			last = next;
		} else {
			next = new Node<>(item, last);
			last.setNext(next);
			last = next;
		}
		size++;
		return next;
	}
	
	/**
	 * Precondition: the object has not been removed from the list before.
	 * The user is responsible for this behaviour.
	 * The pointer object should be disposed after the operation.
	 * @param pointer the pointer to the node to remove.
	 */
	public E remove(Node<E> pointer) {
		if (pointer == first) {
			return poll();
		} else if (pointer == last) {
			return pollLast();
		} else { //No need to update first or last. It is in the middle.
			E data = pointer.data;
			pointer.previous.next = pointer.next;
			pointer.next.previous = pointer.previous;
			size--;
			return data;
		}
	}
	
	public boolean isEmpty() {
		return first == null; //or size=0
	}
	
	public int size() { return size; }
	
	/**
	 * IFF isEmpty() == false
	 * @return the element at the start of the list
	 */
	public E peek() {
		return first.data;
	}
	
	/**
	 * Returns and deletes the element at the start of the list.
	 * The user is responsible for not keeping a pointer to this element
	 * after the operation.
	 * IFF isEmpty() == false
	 * @return the element at the start of the list
	 */
	public E poll() {
		E data = first.data;
		if (first == last) {
			first = null;
			last = null;
		} else {
			first = first.next;
			first.previous = null;
		}
		size--;
		return data;
	}
	
	/**
	 * Returns the element at the end of the list.
	 * IFF isEmpty() == false
	 * @return
	 */
	public E peekLast() {
		return last.data;
	}
	
	/**
	 * Removes and returns the element at the end of this list.
	 * The user is responsible for not keeping a pointer to this element
	 * after the operation.
	 * IFF isEmpty() == false
	 * @return
	 */
	public E pollLast() {
		if (last == first) return poll();
		//Else, it has a previous value
		E ret = last.data;
		last = last.previous;
		last.next = null;
		size--;
		return ret;
	}
	
	/**
	 * Clears the data structure. All stored pointers should be deleted.
	 */
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			Node<E> current = first;
			
			@Override
			public boolean hasNext() {
				return current != null;
			}

			@Override
			public E next() {
				E data = current.data;
				current = current.next;
				return data;
			}
		};
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		if (!isEmpty()) {
			Iterator<E> it = iterator();
			sb.append(it.next());
			while (it.hasNext()) {
				sb.append(", ").append(it.next());
			} 
		}
		sb.append(']');
		return sb.toString();
	}
}
