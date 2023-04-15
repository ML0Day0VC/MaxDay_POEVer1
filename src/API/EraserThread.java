/**
 * @author Max Day
 * Created At: 2023/04/05
 */
package API;

class EraserThread implements Runnable {
    private volatile boolean stop;



    public void run () {
        stop = true;
        while (stop) {
            System.out.print("\b*");
            try {
                Thread.sleep(1);
            } catch(InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void stopMasking() {
        stop = false;
    }
}


/*
class EraserThread implements Runnable {
    private boolean stop;
    public EraserThread(String prompt) {
        System.out.print(prompt);
    }
    public void run () {
        stop = true;
        while (stop) {
            System.out.print("\010*");
            try {
                Thread.currentThread().sleep(1);
            } catch(InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
    public void stopMasking() {
        this.stop = false;
    }
}

 */


