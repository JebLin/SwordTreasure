package indi.sword.util.jvm.gc;


/**
 * @Description
 *
 *      此代码演示两点：
 *          1.对象可以在被GC的时候自我拯救
 *          2.自救机会只有一次，因为一个对象的finalize()方法最多被系统自动调用一次。
 *
 *      注意：finalize方法尽量避免使用，因为它不是C/C++中的析构函数，它的代价高昂，不确定性大，无法保证哥哥对象的调用顺序。
 *      建议使用try-finally
 * @Author jeb_lin
 * @Date Created in 3:21 PM 27/04/2018
 * @MODIFIED BY
 */
public class _02_FinalizeEscapeGC {

    public static _02_FinalizeEscapeGC SAVE_HOOK = null;

    @Override
    protected void finalize() throws Throwable{
        super.finalize();
        System.out.println("===  finalize ===");
        _02_FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Exception {
        SAVE_HOOK = new _02_FinalizeEscapeGC();

        // 对象第一次拯救自己
        SAVE_HOOK = null;
        System.gc();

        // 因为finalize方法优先级很低，所以暂停0.5秒等待下
        Thread.sleep(500);
        if(SAVE_HOOK != null){
            System.out.println("yes, is alive...");
        } else {
            System.out.println("no , dead");
        }

        System.out.println("=============");
        // finalize 方法只被执行一次，这下救不活了
        SAVE_HOOK = null;
        System.gc();

        // 因为finalize方法优先级很低，所以暂停0.5秒等待下
        Thread.sleep(500);
        if(SAVE_HOOK != null){
            System.out.println("yes, is alive...");
        } else {
            System.out.println("no , dead");
        }




    }
}
