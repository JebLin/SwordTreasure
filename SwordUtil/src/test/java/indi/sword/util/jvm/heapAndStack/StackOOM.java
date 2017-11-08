package indi.sword.util.jvm.heapAndStack;

public class StackOOM {

    /**
     * @param args
     */

    private int stackLength = 1;
    private void dontStop(){
        while(true){
            try{Thread.sleep(1000);}catch(Exception err){}
        }
    }

    public void stackLeakByThread(){
        while(true){
            Thread t = new Thread(new Runnable(){

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    dontStop();
                }

            });
            t.start();
            stackLength++;
        }
    }

    public static void main(String[] args) throws Throwable{
        // TODO Auto-generated method stub
        StackOOM oom = new StackOOM();
        try{
            oom.stackLeakByThread();
        }catch(Throwable err){
            System.out.println("Stack length:" + oom.stackLength);
            throw err;
        }

    }
}
