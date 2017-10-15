package net.cdahmedeh.murale.provider.parser;

import java.util.List;

import org.yaml.snakeyaml.Yaml;

import net.cdahmedeh.murale.provider.Provider;

public class ProviderParser {
	private static final Yaml yaml;
	
	static {
		yaml = new Yaml();
	}
	
	public static String toConfigurationText(List<Provider> providers) {
		return yaml.dump(providers);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Provider> fromConfigurationText(String text) {
		return yaml.loadAs(text, List.class);
	}
}
