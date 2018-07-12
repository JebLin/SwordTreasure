package indi.sword.util.guava.cache;

import com.google.common.base.Optional;
import com.google.common.cache.*;
import com.google.common.collect.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 2:36 PM 12/07/2018
 * @MODIFIED BY
 */
/*
    GC 清理什么时候发生？
        使用CacheBuilder构建的缓存不会”自动”执行清理和回收工作，也不会在某个缓存项过期后马上清理，也没有诸如此类的清理机制。相反，它会在写操作时顺带做少量的维护工作，或者偶尔在读操作时做——如果写操作实在太少的话。
        这样做的原因在于：如果要自动地持续清理缓存，就必须有一个线程，这个线程会和用户操作竞争共享锁。此外，某些环境下线程创建可能受限制，这样CacheBuilder就不可用了。
        相反，我们把选择权交到你手里。如果你的缓存是高吞吐的，那就无需担心缓存的维护和清理等工作。如果你的 缓存只会偶尔有写操作，而你又不想清理工作阻碍了读操作，那么可以创建自己的维护线程，以固定的时间间隔调用Cache.cleanUp()。ScheduledExecutorService可以帮助你很好地实现这样的定时调度。
 */
public class CacheGC {

    /**
     * 1.限制缓存数量
     **/
    @Test
    public void testSize() throws ExecutionException, InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3) // 设置缓存数量大小
                .build(CacheLoaderCreatetor.createCacheLoader());
        System.out.println(cache.getUnchecked("11"));
        System.out.println(cache.getUnchecked("22"));
        System.out.println(cache.getUnchecked("33"));
        System.out.println(cache.getUnchecked("44")); // 把11给挤掉了

        System.out.println("---");
        System.out.println("cache.size() -> " + cache.size());
        String str1 = cache.getIfPresent("11"); //不会重新加载创建cache
        System.out.println("str1 -> " + str1 + ",最新的把老的替换掉：" + (str1 == null ? "是的" : "否"));

        String str2 = cache.getIfPresent("22"); //不会重新加载创建cache
        System.out.println("str2 获取结果：" + str2);
    }


    /*
         warning: 感觉有bug
         2.权重（感觉用的比较少）不同的缓存项有不同的“权重”（weights）——例如，如果你的缓存值，占据完全不同的内存空间，
         你可以使用CacheBuilder.weigher(Weigher)指定一个权重函数，并且用CacheBuilder.maximumWeight(long)指定最大总重。
         在权重限定场景中，除了要注意回收也是在重量逼近限定值时就进行了，还要知道重量是在缓存创建时计算的，因此要考虑重量计算的复杂度
     */
    @Test
    public void testWeight() throws ExecutionException, InterruptedException {

        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumWeight(32)
                .weigher(new Weigher<String, String>() { //当cache中所有的“weight”总和达到maxKeyWeight时，将会触发“剔除策略”。
                    public int weigh(String key, String value) {
                        int weight = key.length();//individual 权重计算器
                        System.out.println("weight is :" + weight);
                        return weight;
                    }
                })
                .build(CacheLoaderCreatetor.createCacheLoader());
        cache.get("m");
        System.out.println("cacheSize：" + cache.size());
        cache.get("10len_aAAaaaaAAAAA");
        System.out.println("cacheSize：" + cache.size());
        cache.get("lennnnnnnnnn");
        System.out.println("cacheSize：" + cache.size());
        cache.get("nnnn");
        System.out.println("cacheSize：" + cache.size());

        System.out.println("---");
        System.out.println(cache.getIfPresent("m"));
        System.out.println(cache.getIfPresent("len"));
        System.out.println(cache.getIfPresent("11len_aaaaa"));
    }


    /**
     * TTL->time to live
     * Access time => Write/Update/Read
     */
    @Test
    public void testEvictionByAccessTime() throws ExecutionException, InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS) //!!! expireAfterAccess 注意区别，这个有人读的话，也不会销毁  !!!
                .build(CacheLoaderCreatetor.createCacheLoader());

        cache.getUnchecked("JebLin");
        String str = cache.getIfPresent("JebLin"); //不会重新加载创建cache
        System.out.println("被销毁：" + (str == null ? "是的" : "否"));

        TimeUnit.SECONDS.sleep(1);
        str = cache.getIfPresent("JebLin"); //不会重新加载创建cache
        System.out.println("被销毁：" + (str == null ? "是的" : "否"));

        TimeUnit.SECONDS.sleep(1);
        str = cache.getIfPresent("JebLin"); //不会重新加载创建cache
        System.out.println("被销毁：" + (str == null ? "是的" : "否"));

        TimeUnit.SECONDS.sleep(1);
        str = cache.getIfPresent("JebLin"); //不会重新加载创建cache
        System.out.println("被销毁：" + (str == null ? "是的" : "否"));

        TimeUnit.SECONDS.sleep(1);
        str = cache.getIfPresent("JebLin"); //不会重新加载创建cache
        System.out.println("被销毁：" + (str == null ? "是的" : "否"));
    }


    /**
     * Write time => write/update
     */
    @Test
    public void testEvictionByWriteTime() throws ExecutionException, InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)  //!!! expireAfterWrite 注意区别，这个要有人写，才不会销毁  !!!
                .build(CacheLoaderCreatetor.createCacheLoader());

        cache.getUnchecked("JebLin");
        String str = cache.getIfPresent("JebLin"); //不会重新加载创建cache
        System.out.println("被销毁：" + (str == null ? "是的" : "否"));

        TimeUnit.SECONDS.sleep(1);
        str = cache.getIfPresent("JebLin"); //不会重新加载创建cache
        System.out.println("被销毁：" + (str == null ? "是的" : "否"));

        TimeUnit.SECONDS.sleep(1);
        str = cache.getIfPresent("JebLin"); //不会重新加载创建cache
        System.out.println("被销毁：" + (str == null ? "是的" : "否"));

        TimeUnit.SECONDS.sleep(1);
        str = cache.getIfPresent("JebLin"); //不会重新加载创建cache
        System.out.println("被销毁：" + (str == null ? "是的" : "否"));

        TimeUnit.SECONDS.sleep(1);
        str = cache.getIfPresent("JebLin"); //不会重新加载创建cache
        System.out.println("被销毁：" + (str == null ? "是的" : "否"));


    }

    /*
        基于引用的回收（Reference-based Eviction）强（strong）、软（soft）、弱（weak）、虚（phantom）引用-参考
        通过使用弱引用的键、或弱引用的值、或软引用的值，Guava Cache可以把缓存设置为允许垃圾回收：
        CacheBuilder.weakKeys()：使用弱引用存储键。当键没有其它（强或软）引用时，缓存项可以被垃圾回收。
            因为垃圾回收仅依赖恒等式（==），使用弱引用键的缓存用==而不是equals比较键。
        CacheBuilder.weakValues()：使用弱引用存储值。当值没有其它（强或软）引用时，缓存项可以被垃圾回收。
            因为垃圾回收仅依赖恒等式（==），使用弱引用值的缓存用==而不是equals比较值。
        CacheBuilder.softValues()：使用软引用存储值。软引用只有在响应内存需要时，才按照全局最近最少使用的顺序回收。
            考虑到使用软引用的性能影响，我们通常建议使用更有性能预测性的缓存大小限定（见上文，基于容量回收）。使用软引用值的缓存同样用==而不是equals比较值。
     */
    @Test
    public void testWeakKey() throws ExecutionException, InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .weakValues()
//                .weakKeys()
//                .softValues()
                .build(CacheLoaderCreatetor.createCacheLoader());
        cache.getUnchecked("guava");
        cache.getUnchecked("JebLin");

        System.gc();
        TimeUnit.MILLISECONDS.sleep(100);
        String str = cache.getIfPresent("guava"); //不会重新加载创建cache
        System.out.println("被销毁：" + (str == null ? "是的" : "否"));

    }

    @Test
    public void testSoftKey() throws InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .softValues()
                .build(CacheLoaderCreatetor.createCacheLoader());
        int i = 0;
        for (; ; ) {
            cache.put("Alex" + i, new String("Alex" + 1));
            System.out.println("The String [" + (i++) + "] is store into cache.");
            System.out.println("cache size -> " + cache.size());
            TimeUnit.MILLISECONDS.sleep(600);
        }
    }


    /*
        显式清除
        任何时候，你都可以显式地清除缓存项，而不是等到它被回收：
        个别清除：Cache.invalidate(key)
        批量清除：Cache.invalidateAll(keys)
        清除所有缓存项：Cache.invalidateAll()
     */
    @Test
    public void testExplicitRemove() throws ExecutionException, InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2000, TimeUnit.SECONDS)  //!!! expireAfterWrite 注意区别，这个要有人写，才不会销毁  !!!
                .build(CacheLoaderCreatetor.createCacheLoader());

        cache.put("k11", "v11");
        System.out.println(cache.getIfPresent("1"));
        System.out.println(cache.getIfPresent("k11"));
        cache.invalidate("k11");
        System.out.println(cache.getIfPresent("k11"));

        System.out.println("---");
        cache.put("k11", "v11");
        cache.put("k12", "v12");
        cache.put("k13", "v13");
        System.out.println(cache.getIfPresent("k11"));
        System.out.println(cache.getIfPresent("k12"));
        System.out.println(cache.getIfPresent("k13"));
        cache.invalidateAll(Lists.newArrayList("k11", "k12"));
        System.out.println(cache.getIfPresent("k11"));
        System.out.println(cache.getIfPresent("k12"));
        System.out.println(cache.getIfPresent("k13"));
        System.out.println("---");

//        cache.invalidateAll();
        System.out.println(cache.getIfPresent("k13"));
    }


    /*
        移除的监听器
        通过CacheBuilder.removalListener(RemovalListener)，你可以声明一个监听器，
        以便缓存项被移除时做一些额外操作。缓存项被移除时，RemovalListener会获取移除通知[RemovalNotification]，
        其中包含移除原因[RemovalCause]、键和值。
     */
    @Test
    public void testCacheRemovedNotification() {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        RemovalListener<String, String> listener = notification ->
        {
            if (notification.wasEvicted()) {
                RemovalCause cause = notification.getCause();
                System.out.println("remove cause is :" + cause.toString());
                System.out.println("key:" + notification.getKey() + "value:" + notification.getValue());
            }
        };

        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener(listener)// 添加删除监听
                .build(loader);

        cache.getUnchecked("11");
        cache.getUnchecked("22");
        cache.getUnchecked("33");
        cache.getUnchecked("44");
        cache.getUnchecked("55");
        cache.getUnchecked("66");


        /*
            警告：默认情况下，监听器方法是在移除缓存时同步调用的。因为缓存的维护和请求响应通常是同时进行的，
            代价高昂的监听器方法在同步模式下会拖慢正常的缓存请求。在这种情况下，
            你可以使用RemovalListeners.asynchronous(RemovalListener, Executor)把监听器装饰为异步操作。
         */
    }


}
