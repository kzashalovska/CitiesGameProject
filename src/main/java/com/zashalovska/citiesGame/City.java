package com.zashalovska.citiesGame;

import javax.persistence.*;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "boolean default false")
    private Boolean wasInGame;

    public String getName() {
        return name;
    }


    public Boolean getWasInGame() {
        return wasInGame;
    }

    public void setWasInGame(Boolean wasInGame) {
        this.wasInGame = wasInGame;
    }
}
