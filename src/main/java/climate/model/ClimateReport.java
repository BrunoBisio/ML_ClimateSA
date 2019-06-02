package climate.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ClimateReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDate date;
    private String weather;

    public ClimateReport() {
    }

    public ClimateReport(LocalDate date, String weather) {
        this.date = date;
        this.weather = weather;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String wheater) {
        this.weather = wheater;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}