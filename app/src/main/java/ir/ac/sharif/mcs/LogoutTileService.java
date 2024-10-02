// In The Name Of Allah
package ir.ac.sharif.mcs;

import android.os.Handler;
import android.os.Looper;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LogoutTileService extends TileService {

    @Override
    public void onClick() {
        new Thread(() -> {
            try {
                // Send POST request to the logout URL
                URL url = new URL("https://net2.sharif.edu/logout");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String data = "fakedata";

                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    showToast("Logout successful");
                } else {
                    showToast("Logout failed");
                }
            } catch (Exception e) {
                showToast("Error");
            } finally {
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
