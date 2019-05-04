package arwith.myplayerhub;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

import arwith.myplayerhub.Overwatch.OverwatchStats;

public class OverwatchStatistics {

    public OverwatchStatistics() {

    }

    public OverwatchStats downloadUserData(String platform, String region, String name, String tag) throws IOException {
        try {
            URL url = new URL("https://ow-api.com/v1/stats/"+platform+"/"+region+"/"+name+"-"+tag+"/profile/");

            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if(inputStream == null) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            String result = buffer.toString();

            OverwatchStats overwatchStats = new Gson().fromJson(result, OverwatchStats.class);

            Log.d("RESULT", result);

            reader.close();

            return overwatchStats;
        } catch (IOException e) {
            Log.e("Request", "Error ", e);
            return null;
        }
    }
}
