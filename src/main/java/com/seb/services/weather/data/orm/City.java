package com.seb.services.weather.data.orm;

import com.seb.services.weather.enums.Province;
import com.seb.services.weather.enums.Region;

/**
 * Created by sebastien.vandamme@gmail.com on 12/07/2014.
 */
public class City {
    private String name;
    private int population;
    private Province province;
    private Region region;

    public City() {
        super();
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
}
