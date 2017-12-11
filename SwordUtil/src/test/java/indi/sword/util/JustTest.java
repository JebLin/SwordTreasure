package indi.sword.util;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

public class JustTest {
    public static void main(String[] args) {
        Map<String,String> map = new HashedMap();
        map.putAll(null);
        System.out.println(map.size());
    }
}
