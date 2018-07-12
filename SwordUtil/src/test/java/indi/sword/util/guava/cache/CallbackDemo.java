package indi.sword.util.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 12:18 PM 12/07/2018
 * @MODIFIED BY
 */
/*
    refresh机制：
    - LoadingCache.refresh(K) 在生成新的value的时候，旧的value依然会被使用。
    - CacheLoader.reload(K, V) 生成新的value过程中允许使用旧的value
    - CacheBuilder.refreshAfterWrite(long, TimeUnit) 自动刷新cache
 */
public class CallbackDemo {
    public static void main(String[] args) throws Exception{
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .build();

        cache.put("java","cache java");
        try {

            String result = cache.get("java", new Callable<String>() {
                @Override
                public String call() {
                    return "default java";
                }
            });

            System.out.println(result);
            Thread.sleep(1000);

            String result2 = cache.get("java", () -> "default java");
            System.out.println(result2);

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
