package gr.uom.android.helpinghand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class MainFrag extends Fragment {


    public MainFrag(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main, container, false);
        return view;
    }

    @Override
    public void onStart() {
        WeatherTask weatherTask = new WeatherTask(this);
        weatherTask.execute();
        super.onStart();
    }

    public void setWeatherString(String weatherString){
        TextView textView = getActivity().findViewById(R.id.weatherText);
        textView.append(weatherString);

    }

    public void setWeatherImage(String weatherConditions){
        ImageView imageView = getActivity().findViewById(R.id.WeatherImage);
        if(weatherConditions.equalsIgnoreCase("Clouds")){
            imageView.setImageResource(R.drawable.sunny_s_cloudy);
        }
        else if(weatherConditions.equalsIgnoreCase("Rain")){
            imageView.setImageResource(R.drawable.rain);
        }
        else if(weatherConditions.equalsIgnoreCase("Clear")){
            imageView.setImageResource(R.drawable.sunny);
        }
    }
}
