package com.example.weathermap.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weathermap.Models.Convert;
import com.example.weathermap.Models.getfromapi.CityWeather;
import com.example.weathermap.Models.WeatherApi;
import com.example.weathermap.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{
    private TextView londonTemp,madridTemp,sydneyTemp,moscowTemp,
                        tokyoTemp,newyorkTemp,carioTemp;
    private TextView londonName,madridName,sydneyName,moscowName,
                        tokyoName,newyorkName,carioName;
    private Button btnLondon,btnMadrid,btnSydney,btnMoscow,
                        btnTokyo,btnNewYork,btnCairo;
    // Keys
    private static final String LONDON_CITY_KEY = "328328";
    private static final String MADRID_CITY_KEY = "308526";
    private static final String SYDNEY_CITY_KEY = "22889";
    private static final String MOSCOW_CITY_KEY = "294021";
    private static final String TOKYO_CITY_KEY = "226396";
    private static final String NEWYORK_CITY_KEY = "349727";
    private static final String CAIRO_CITY_KEY = "127164";

    //Google Connects
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findIds();
        pasteData();

        if(isServicesOK())
            init();
    }

    private void init(){
        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void findIds(){
        btnLondon = findViewById(R.id.btnLondon);
        londonName = findViewById(R.id.london_name);
        londonTemp = findViewById(R.id.london_temperature);

        btnMadrid = findViewById(R.id.btnMadrid);
        madridName = findViewById(R.id.madrid_name);
        madridTemp = findViewById(R.id.madrid_temperature);

        btnSydney = findViewById(R.id.btnSydney);
        sydneyName = findViewById(R.id.sydney_name);
        sydneyTemp = findViewById(R.id.sydney_temperature);

        btnMoscow = findViewById(R.id.btnMoscow);
        moscowName = findViewById(R.id.moscow_name);
        moscowTemp = findViewById(R.id.moscow_temperature);

        btnTokyo = findViewById(R.id.btnTokyo);
        tokyoName = findViewById(R.id.tokyo_name);
        tokyoTemp = findViewById(R.id.tokyo_temperature);

        btnNewYork = findViewById(R.id.btnNewYork);
        newyorkName = findViewById(R.id.newyork_name);
        newyorkTemp = findViewById(R.id.newyork_temperature);

        btnCairo = findViewById(R.id.btnCairo);
        carioName = findViewById(R.id.cairo_name);
        carioTemp = findViewById(R.id.cairo_temperature);

    }

    private void pasteData(){

        pasteTemperature(londonTemp,LONDON_CITY_KEY,btnLondon,londonName);

        pasteTemperature(madridTemp,MADRID_CITY_KEY,btnMadrid,madridName);

        pasteTemperature(sydneyTemp,SYDNEY_CITY_KEY,btnSydney,sydneyName);

        pasteTemperature(moscowTemp,MOSCOW_CITY_KEY,btnMoscow,moscowName);

        pasteTemperature(tokyoTemp,TOKYO_CITY_KEY,btnTokyo,tokyoName);

        pasteTemperature(newyorkTemp,NEWYORK_CITY_KEY,btnNewYork,newyorkName);

        pasteTemperature(carioTemp,CAIRO_CITY_KEY,btnCairo,carioName);
    }

    private void pasteTemperature(final TextView textView, String cityKey, final Button button,final TextView cityname) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApi.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi getApi = retrofit.create(WeatherApi.class);

        Call<CityWeather> call = getApi.getData(cityKey);

        call.enqueue(new Callback<CityWeather>() {
            @Override
            public void onResponse(Call<CityWeather> call, Response<CityWeather> response) {
                if (!response.isSuccessful())
                    textView.setText("Code: " + response.code());
                try{
                    CityWeather cityWeather = response.body();
                    if(cityWeather == null){
                        Toast.makeText(MainActivity.this,"no w0rk",Toast.LENGTH_LONG);
                    }
                    Integer tMax = cityWeather.getDailyForecasts().get(0).getTemperature().getMaximum().getValue();
                    Integer tMin = cityWeather.getDailyForecasts().get(0).getTemperature().getMinimum().getValue();
                    String content = Convert.convert(tMax).toString() + "C / " + Convert.convert(tMin).toString() + "C";
                    button.setOnClickListener(new ShowWeatherForFiveDayListener(MainActivity.this,cityname.getText().toString(),cityWeather));
                    textView.setText(content);
                }catch (NullPointerException e){
                    textView.setText("connect err");
                }
            }

            @Override
            public void onFailure(Call<CityWeather> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}
