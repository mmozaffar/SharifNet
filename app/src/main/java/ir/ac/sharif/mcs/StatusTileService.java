// In The Name Of Allah
package ir.ac.sharif.mcs;

import android.service.quicksettings.TileService;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.util.Log;


public class StatusTileService extends TileService {

    @Override
    public void onClick() {
        new Thread(() -> {
            try {
                // Send GET request to the status URL
                URL url = new URL("https://net2.sharif.edu/status");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String responseBody = response.toString();
                if (!responseBody.isEmpty()) {
                    Pattern pattern = Pattern.compile("<td>(.*?)</td>");
                    Matcher matcher = pattern.matcher(responseBody);
                    if (matcher.find()) {
                        String username = matcher.group(1);
                        showToast("Logged in as: " + username);
                    } else {
                        showToast("Not logged in");
                    }
                } else {
                    showToast("Not logged in");
                }
            } catch (Exception e) {
                Log.e("Main", e.toString());
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
