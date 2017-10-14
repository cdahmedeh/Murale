package net.cdahmedeh.murale.provider.field;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface FieldInfo {
	static enum EmptyEnum {}

	String name() default "";
	Class<? extends Enum<?>> enumeration() default EmptyEnum.class;
}
