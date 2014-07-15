package com.seb.services.weather.data.orm;

import com.seb.services.weather.enums.Province;
import com.seb.services.weather.enums.Region;

import javax.persistence.*;
import java.util.List;

/**
 * Created by sebastien.vandamme@gmail.com on 12/07/2014.
 */
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private int population;

    @Enumerated(EnumType.STRING)
    private Province province;

    @Enumerated(EnumType.STRING)
    private Region region;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    @JoinColumn(name = "id")
    private List<TemperatureHistory> temperatureHistory;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private List<WeatherHistory> weatherHistory;

    public City() {
        super();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<TemperatureHistory> getTemperatureHistory() {
        return temperatureHistory;
    }

    public void setTemperatureHistory(List<TemperatureHistory> temperatureHistory) {
        this.temperatureHistory = temperatureHistory;
    }

    public List<WeatherHistory> getWeatherHistory() {
        return weatherHistory;
    }

    public void setWeatherHistory(List<WeatherHistory> weatherHistory) {
        this.weatherHistory = weatherHistory;
    }
}
