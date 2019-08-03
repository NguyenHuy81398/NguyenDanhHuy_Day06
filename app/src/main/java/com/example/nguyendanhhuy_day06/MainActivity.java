package com.example.nguyendanhhuy_day06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tvTemp, tvTemp_max, tvTemp_min, tvPressure, tvHumidity;
    List<Weather> weathers;
    RelativeLayout weather;
    String json = "{\"coord\": { \"lon\": 139,\"lat\": 35}, \"weather\": [ { \"id\": 800, \"main\": \"Clear\", \"description\": \"clear sky\", \"icon\": \"01n\" } ], \"base\": \"stations\", \"main\": { \"temp\": 289.92, \"pressure\": 1009, \"humidity\": 92, \"temp_min\": 288.71, \"temp_max\": 290.93 }, \"wind\": { \"speed\": 0.47, \"deg\": 107.538 }, \"clouds\": { \"all\": 2 }, \"dt\": 1560350192, \"sys\": { \"type\": 3, \"id\": 2019346, \"message\": 0.0065, \"country\": \"JP\", \"sunrise\": 1560281377, \"sunset\": 1560333478 }, \"timezone\": 32400, \"id\": 1851632, \"name\": \"Shuzenji\", \"cod\": 200 }";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Thời tiết");

        tvTemp = findViewById(R.id.tvTemp);
        tvTemp_max = findViewById(R.id.tvTemp_max);
        tvTemp_min = findViewById(R.id.tvTemp_min);
        tvPressure = findViewById(R.id.tvPressure);
        tvHumidity = findViewById(R.id.tvHumidity);
        weather = findViewById(R.id.weather);

        weathers = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(json);
            JSONArray jsonArray = object.getJSONArray("weather");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject objectWeather = jsonArray.getJSONObject(i);

                String id = objectWeather.getString("id");
                String main = objectWeather.getString("main");
                String description = objectWeather.getString("description");
                String icon = objectWeather.getString("icon");
                weathers.add(new Weather(id, main, description, icon));

            }
            weather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getBaseContext(),"ID: "+weathers.get(0).getId()+"\nMain: "+weathers.get(0).getMain()+"\nDescription: "+weathers.get(0).getDescription()+"\nIcon: "+weathers.get(0).getIcon(),Toast.LENGTH_LONG).show();
                }
            });

            JSONObject objectMain = object.getJSONObject("main");
            String temp = objectMain.getString("temp");
            String pressure = objectMain.getString("pressure");
            String humidity = objectMain.getString("humidity");
            String temp_min = objectMain.getString("temp_min");
            String temp_max = objectMain.getString("temp_max");

            double temp_C = Double.parseDouble(temp) - 273.15;
            double temp_maxC = Double.parseDouble(temp_max) - 273.15;
            double temp_minC = Double.parseDouble(temp_min) - 273.15;

            tvTemp.setText(String.format("%2.0f", temp_C));
            tvPressure.setText(pressure);
            tvHumidity.setText(humidity);
            tvTemp_max.setText(String.format("%2.0f", temp_maxC));
            tvTemp_min.setText(String.format("%2.0f", temp_minC));
            //"temp": 289.92, "pressure": 1009, "humidity": 92, "temp_min": 288.71, "temp_max": 290.93
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
