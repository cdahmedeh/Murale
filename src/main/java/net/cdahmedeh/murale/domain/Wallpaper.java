package net.cdahmedeh.murale.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.cdahmedeh.murale.provider.Provider;

@ToString
public class Wallpaper {
	@Getter @Setter
	private String id;
	
	@Getter @Setter
	private String url;
	
	@Getter @Setter
	private String origin;
	
	@Getter @Setter
	private String title;
	
	@Getter @Setter
	private String author;
	
	@Getter @Setter
	private Provider provider;
	
}
