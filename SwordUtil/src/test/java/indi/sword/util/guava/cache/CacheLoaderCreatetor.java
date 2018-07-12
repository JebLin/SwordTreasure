package indi.sword.util.guava.cache;

import com.google.common.base.Optional;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 3:50 PM 12/07/2018
 * @MODIFIED BY
 */
public class CacheLoaderCreatetor {
    public static com.google.common.cache.CacheLoader<String, String> createCacheLoader() {
        return new com.google.common.cache.CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                System.out.println("加载创建key:" + key);
                return new String(key);
            }
        };
    }

    public static com.google.common.cache.CacheLoader<String, String> createNUllCacheLoader() {
        return new com.google.common.cache.CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                System.out.println("加载创建key:" + key);
                if (key.equals("null")) {
                    return null;
                }
                return new String(key);
            }
        };
    }

    public static com.google.common.cache.CacheLoader<String, Optional<String>> createNullValueUseOptionalCacheLoader() {
        return new com.google.common.cache.CacheLoader<String, Optional<String>>() {
            @Override
            public Optional<String> load(String key) throws Exception {
                System.out.println("加载创建key:" + key);
                if (key.equals("null")) {
                    return Optional.fromNullable(null);
                } else {
                    return Optional.fromNullable( new String(key));
                }
            }


        };
    }
}
