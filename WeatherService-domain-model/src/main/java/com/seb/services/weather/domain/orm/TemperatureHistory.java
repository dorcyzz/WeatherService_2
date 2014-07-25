package com.seb.services.weather.domain.orm;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

/**
 * Created by sebastien.vandamme@gmail.com on 15/07/2014.
 */
@Entity
public class TemperatureHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityName", nullable = false)
    private City city;

    private int temperature;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime date;

    public TemperatureHistory() {
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TemperatureHistory)) return false;

        TemperatureHistory that = (TemperatureHistory) o;

        if (id != that.id) return false;
        if (temperature != that.temperature) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + temperature;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TemperatureHistory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", temperature=" + temperature +
                ", date=" + date +
                '}';
    }
}
