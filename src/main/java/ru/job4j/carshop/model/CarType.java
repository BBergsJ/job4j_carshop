package ru.job4j.carshop.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "type")
public class CarType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public CarType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarType carType = (CarType) o;
        return id == carType.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}