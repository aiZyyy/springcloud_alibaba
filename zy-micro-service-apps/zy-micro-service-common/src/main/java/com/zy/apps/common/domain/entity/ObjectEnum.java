/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zy.apps.common.domain.entity;

/**
 * @Title: ObjectEnum.class
 * @author ZY
 * @date 2019-7-9 11:44:29
 * @version V1.0
 * @Description:对象类型枚举
 */
public enum ObjectEnum {

    Byte(java.lang.Byte.class),
    Boolean(java.lang.Boolean.class),
    Short(java.lang.Short.class),
    Integer(java.lang.Integer.class),
    Float(java.lang.Float.class),
    Double(java.lang.Double.class),
    String(java.lang.String.class),
    Date(java.util.Date.class),
    Long(java.lang.Long.class),
    List(java.util.List.class);
    private Class<?> clazz;

    private ObjectEnum(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public static ObjectEnum findByClass(Class<?> clazz) throws RuntimeException {
        ObjectEnum result = null;
        ObjectEnum[] fcs = ObjectEnum.values();
        for (ObjectEnum fe : fcs) {
            if (clazz.equals(fe.getClazz())) {
                result = fe;
                break;
            }
        }
        return result;
    }
}
