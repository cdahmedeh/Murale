package net.cdahmedeh.murale.provider;

import static org.apache.commons.lang3.reflect.FieldUtils.getFieldsWithAnnotation;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.ImmutableList;

import lombok.Getter;
import net.cdahmedeh.murale.domain.Wallpaper;
import net.cdahmedeh.murale.error.ConnectivityException;
import net.cdahmedeh.murale.error.ProviderException;
import net.cdahmedeh.murale.provider.field.FieldInfo;
import net.cdahmedeh.murale.provider.field.ProviderField;
import net.cdahmedeh.murale.util.CollectionUtil;

public abstract class Provider {
	@Getter
	private final String uuid = UUID.randomUUID().toString();
	
	public abstract String getName();
	
	public abstract List<Wallpaper> query(final int count) throws ConnectivityException, ProviderException;
	
	public List<ProviderField> getFields() {
		Field[] configFields = getFieldsWithAnnotation(this.getClass(), FieldInfo.class);
		return CollectionUtil.map(
				ImmutableList.copyOf(configFields), 
				cf -> new ProviderField(this, cf, cf.getAnnotation(FieldInfo.class))
		);
	}
}