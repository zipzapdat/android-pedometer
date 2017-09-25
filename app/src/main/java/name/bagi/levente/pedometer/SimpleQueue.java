package name.bagi.levente.pedometer;

import java.util.LinkedList;

public class SimpleQueue<E> {

	private LinkedList<E> list = new LinkedList<E>();
	private Integer size;

	public SimpleQueue(Integer size) {
		this.size = size;
	}

	/**
	 * Puts object in queue.
	 */
	public void put(E o) {
		//Incase queue reached max size - remove the elements
		if (list.size() == this.size)
			list.removeFirst();
		
		list.addLast(o);
	}

	/**
	 * Returns an element (object) from queue.
	 * 
	 * @return element from queue or <code>null</code> if queue is empty
	 */
	public E get() {
		if (list.isEmpty()) {
			return null;
		}
		return list.removeFirst();
	}

	/**
	 * Returns all elements from the queue and clears it.
	 */
	public Object[] getAll() {
		Object[] res = new Object[list.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = list.get(i);
		}
		list.clear();
		return res;
	}

	/**
	 * Returns <code>true</code> if queue is empty, otherwise <code>false</code>
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * Returns queue size.
	 */
	public int size() {
		return list.size();
	}
}
