package org.ivcode.stonks.utils.iterators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class CombinationIterable implements Iterable<List<Object>> {
	
	private final List<Iterable<?>> iterables;
	
	public CombinationIterable(List<Iterable<?>> iterables) {
		this.iterables = iterables;
	}

	@Override
	public Iterator<List<Object>> iterator() {
		return new MyIterator();
	}
	
	private class MyIterator implements Iterator<List<Object>> {

		private int index = 0;
		private Object[] values = new Object[iterables.size()];
		private List<Iterator<?>> iterators = new ArrayList<>();
		
		@Override
		public boolean hasNext() {
			
			boolean hasNext = iterators.isEmpty();
			for(Iterator<?> it : iterators) {
				hasNext |= it.hasNext();
			}
			return index>=0 && hasNext;
		}

		@Override
		public List<Object> next() {
			while(index>=0) { 
				Iterator<?> it = get();
				if(it.hasNext()) {
					values[index] = it.next();
					if(index<iterables.size()-1) {
						index++;
					} else {
						return new ArrayList<>(Arrays.asList(values));
					}
				} else {
					pop();
					index--;
				}
			}
			
			throw new NoSuchElementException();
		}
		
		private Iterator<?> get() {
			return iterators.size()<=index ? push() : iterators.get(index);
		}
		
		private Iterator<?> push() {
			Iterator<?> it = iterables.get(index).iterator();
			iterators.add(it);
			
			return it;
		}
		
		private void pop() {
			iterators.remove(index);
		}
	}
}
