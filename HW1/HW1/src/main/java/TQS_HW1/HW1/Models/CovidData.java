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

    public CovidData(String country) {
        this.country = country;
    }

    public CovidData() {
    }

    public String getCountry() {
        return country;
    }

    public Date getObjectCreated() {
        return dateCreated;
    }

    public void setObjectCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
