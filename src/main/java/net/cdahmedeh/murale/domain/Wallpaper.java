package net.cdahmedeh.murale.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Wallpaper {
	@Getter @Setter
	private String url;
	
	@Getter @Setter
	private String title;
	
	@Getter @Setter
	private String author;
}
