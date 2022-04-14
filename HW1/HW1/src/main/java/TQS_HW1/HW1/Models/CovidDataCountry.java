package TQS_HW1.HW1.Models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CovidDataCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String continent;
    private String day;

    private String new_cases;
    private int recovered_cases;
    private int total_cases;
    private int critical_cases;
    private int active_cases;
    private int total_tests;
    private String new_deaths;
    private int total_deaths;

    private Date object_created = new Date(System.currentTimeMillis());

    public CovidDataCountry(String country, String continent, String day) {
        this.country = country;
        this.continent = continent;
        this.day = day;
    }

    public CovidDataCountry() {
        this.country = "Undefined";
        this.continent = "Undefined";
        this.day = null;
    }

    public String getCountry() {
        return country;
    }

    public String getContinent() {
        return continent;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public String getNew_cases() {
        return new_cases;
    }

    public void setNew_cases(String new_cases) {
        this.new_cases = new_cases;
    }

    public int getRecovered_cases() {
        return recovered_cases;
    }

    public void setRecovered_cases(int recovered_cases) {
        this.recovered_cases = recovered_cases;
    }

    public int getTotal_cases() {
        return total_cases;
    }

    public void setTotal_cases(int total_cases) {
        this.total_cases = total_cases;
    }

    public int getCritical_cases() {
        return critical_cases;
    }

    public void setCritical_cases(int critical_cases) {
        this.critical_cases = critical_cases;
    }

    public int getActive_cases() {
        return active_cases;
    }

    public void setActive_cases(int active_cases) {
        this.active_cases = active_cases;
    }

    public int getTotal_tests() {
        return total_tests;
    }

    public void setTotal_tests(int total_tests) {
        this.total_tests = total_tests;
    }

    public String getNew_deaths() {
        return new_deaths;
    }

    public void setNew_deaths(String new_deaths) {
        this.new_deaths = new_deaths;
    }

    public int getTotal_deaths() {
        return total_deaths;
    }

    public void setTotal_deaths(int total_deaths) {
        this.total_deaths = total_deaths;
    }

    public Date getObject_created() {
        return object_created;
    }

    public void setObject_created(Date object_created) {
        this.object_created = object_created;
    }
}