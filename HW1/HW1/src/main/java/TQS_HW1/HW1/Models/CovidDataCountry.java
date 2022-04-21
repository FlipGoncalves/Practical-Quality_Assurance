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

    private String newCases;
    private int recoveredCases;
    private int totalCases;
    private int criticalCases;
    private int activeCases;
    private int totalTests;
    private String newDeaths;
    private int totalDeaths;

    private Date objectCreated = new Date(System.currentTimeMillis());

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

    public String getNewCases() {
        return newCases;
    }

    public void setNewCases(String newCases) {
        this.newCases = newCases;
    }

    public int getRecoveredCases() {
        return recoveredCases;
    }

    public void setRecoveredCases(int recoveredCases) {
        this.recoveredCases = recoveredCases;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getCriticalCases() {
        return criticalCases;
    }

    public void setCriticalCases(int criticalCases) {
        this.criticalCases = criticalCases;
    }

    public int getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(int activeCases) {
        this.activeCases = activeCases;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public void setTotalTests(int totalTests) {
        this.totalTests = totalTests;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        this.newDeaths = newDeaths;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public Date getObjectCreated() {
        return objectCreated;
    }

    public void setObjectCreated(Date objectCreated) {
        this.objectCreated = objectCreated;
    }
}