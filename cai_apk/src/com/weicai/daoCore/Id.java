package com.weicai.daoCore;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
/**
 * @author jiuwuerliu@sina.com
 *
 * ±ê×¢ÎªÖ÷¼ü×Ö¶Î
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
 
}