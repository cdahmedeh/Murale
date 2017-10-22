package net.cdahmedeh.murale.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor(staticName = "of")
public class Resolution {
	@Getter
	private final int width;
	
	@Getter
	private final int height;
}
