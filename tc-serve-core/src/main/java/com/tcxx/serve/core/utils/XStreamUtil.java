package com.tcxx.serve.core.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.lang.StringUtils;

public class XStreamUtil {

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
     * 从XML字符串反序列化一个对象
     * @param xmlStr
     * @return
     */
    public <T> T xmlToBean(String xmlStr,Class<T> cls){
        if (StringUtils.isBlank(xmlStr)){
            throw new IllegalArgumentException("xmlStr can't be null");
        }
        XStream xStream = new XStream(new XppDriver(new NoNameCoder()));
        XStream.setupDefaultSecurity(xStream);  //避免出现警告:Security framework of XStream not initialized, XStream is probably vulnerable

        xStream.aliasSystemAttribute(null,"class");//去掉class属性
        xStream.autodetectAnnotations(true);//自动探测注解
        xStream.ignoreUnknownElements(); //忽略未知元素

        xStream.allowTypes(new Class[]{cls}); //与XStream.setupDefaultSecurity使用，为显式类型添加安全权限
        xStream.processAnnotations(cls); //若使用注解,在XML字符串映射为Java实体对象时，则一定要调用此方法.

        T object = (T) xStream.fromXML(xmlStr);
        return object;
    }

}
