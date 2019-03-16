package com.tcxx.serve.core.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.util.Objects;

public class XStreamUtil {

    private static XStream xStream = null;

    static{
        if(xStream == null){
            xStream = new XStream(new XppDriver(new NoNameCoder()));
            xStream.aliasSystemAttribute(null,"class");//去掉class属性
            xStream.autodetectAnnotations(true);//自动探测注解
            xStream.ignoreUnknownElements(); //忽略未知元素

            XStream.setupDefaultSecurity(xStream);  //避免出现以下警告:Security framework of XStream not initialized, XStream is probably vulnerable
        }
    }

    /**
     *  通过静态内部类实现单例模式
     */
    private static class LazyHolder {
        private static final XStreamUtil INSTANCE = new XStreamUtil();
    }

    private XStreamUtil(){}

    /**
     * 取得XStreamUtil实例
     * @return
     */
    public static final XStreamUtil getInstance(){
        return LazyHolder.INSTANCE;
    }

    /**
     * 与XStream.setupDefaultSecurity使用，为显式类型添加安全权限，
     * 避免抛出Security framework of XStream not initialized,XStream is probably vulnerable异常信息
     * @param classes
     */
    public void setXStreamAllowTypes(Class[] classes){
        xStream.allowTypes(classes);
    }

    /**
     * 若使用注解,在XML字符串映射为Java实体对象时，则一定要调用此方法.
     * @param types
     */
    public void setXStreamProcessAnnotations(Class[] types){
        xStream.processAnnotations(types);
    }

    /**
     *  把实体对象转换为XML字符串
     * @param bean
     * @return
     */
    public String beanToXml(Object bean){
        if (Objects.isNull(bean)){
            throw new IllegalArgumentException("bean can't be null");
        }
        String xmlStr = xStream.toXML(bean);
        return xmlStr;
    }

    /**
     * 从XML字符串反序列化一个对象
     * @param xmlStr
     * @return
     */
    public <T> T xmlToBean(String xmlStr,Class<T> cls){
        if(null == xmlStr || xmlStr.length() == 0){
            return null;
        }

        T object = (T) xStream.fromXML(xmlStr);
        return object;
    }

}
