package com.zashalovska.citiesGame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CityService {

    private static final int LIMIT = 80;
    private static final String WAS_USED_ERROR_MSG = "This city already was used. Please write another one";
    private static final String NOT_EXISTING_ERROR_MSG = "This city is not existing in Ukraine. Try again";
    private static final String EMPTY = "";
    private char cityLastLetter;

    @Autowired
    private CityRepository cityRepository;

    public City findCityByFirstLetter(String firstLetter) throws NoSuchElementException
    {
        List<City> cities = cityRepository.findByNameStartsWith(firstLetter.toLowerCase());
        Collections.shuffle(cities);

        return cities.stream().findAny().orElseThrow(NoSuchElementException::new);
    }

    public City savePreviousCity(City city){
       return cityRepository.save(city);
    }

    public City findById(long id){
        return cityRepository.findById(id).get();
    }

    public String checkCityIsExisted(String cityName) throws NoSuchElementException
    {
        City city = cityRepository.findCityByName(cityName.toLowerCase());

        if(city == null){
            return NOT_EXISTING_ERROR_MSG;
        }

        else if (city.getWasInGame()){
            return WAS_USED_ERROR_MSG;
        }

        city.setWasInGame(true);
        this.savePreviousCity(city);

        return EMPTY;
    }

    public void resetPreviousGame()
    {
       List<City> wereInGame =  cityRepository.findByWasInGameIsTrue();
        for (City city : wereInGame) {
            city.setWasInGame(false);
            cityRepository.save(city);
        }
    }

    public City getStartCity() {
        Random rand = new Random();
        long randomCityId = rand.nextInt(LIMIT);

        City city = this.findById(randomCityId);
        city.setWasInGame(true);
        savePreviousCity(city);
        setCityLastLetter(city.getName().charAt(city.getName().length()-1));
        return city;
    }

    public char getCityLastLetter() {
        return cityLastLetter;
    }

    public void setCityLastLetter(char cityLastLetter) {
        this.cityLastLetter = cityLastLetter;
    }
}
