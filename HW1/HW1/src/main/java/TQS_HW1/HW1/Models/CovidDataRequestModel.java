package TQS_HW1.HW1.Models;

public class CovidDataRequestModel {
    private String country;

    public CovidDataRequestModel(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}