package gr.uom.android.helpinghand;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static gr.uom.android.helpinghand.BuildConfig.OPEN_WEATHER;

public class WeatherTask extends AsyncTask<String, Void, String> {

    private String Description;

    private final String LOG_TAG = WeatherTask.class.getSimpleName();

    private MainFrag weatherFragment;

    public WeatherTask(MainFrag mainFrag) {
        this.weatherFragment = mainFrag;
    }


    private String formatHighLows(double high, double low) {

        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        return roundedHigh + "°C / " + roundedLow +"°C";
    }

    private List<String> getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException {

        final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";
        final String OWM_DESCRIPTION = "main";

        String day;

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);


        Date date = new Date();
        day = DateFormat.getDateTimeInstance().format(date);


        List<String> resultStrings = new ArrayList<>();
        for (int i = 0; i < weatherArray.length(); i++) {

            String description;
            String highAndLow;

            JSONObject dayForecast = weatherArray.getJSONObject(i);


            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);

            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            double high = temperatureObject.getDouble(OWM_MAX);
            double low = temperatureObject.getDouble(OWM_MIN);

            highAndLow = formatHighLows(high, low);
            resultStrings.add(day + "\n" + description + "\n" + highAndLow);
            Description = description;
        }


        return resultStrings;

    }

    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String forecastJsonStr;
        String weatherFormat = "json";
        int numDays = 1;
        String units = "metric";

        try {
            final String baseUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?";
            final String queryParam = "id";
            final String formatParam = "mode";
            final String unitsParam = "units";
            final String daysParam = "cnt";
            final String apiKeyParam = "APPID";

            Uri builtUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter(queryParam, "734077")
                    .appendQueryParameter(formatParam, weatherFormat)
                    .appendQueryParameter(unitsParam, units)
                    .appendQueryParameter(daysParam, Integer.toString(numDays))
                    .appendQueryParameter(apiKeyParam, OPEN_WEATHER)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        try {
            return getWeatherDataFromJson(forecastJsonStr, numDays).get(0);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            weatherFragment.setWeatherString(result);
            weatherFragment.setWeatherImage(Description);
        }
    }
}