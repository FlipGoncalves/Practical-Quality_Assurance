package TQS_HW1.HW1.Models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CovidData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;

    private Date dateCreated = new Date(System.currentTimeMillis());

    public CovidData(CovidDataRequestModel covid) {
        this.country = covid.getCountry();
    }

    public CovidData(String country) {
        this.country = country;
    }

    public CovidData() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "CovidData [country=" + country + ", id=" + id + "]";
    }

    public Date getObjectCreated() {
        return dateCreated;
    }

    public void setObjectCreated(Date objectcreated) {
        this.dateCreated = objectcreated;
    }
}
