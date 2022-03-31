package lab3_2.CarInformationAPI;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tqs_car")
public class Car {
    private String maker;
    private String model;
    @Id
    private long id;

    public Car(Long id, String maker, String model) {
        this.id = id;
        this.maker = maker;
        this.model = model;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
}
