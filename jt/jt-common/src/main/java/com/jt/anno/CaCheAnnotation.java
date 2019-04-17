package com.jt.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)	//要求注解在方法中使用
@Retention(RetentionPolicy.RUNTIME)	//运行期有效
public @interface CaCheAnnotation {
	int index() default 0;	//默认为第一个参数
	CACHE_TYPE cacheType() default CACHE_TYPE.FIND;
	
	//自定义枚举类型
	enum CACHE_TYPE {
		FIND,UPDATE;
	}
}
