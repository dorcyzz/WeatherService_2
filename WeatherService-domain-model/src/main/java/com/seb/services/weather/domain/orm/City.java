package com.seb.services.weather.domain.orm;

import com.seb.services.weather.domain.enums.Province;
import com.seb.services.weather.domain.enums.Region;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sebastien.vandamme@gmail.com on 12/07/2014.
 */
@Entity
public class City {
    @Id
    @NotBlank
    private String name;

    @NotNull
    @Min(500)
    private Integer population;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Province province;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Region region;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "name")
    @NotNull
    private Set<TemperatureHistory> temperatureHistory = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "name")
    @NotNull
    private Set<WeatherHistory> weatherHistory = new HashSet<>();

    public City() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
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

    public Set<TemperatureHistory> getTemperatureHistory() {
        return temperatureHistory;
    }

    public void setTemperatureHistory(Set<TemperatureHistory> temperatureHistory) {
        this.temperatureHistory = temperatureHistory;
    }

    public Set<WeatherHistory> getWeatherHistory() {
        return weatherHistory;
    }

    public void setWeatherHistory(Set<WeatherHistory> weatherHistory) {
        this.weatherHistory = weatherHistory;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;

        City city = (City) o;

        if (name != null ? !name.equals(city.name) : city.name != null) return false;
        if (population != null ? !population.equals(city.population) : city.population != null) return false;
        if (province != city.province) return false;
        if (region != city.region) return false;
        if (temperatureHistory != null ? !temperatureHistory.equals(city.temperatureHistory) : city.temperatureHistory != null)
            return false;
        if (weatherHistory != null ? !weatherHistory.equals(city.weatherHistory) : city.weatherHistory != null)
            return false;

        return true;
    }

    @Override
    public final int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (population != null ? population.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (temperatureHistory != null ? temperatureHistory.hashCode() : 0);
        result = 31 * result + (weatherHistory != null ? weatherHistory.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", population=" + population +
                ", province=" + province +
                ", region=" + region +
                ", temperatureHistory=" + temperatureHistory +
                ", weatherHistory=" + weatherHistory +
                '}';
    }
}
