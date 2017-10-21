package net.cdahmedeh.murale.provider;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import net.cdahmedeh.murale.domain.Wallpaper;
import net.cdahmedeh.murale.error.ConnectivityException;
import net.cdahmedeh.murale.error.ProviderException;

public abstract class Provider {
	@Getter
	private final String uuid = UUID.randomUUID().toString();
	
	public abstract String getName();
	
	public abstract String getSearch();
	
	public abstract List<Wallpaper> query(final int count) throws ConnectivityException, ProviderException;
}