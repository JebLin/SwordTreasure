package indi.sword.util.webService._01_helloworld.server.util;

import indi.sword.util.webService._01_helloworld.server.bean.Cat;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;


/**
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/16 16:38
 */
/*
    该转换器就负责完成 StringCat 与 Map<String,Cat>的相互转换.
    XmlAdapter<StringCat,Map<String,Cat>>
 */
public class FkXmlAdapter extends XmlAdapter<StringCat,Map<String,Cat>> {

    /**
     * 解编
     * @param stringCat
     * @return
     * @throws Exception
     */
    @Override
    public Map<String,Cat> unmarshal(StringCat stringCat) throws Exception {

        Map<String,Cat> result = new HashMap<>();
        stringCat.getEntrySet().forEach(entry -> {
            result.put(entry.getKey(),entry.getValue());
        });

        return result;
    }

    /**
     * @Decription 整编  Map<String,Cat> Map类型是要处理的类型
     * @Author: rd_jianbin_lin
     * @Date : 2017/12/16 16:39
     */
    @Override
    public StringCat marshal(Map<String,Cat> map) throws Exception {
        StringCat sc = new StringCat();
        for (String key:map.keySet()){
            sc.getEntrySet().add(new StringCat.Entry(key,map.get(key)));
        }
        return sc;
    }
}
