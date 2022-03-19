package Geocode;

public class Address 
{
    private String road;
    private String state;
    private String city;
    private String zio;
    private String houseNumber;

    public Address(String road, String state, String city, String zio, String houseNumber) {
        this.road = road;
        this.state = state;
        this.city = city;
        this.zio = zio;
        this.houseNumber = houseNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((houseNumber == null) ? 0 : houseNumber.hashCode());
        result = prime * result + ((road == null) ? 0 : road.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((zio == null) ? 0 : zio.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (houseNumber == null) {
            if (other.houseNumber != null)
                return false;
        } else if (!houseNumber.equals(other.houseNumber))
            return false;
        if (road == null) {
            if (other.road != null)
                return false;
        } else if (!road.equals(other.road))
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        if (zio == null) {
            if (other.zio != null)
                return false;
        } else if (!zio.equals(other.zio))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Address [city=" + city + ", houseNumber=" + houseNumber + ", road=" + road + ", state=" + state
                + ", zio=" + zio + "]";
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZio() {
        return zio;
    }

    public void setZio(String zio) {
        this.zio = zio;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    
}
