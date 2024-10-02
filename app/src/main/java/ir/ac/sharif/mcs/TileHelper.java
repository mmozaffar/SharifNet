// In The Name Of Allah
package ir.ac.sharif.mcs;

import android.os.Handler;
import android.os.Looper;


public class TileHelper {

    public static void stopAppWithDelay() {
        // Create a Handler to delay the exit by 5 seconds (5000 milliseconds)
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Call System.exit() after the delay
                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(0);
            }
        }, 2000); // 5000 milliseconds = 5 seconds
    }
}