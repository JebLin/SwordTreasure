package indi.sword.util.guava.base;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import indi.sword.util.guava.cache.CacheLoaderCreatetor;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 3:59 PM 12/07/2018
 * @MODIFIED BY
 */
public class CreateDemo {


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * 测试为空的情况
     *
     * @throws InterruptedException
     * @throws Exception
     */
    @Test
    public void testLoadNullValue() throws InterruptedException, Exception {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .softValues()
                .build(CacheLoaderCreatetor.createNUllCacheLoader());
        thrown.expect(CacheLoader.InvalidCacheLoadException.class);
        try {
            //不存在创建了一个null的value，不被允许的！
            cache.getUnchecked("null");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadNullValueUseOptional() {
        LoadingCache<String, Optional<String>> cache = CacheBuilder.newBuilder().
                build(CacheLoaderCreatetor.createNullValueUseOptionalCacheLoader());
        Optional<String> strOptional = cache.getUnchecked("guava");
        if (strOptional.isPresent()) {
            String str = strOptional.get();
            System.out.println("str：" + str.toString());
        }

        Optional<String> str = cache.getUnchecked("null");
        if (!str.isPresent()) {
            System.out.println("str is null");
            String def = cache.getUnchecked("null").or(new String("default"));
            System.out.println("str default：" + def.toString());
        }
    }


    @Test
    public void testCachePreLoad() {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);
        Map<String, String> preData = new HashMap<String, String>() {
            {
                put("guava", "guava");
                put("guava1", "guava1");
            }
        };
        cache.putAll(preData); //提前插入
        System.out.println("cache size :" + cache.size());
        System.out.println("guava:" + cache.getUnchecked("guava"));
    }

}
