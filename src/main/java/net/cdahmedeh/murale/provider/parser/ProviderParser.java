package net.cdahmedeh.murale.provider.parser;

import java.util.List;

import com.google.gson.Gson;

import net.cdahmedeh.murale.provider.Provider;

public class ProviderParser {
	private static Gson gson = new Gson();
	
	public static String toConfigurationText(List<Provider> providers) {
		return gson.toJson(providers);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Provider> fromConfigurationText(String text) {
		return gson.fromJson(text, List.class);
	}
}
