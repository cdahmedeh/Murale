package net.cdahmedeh.murale.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtil {
	public static <T> String join(Collection<T> collection, Function<T, String> mapper, String delimeter) {
		return collection.stream().map(mapper).collect(Collectors.joining(delimeter));
	}

	public static <I, O> List<O> map(List<I> list, Function<I, O> mapper) {
		return list.stream().map(mapper).collect(Collectors.toList());
	}

	public static <I, O> Set<O> map(Set<I> set, Function<I, O> mapper) {
		return set.stream().map(mapper).collect(Collectors.toSet());
	}

	public static <E> E random(List<E> list) {
		int index = ThreadLocalRandom.current().nextInt(list.size());
		return list.get(index);
	}

	public static void truncate(List<?> list, int bound) {
		int size = list.size();
		int cut = bound > size ? size : bound;
		list.subList(cut, size).clear();
	}
}