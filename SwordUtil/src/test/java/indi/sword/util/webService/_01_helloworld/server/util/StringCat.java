package indi.sword.util.webService._01_helloworld.server.util;

import indi.sword.util.webService._01_helloworld.server.bean.Cat;

import java.util.HashSet;
import java.util.Set;

/**
 * @Decription 将 CXF 无法处理的 Map 类型转换成 Set，其实这也是
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/16 16:24
 */
public class StringCat {

    public static class Entry{
        private String key;
        private Cat value;

        public Entry() {
        }

        public Entry(String key, Cat value) {
            this.key = key;
            this.value = value;
        }

        public Cat getValue() {
            return value;
        }

        public void setValue(Cat value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key='" + key + '\'' +
                    ", value=" + value +
                    '}';
        }
    }
    private Set<Entry> entrySet = new HashSet<Entry>();

    public Set<Entry> getEntrySet() {
        return entrySet;
    }

    public void setEntrySet(Set<Entry> entrySet) {
        this.entrySet = entrySet;
    }

    @Override
    public String toString() {
        return "StringCat{" +
                "entrySet=" + entrySet +
                '}';
    }
}
