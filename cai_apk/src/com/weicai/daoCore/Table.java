package com.weicai.daoCore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
/**
 *
 *  ��ע���ݿ����
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface  Table {
    /**
     * @return ���ݿ����
     */
    String name();
}
