package com.util;

import java.io.Serializable;
import java.util.AbstractList;

/**  
 * 固定长度队列
 * <p>2014年8月9日下午4:18:57 xijiajia</p>
 */
public class FixQueue<T> extends AbstractList<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FixQueue(){
		this.capacity = 3;
        this.queue = newArray(capacity);
        this.head = 0;
        this.tail = 0;
	}
	
    public FixQueue(int capacity) {
        this.capacity = capacity;
        this.queue = newArray(capacity);
        this.head = 0;
        this.tail = 0;
    }

    @SuppressWarnings("unchecked")
    private T[] newArray(int size) {
        return (T[]) new Object[size];
    }

    public boolean add(T o) {
        int newtail = tail % capacity;
        if (tail != 0 && newtail == head){
        	T[] newqueue = newArray(capacity);
        	for(int i = 1; i < queue.length; i++){
        		newqueue[i-1] = get(i);
        	}
        	this.queue = newqueue;
        	queue[tail-1] = o;
        }else{
        	queue[tail] = o;
        	tail = tail + 1;
        }
        return true; // we did add something
    }

    public T remove(int i) {
        if (i != 0)
            throw new IllegalArgumentException("Can only remove head of queue");
        if (head == tail)
            throw new IndexOutOfBoundsException("Queue empty");
        T removed = queue[head];
        queue[head] = null;
        head = (head + 1) % capacity;
        return removed;
    }

    public T get(int i) {
        int size = size();
        if (i < 0 || i >= size) {
            final String msg = "Index " + i + ", queue size " + size;
            throw new IndexOutOfBoundsException(msg);
        }
        int index = (head + i) % capacity;
        return queue[index];
    }
    
    public void reverse(){
    	T[] newqueue = newArray(capacity);
    	for(int i = 0; i < capacity; i++){
    		newqueue[i] = get(capacity - i - 1);
    	}
    	this.queue = newqueue;
    }

    public int size() {
        // Can't use % here because it's not mod: -3 % 2 is -1, not +1.
        int diff = tail - head;
        if (diff < 0){
        	diff += capacity;
        }
        return diff;
    }

    private int capacity;
    private T[] queue;
    private int head;
    private int tail;
    
}
