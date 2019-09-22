package indi.sword.util.concurrent;

import java.util.function.Supplier;

/**
 * @Description
 * @Author jeb_lin
 */
//每个人都有一个账户，每次买东西都会进行扣费，每个人花的是自己的钱，每个人的账户存款都不一样。一个人就是一条线程，账户存款就是线程内的局部变量。
public class _22_TestThreadLocal {
    public static void main(String[] args) {
        wallet wallet = new wallet();
        // 3.  3个线程共享wallet，各自消费
        new Thread(new TaskDemo(wallet), "A").start();
        new Thread(new TaskDemo(wallet), "B").start();
        new Thread(new TaskDemo(wallet), "C").start();
    }

    private static class TaskDemo implements Runnable { //一个人就像一个线程
        private wallet wallet;

        public TaskDemo(wallet wallet) {
            this.wallet = wallet;
        }

        public void run() {
            try{
                for (int i = 0; i < 3; i++) {
                    wallet.spendMoney();

                    // 4. 每个线程打出3个
                    System.out.println(Thread.currentThread().getName() + " --> balance["
                            + wallet.getBalance() + "] ," + "cost [" + wallet.getCost() + "]");
                }
            }finally {
                wallet.removeAll();
            }

        }
    }

    private static class wallet { //一个人就像一个线程
        // 1.通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值
        private static ThreadLocal<Integer> balance = ThreadLocal.withInitial(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 100;
            }
        }); // 假设初始账户有100块钱


        private static ThreadLocal<Integer> cost = ThreadLocal.withInitial(() -> 0); // 假设初始账户有100块钱
//        private static ThreadLocal<Integer> balance = new ThreadLocal<>();
//        private static ThreadLocal<Integer> cost = new ThreadLocal<>();

        public int getBalance() {
            return balance.get();
        }

        public int getCost() {
            return cost.get();
        }

        // 2。 消费
        public void spendMoney() {
            int balanceNow = balance.get();
            int costNow = cost.get();
            balance.set(balanceNow - 10); // 每次花10块钱
            cost.set(costNow + 10);
        }


        public void removeBalance(){
            balance.remove();
        }

        public void removeCost(){
            cost.remove();
        }

        public void removeAll(){
            removeBalance();
            removeCost();
        }
    }
}
