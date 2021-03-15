package org.ivcode.stonks.utils.iterators;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class TypedCombinationIterable<T> implements Iterable<T> {

	private final Function<List<Object>, T> func;
	private final Iterable<List<Object>> iterable;
	
	public TypedCombinationIterable(Function<List<Object>, T> func, Iterable<?>... iterables) {
		this.func = func;
		this.iterable = new CombinationIterable(Arrays.asList(iterables));
	}

	@Override
	public Iterator<T> iterator() {
		return new MyIterator<T>(func, iterable.iterator());
	}

	public static <T> Function<List<Object>, T> createConstructorFunction(Class<T> type, Class<?>... parameterTypes)
			throws ReflectiveOperationException {
		Constructor<T> c = type.getDeclaredConstructor(parameterTypes);
		return new ConstructorFunction<>(c);
	}

	private static class ConstructorFunction<T> implements Function<List<Object>, T> {
		private final Constructor<T> constructor;

		public ConstructorFunction(Constructor<T> constructor) {
			this.constructor = constructor;
		}

		@Override
		public T apply(List<Object> t) {
			Object[] values = t.toArray(new Object[t.size()]);
			try {
				return constructor.newInstance(values);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {

				throw new IllegalStateException(e);
			}
		}
	}

	private static class MyIterator<T> implements Iterator<T> {

		private final Function<List<Object>, T> func;
		private final Iterator<List<Object>> it;

		public MyIterator(Function<List<Object>, T> func, Iterator<List<Object>> it) {
			this.func = func;
			this.it = it;
		}

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public T next() {
			return func.apply(it.next());
		}

	}
}
