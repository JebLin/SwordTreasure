package indi.sword.util.concurrent;

public class _01_02_TestVolatile {

    private int balance;

    public void updateBalance() {
        try {
            for (int i = 0; i < 100; i++) {
                int temp = (int) (Math.random() * 100);
                System.out.println("begin to updateBalance ..." + temp);
                balance = temp;
                Thread.sleep(1000);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBalance() {
        System.out.println(this.balance);
    }

    public void showValue() {
        balance = 10;
        try {
            for (; ; ) {
                showBalance();
//                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        _01_02_TestVolatile demo = new _01_02_TestVolatile();

        Thread.sleep(1000);

        new Thread(() -> demo.showValue()).start();
        new Thread(() -> demo.updateBalance()).start();
    }
}
