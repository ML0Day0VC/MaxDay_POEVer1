/**
 * @author Max Day
 * Created At: 2023/04/05
 */
package API.Credentials;

class EraserThread implements Runnable {
    private volatile boolean stop;

    /**
     * This is the thread that is responsible for executing on character input when a character of the password is typed. The character is backspaced and then replaced with an asterisks (*) until the password has been fully typed
     */
    public void run() {
        stop = true;
        while (stop) {
            System.out.print("\b*"); // the character \b is used to backspace the current character and then the * is printed to replace it
            try { // try catch is needed here as u don't rly want to throw any exceptions in a thread that is doing so little in this case
                Thread.sleep(1);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // flag to stop the thread
    public void stopMasking() {
        stop = false;
    }
}

