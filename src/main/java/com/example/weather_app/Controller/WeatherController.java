package com.example.weather_app.Controller;

import com.example.weather_app.Model.WeatherResponse;
import com.example.weather_app.WeatherAppApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Controller
public class WeatherController {

    @Value("${api.key}")
    private String apiKey;
    @GetMapping("/abc")
    public String getIndex(){
        return "index";
    }


    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city, Model model){
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appId=" + apiKey + "&units=metric";
        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);

        if(weatherResponse != null){
            model.addAttribute("city", weatherResponse.getName());
            model.addAttribute("country", weatherResponse.getSys().getCountry());
            model.addAttribute("weatherDescription", weatherResponse.getWeather().get(0).getDescription());
            model.addAttribute("temperature", weatherResponse.getMain().getTemp());
            model.addAttribute("humidity", weatherResponse.getMain().getHumidity());
            model.addAttribute("windSpeed", weatherResponse.getWind().getSpeed());
            String weatherIcon = "wi wi-owm-"  + weatherResponse.getWeather().get(0).getId();
            model.addAttribute("weatherIcon", weatherIcon);
        }
        else {
            model.addAttribute("error", "City Not Found");
        }
        System.out.println("City: " + weatherResponse.getName());
        System.out.println("Country: " + weatherResponse.getSys().getCountry());
        System.out.println("Temperature: " + weatherResponse.getMain().getTemp());
        System.out.println("Humidity: " + weatherResponse.getMain().getHumidity());
        System.out.println("Wind Speed: " + weatherResponse.getWind().getSpeed());
        System.out.println("Weather Description: " + weatherResponse.getWeather().get(0).getDescription());

        return "weather";
    }
}