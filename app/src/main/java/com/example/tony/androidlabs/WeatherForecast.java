//package com.example.tony.androidlabs;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.Xml;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class WeatherForecast extends Activity {
//    protected static final String NAME = "WeatherForecastActivity";
////    protected static final String URL_IMAGE = "";
//    private ImageView ImageView;
//    private TextView currentTempView;
//    private TextView minTempView;
//    private TextView maxTempView;
//    private ProgressBar proBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_weather_forecast);
//         ImageView = (ImageView) findViewById(R.id.currentForecast);
//         currentTempView = (TextView) findViewById(R.id.currenttemperature);
//         minTempView = (TextView) findViewById(R.id.mintemperature);
//         maxTempView = (TextView) findViewById(R.id.maxtemperature);
//         proBar = (ProgressBar) findViewById(R.id.progressBar);
//        proBar.setVisibility(View.VISIBLE);
//        proBar.setMax(100);
//        new ForecastQuery().execute(null, null, null);
//    }
//
//    private class ForecastQuery extends AsyncTask<String, Integer, String> {
//        private final String windSpeed = null;
//        private String minTemp = null;
//        private String maxTemp = null;
//        private String currTemp = null;
//        private String iconname = null;
//        private String location = null;
//        private Bitmap Bit = null;
//
//
//        // ... == [] the same as calling array instance
//        @Override
//        protected String doInBackground(String... strings) {
//            InputStream iStream = null;
//            try {
//                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(10000 /* milliseconds */);
//                conn.setConnectTimeout(15000 /* milliseconds */);
//                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//                // Starts the query
//                conn.connect();
//                iStream = conn.getInputStream();
////                iStream.read(buffer);
//                XmlPullParser parser = Xml.newPullParser();
//                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
//                parser.setInput(iStream, null);
//
//                int eventType = parser.getEventType();
//                boolean set = false;
//                while (eventType != XmlPullParser.END_DOCUMENT) {
//                    if (eventType == XmlPullParser.START_TAG) {
//                        if (parser.getName().equalsIgnoreCase("current")) {
//                            set = true;
//                        } else if (parser.getName().equalsIgnoreCase("city") && set) {
//                            location = parser.getAttributeValue(null, "name");
//                        } else if (parser.getName().equalsIgnoreCase("temperature") && set) {
//                            currTemp = parser.getAttributeValue(null, "value");
//                            // call to put 25 on GUI
//                            // or publishProgress(values 20, 25,30,35...) cause we have an array
//                            publishProgress(25);
//                            minTemp = parser.getAttributeValue(null, "min");
//                            // call tp put 50 on GUI
//                            publishProgress(50);
//                            maxTemp = parser.getAttributeValue(null, "max");
//                            // call to put 75 on GUI
//                            publishProgress(75);
//                        } else if (parser.getName().equalsIgnoreCase("weather") && set) {
//                            iconname = parser.getAttributeValue(null, "icon") + ".png";
//                            File file = getBaseContext().getFileStreamPath(iconname+".png");
//                            if (!file.exists()) {
//                                       saveImage(iconname);
//                            } else {
//                                Log.i(NAME, "Saved icon, " + iconname + " is displayed.");
//                                try {
//                                    FileInputStream in = new FileInputStream(file);
//                                    Bit = BitmapFactory.decodeStream(in);
//                                } catch (FileNotFoundException e) {
//                                    Log.i(NAME, "Saved icon, " + Bit + " is not found.");
//                                }
//                            }
//                            publishProgress(100);
//
//                        }
//                    } else if (eventType == XmlPullParser.END_TAG) {
//                        if (parser.getName().equalsIgnoreCase("current"))
//                            set = false;
//                    }
//                    eventType = parser.next();
//                }
//
//            } catch (IOException e) {
//                Log.i(NAME, "IOException: " + e.getMessage());
//            } catch (XmlPullParserException f) {
//                Log.i(NAME, "XmlPullParserException: " + f.getMessage());
//            } finally {
//                if (iStream != null)
//                    try {
//                        iStream.close();
//                    } catch (IOException e) {
//                        Log.i(NAME, "IOException: " + e.getMessage());
//                    }
//                return null;
//            }
//
//        }
//        @SuppressLint({"DefaultLocale", "SetTextI18n"})
//        @Override
//        protected void onPostExecute(String s) {
//// we can post now to GUI
//
//            super.onPostExecute(s);
//            minTempView.setText("Min " + String.format("%.1f", Double.parseDouble(minTemp)) + "\u00b0");
//            maxTempView.setText("Max " + String.format("%.1f", Double.parseDouble(maxTemp)) + "\u00b0");
//            currentTempView.setText("Current " + String.format("%.1f", Double.parseDouble(currTemp)) + "\u00b0");
//            ImageView.setImageBitmap(Bit);
//            proBar.setVisibility(View.INVISIBLE);
//        }
//
//        // when GUI is rerady apply the updATE
//        @Override
//        protected void onProgressUpdate(Integer... value) {
//            super.onProgressUpdate(value);
//            proBar.setProgress(value[0]);
//        }
//
//        private void saveImage(String fname     ) {
//            HttpURLConnection connection = null;
//            try {
//                URL url = new URL("http://openweathermap.org/img/w/" + fname);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//                int responseCode = connection.getResponseCode();
//                if (responseCode == 200) {
//                    Bit = BitmapFactory.decodeStream(connection.getInputStream());
//                    FileOutputStream outputStream = openFileOutput(fname, Context.MODE_PRIVATE);
//                    Bit.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    outputStream.flush();
//                    outputStream.close();
//                    Log.i(NAME, "Weather icon, " + fname + " is downloaded and displayed.");
//                } else
//                    Log.i(NAME, "Can't connect to the weather icon for downloading.");
//            } catch (Exception e) {
//                Log.i(NAME, "weather icon download error: " + e.getMessage());
//            } finally {
//                if (connection != null) {
//                    connection.disconnect();
//                }
//            }
//
//        }
//    }
//}
//
package com.example.tony.androidlabs;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.String.format;

public class WeatherForecast extends Activity {

    protected static final String NAME = "WeatherForecast";
    private TextView wind;
    private TextView currentTextView;
    private TextView minTextView;
    private TextView maxTextView;
    private TextView targetLocation;
    private ImageView weatherImageView;
    private ProgressBar normProgBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherImageView = (ImageView) findViewById(R.id.currentForecast);

        wind = (TextView) findViewById(R.id.windspeed);
        currentTextView = (TextView) findViewById(R.id.currenttemperature);

        minTextView = (TextView) findViewById(R.id.mintemperature);
        maxTextView = (TextView) findViewById(R.id.maxtemperature);
        targetLocation = (TextView) findViewById(R.id.target);

        normProgBar = (ProgressBar) findViewById(R.id.progressBar);
        normProgBar.setVisibility(View.VISIBLE);
//        normProgBar.setMax(100);

        new ForecastQuery().execute(null, null, null);

    }


    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String windSpeedddddd = null;
        private String currentTemp = null;
        private String minTemp = null;
        private String maxTemp = null;
        private String iconFilename = null;
        private Bitmap weatherImage = null;
        private String currLocation = null;

        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream = null;
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(10000 /* milliseconds */);
//                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                inputStream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(inputStream, null);

                int eventType = parser.getEventType();
                boolean set = false;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {

                        if (parser.getName().equalsIgnoreCase("current")) {
                            set = true;
                        } else if (parser.getName().equalsIgnoreCase("city") && set) {
                            currLocation = parser.getAttributeValue(null, "name");
                        } else if (parser.getName().equalsIgnoreCase("temperature") && set) {
                            currentTemp = parser.getAttributeValue(null, "value");
                            publishProgress(25);
                            minTemp = parser.getAttributeValue(null, "min");
                            publishProgress(50);
                            maxTemp = parser.getAttributeValue(null, "max");
                            publishProgress(75);
                        }else if (parser.getName().equalsIgnoreCase("speed") && set) {
                            windSpeedddddd = parser.getAttributeValue(null,"value");
                            publishProgress(100);

                        }
                        else if (parser.getName().equalsIgnoreCase("weather") && set) {
                            iconFilename = parser.getAttributeValue(null, "icon") + ".png";
                            File file = getBaseContext().getFileStreamPath(iconFilename);
                            if (!file.exists()) {
                                saveImage(iconFilename);
                            } else {
                                Log.i(NAME, "Saved icon, " + iconFilename + " is displayed.");
                                try {
                                    FileInputStream in = new FileInputStream(file);
                                    weatherImage = BitmapFactory.decodeStream(in);
                                } catch (FileNotFoundException e) {
                                    Log.i(NAME, "Saved icon, " + iconFilename + " is not found.");
                                }
                            }
                            publishProgress(100);

                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (parser.getName().equalsIgnoreCase("current"))
                            set = false;
                    }
                    eventType = parser.next();
                }

            } catch (IOException e) {
                Log.i(NAME, "IOException: " + e.getMessage());
            } catch (XmlPullParserException e) {
                Log.i(NAME, "XmlPullParserException: " + e.getMessage());
            } finally {
                if (inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        Log.i(NAME, "IOException: " + e.getMessage());
                    }
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            normProgBar.setProgress(values[0]);
            if (values[0] == 100) {

            }
        }

        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            wind.setText("WIND SPEED IS : " + format("%.1f", Double.parseDouble(windSpeedddddd)) + "\u00b0");
            minTextView.setText("Min " + format("%.1f", Double.parseDouble(minTemp)) + "\u00b0");
            currentTextView.setText("Current " + format("%.1f", Double.parseDouble(currentTemp)) + "\u00b0");
            maxTextView.setText("Max " + format("%.1f", Double.parseDouble(maxTemp)) + "\u00b0");
            targetLocation.setText("Weather report for " + currLocation);
            weatherImageView.setImageBitmap(weatherImage);
            normProgBar.setVisibility(View.INVISIBLE);
        }

        private void saveImage(String fname) {
            HttpURLConnection connection = null;
            try {
                URL url = new URL("http://openweathermap.org/img/w/" + fname);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    weatherImage = BitmapFactory.decodeStream(connection.getInputStream());
                    FileOutputStream outputStream = openFileOutput(fname, Context.MODE_PRIVATE);
                    weatherImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Log.i(NAME, "Weather icon, " + fname + " is downloaded and displayed.");
                } else
                    Log.i(NAME, "Can't connect to the weather icon for downloading.");
            } catch (Exception e) {
                Log.i(NAME, "weather icon download error: " + e.getMessage());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
}