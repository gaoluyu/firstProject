package androidServer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidServer.util.Constants;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented 
public @interface Authenticate {
	public int value() default Constants.Role.operator;

	// true: 每个角色严格按照自己的职责，权限高的角色不能执行权限低的功能
	// false: 权限高的角色可以执行权限低的功能
	public boolean isStrict() default false;
}
