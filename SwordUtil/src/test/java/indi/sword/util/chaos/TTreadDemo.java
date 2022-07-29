package indi.sword.util.chaos;

/**
 * @author jeb_lin
 * 10:52 PM 2020/2/18
 */
public class TTreadDemo implements Runnable {

    private TT tt;

    public TTreadDemo(TT tt){
        this.tt = tt;
    }

    @Override
    public void run() {


        System.out.println(TT.getThreadId());
    }
}
