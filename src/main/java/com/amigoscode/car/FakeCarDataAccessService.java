package com.amigoscode.car;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("fake")
public class FakeCarDataAccessService implements CarDAO {

    private List<Car> db;

    public FakeCarDataAccessService() {
        this.db = new ArrayList<>();
    }

    @Override
    public Car selectCarById(Integer id) {
        for (Car c:db) {
            if (c.getId() == id){
            return c;
            }
        }
        return null;
    }

    @Override
    public List<Car> selectAllCars() {
        return db;
    }

    @Override
    public int insertCar(Car car) {
        db.add(car);
        return 1;
    }

    @Override
    public int deleteCar(Integer id) {
//        for (Car c:db) {
//            if (c.getId() == id);
//            db.remove(c);
//            return 1;
//        }
//        return 0;

        db.remove(selectCarById(id));
        return 1;

    }

    @Override
    public int updateCar(Integer id, Car update) {
        selectCarById(id).setBrand(update.getBrand());
        selectCarById(id).setRegNumber(update.getRegNumber());
        selectCarById(id).setPrice(update.getPrice());
        return 1;
    }
}
