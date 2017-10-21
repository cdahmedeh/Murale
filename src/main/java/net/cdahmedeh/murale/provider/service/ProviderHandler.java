package net.cdahmedeh.murale.provider.service;

import static com.google.common.collect.Lists.newArrayList;
import static net.cdahmedeh.murale.util.CollectionUtil.random;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.cdahmedeh.murale.domain.Wallpaper;
import net.cdahmedeh.murale.error.ConnectivityException;
import net.cdahmedeh.murale.error.ProviderException;
import net.cdahmedeh.murale.provider.Provider;

public class ProviderHandler {
	public static List<Wallpaper> getWallpapers(final List<Provider> providers, final int count) throws ConnectivityException {
		List<Wallpaper> wallpapers = newArrayList();
		int remaining = count;
		
		while (remaining > 0) {
			try {
				Provider provider = random(providers);
				int random = 1 + ThreadLocalRandom.current().nextInt(remaining);
				List<Wallpaper> results = provider.query(random);
				
				wallpapers.addAll(results);
				remaining -= results.size(); 
			} catch (ProviderException e) {
				continue;
			}
		}
		
		return wallpapers;
	}
}
