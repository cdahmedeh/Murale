package net.cdahmedeh.murale.provider.field;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static net.cdahmedeh.murale.util.CollectionUtil.map;

import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;

import lombok.RequiredArgsConstructor;
import net.cdahmedeh.murale.provider.Provider;

@RequiredArgsConstructor
public class ProviderField {
	private final Provider provider;
	private final Field field;
	private final FieldInfo info;

	public String name() {
		return info.name();
	}

	public String value() {
		try {
			field.setAccessible(true);

			Object objValue = field.get(provider);
			return objValue == null ? null : objValue.toString();
		} catch (IllegalArgumentException | IllegalAccessException e) {
		}

		return null;
	}

	public Set<String> values() {
		try {
			field.setAccessible(true);

			if (is(EnumSet.class)) {
				EnumSet<?> enumSet = (EnumSet<?>) field.get(provider);
				return map(enumSet, e -> e.toString());
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
		}
		return emptySet();
	}

	public List<String> assignableValues() {
		if (is(Enum.class) || is(EnumSet.class)) {
			Object[] constants = info.enumeration().getEnumConstants();
			return map(ImmutableList.copyOf(constants), c -> c.toString());
		}

		return emptyList();
	}
	
	public boolean isMultiText() {
		return is(List.class);
	}

	public boolean isSingleSelectable() {
		return is(Enum.class);
	}

	public boolean isMultiSelectable() {
		return is(EnumSet.class);
	}

	private boolean is(Class<?> clazz) {
		return clazz.isAssignableFrom(field.getType());
	}
}
