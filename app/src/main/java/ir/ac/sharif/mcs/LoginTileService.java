// In The Name Of Allah
package ir.ac.sharif.mcs;

import android.os.Handler;
import android.os.Looper;
import android.service.quicksettings.TileService;
import android.widget.Toast;
import android.content.Context;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import android.content.SharedPreferences;
import java.net.URL;

public class LoginTileService extends TileService {
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String PREFS_NAME = "networkPrefs";


    @Override
    public void onClick() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(KEY_USERNAME, null);
        String password = sharedPreferences.getString(KEY_PASSWORD, null);
        if (username == null || password == null) {
            // Notify user to set username and password
            showToast("Missingcredentials");
            return;
        }
        new Thread(() -> {
            try {
                // Send POST request to the login URL
                URL url = new URL("https://net2.sharif.edu/login");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String data = "username="+username+"&password="+password;

                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    showToast("Login successful");
                } else {
                    showToast("Login failed");
                }
            } catch (Exception e) {
                showToast("Error");
            }finally {
                TileHelper.stopAppWithDelay();
            }
        }).start();
    }

    private void showToast(String message) {
        new Handler(Looper.getMainLooper()).post(() ->
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show()
        );
    }
}
