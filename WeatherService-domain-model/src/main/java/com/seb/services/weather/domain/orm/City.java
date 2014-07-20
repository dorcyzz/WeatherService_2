package com.seb.services.weather.domain.orm;

import com.seb.services.weather.domain.enums.Province;
import com.seb.services.weather.domain.enums.Region;

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
    private List<TemperatureHistory> temperatureHistory;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private List<WeatherHistory> weatherHistory;

    public City() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;

        City city = (City) o;

        if (id != city.id) return false;
        if (population != city.population) return false;
        if (name != null ? !name.equals(city.name) : city.name != null) return false;
        if (province != city.province) return false;
        if (region != city.region) return false;
        if (temperatureHistory != null ? !temperatureHistory.equals(city.temperatureHistory) : city.temperatureHistory != null)
            return false;
        if (weatherHistory != null ? !weatherHistory.equals(city.weatherHistory) : city.weatherHistory != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + population;
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (temperatureHistory != null ? temperatureHistory.hashCode() : 0);
        result = 31 * result + (weatherHistory != null ? weatherHistory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "The city of " + getName() + " (id = " + getId() + "), population = " + getPopulation() + ", region = " + getRegion() + ", Province = " + getProvince();
    }
}