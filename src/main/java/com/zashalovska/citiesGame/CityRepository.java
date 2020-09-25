package com.zashalovska.citiesGame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository <City, Long> {

    List<City> findByNameStartsWith(String letter);
    City findCityByName(String name);
    List<City> findByWasInGameIsTrue();
    
}
