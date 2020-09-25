package com.zashalovska.citiesGame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class CityController {

    private static final String WELCOME_TO_GAME_CITY = "Welcome to game! City: ";
    private static final String SEND_NAME_OF_CITY_MESSAGE = "Please send name of city which starts on letter ";
    private static final String EMPTY = "";
    private static final String YOU_ARE_WINNER = "You are winner!";
    private static final String THANKS_FOR_GAME = "Thanks for game";

    @Autowired
    private CityService cityService;

    @GetMapping("/begin")
    public String beginGame() {
        cityService.resetPreviousGame();
        City city = cityService.getStartCity();
  
        return  WELCOME_TO_GAME_CITY + city.getName();
    }



    @GetMapping("/next")
    public String receiveCity(@RequestParam(value = "word") String city) {

        if (!(Character.toUpperCase(cityService.getCityLastLetter()) == Character.toUpperCase(city.charAt(0)))) {
            return SEND_NAME_OF_CITY_MESSAGE + cityService.getCityLastLetter();
        }

        String errorMes = cityService.checkCityIsExisted(city);
        if(!errorMes.equals(EMPTY)){
         return errorMes;
        }

        City newCity;

        try
        {
           newCity  = cityService.findCityByFirstLetter(String.valueOf(city.charAt(city.length() - 1)));
        }
        catch (NoSuchElementException ex) {
            return YOU_ARE_WINNER;
        }

        cityService.setCityLastLetter(newCity.getName().charAt(newCity.getName().length()-1));
        newCity.setWasInGame(true);
        cityService.savePreviousCity(newCity);

        return newCity.getName();
    }

    @PostMapping("/end")
    public String endGame() {
        return THANKS_FOR_GAME;
    }
}
