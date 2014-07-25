package com.seb.services.weather.domain.orm;

import com.seb.services.weather.domain.enums.WeatherType;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

/**
 * Created by sebastien.vandamme@gmail.com on 15/07/2014.
 */
@Entity
public class WeatherHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityName", nullable = false)
    private City city;

    @Enumerated(EnumType.STRING)
    private WeatherType weather;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime date;


    public WeatherHistory() {
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

    public WeatherType getWeather() {
        return weather;
    }

    public void setWeather(WeatherType weather) {
        this.weather = weather;
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
        if (!(o instanceof WeatherHistory)) return false;

        WeatherHistory that = (WeatherHistory) o;

        if (id != that.id) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (weather != that.weather) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (weather != null ? weather.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeatherHistory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weather=" + weather +
                ", date=" + date +
                '}';
    }
}
