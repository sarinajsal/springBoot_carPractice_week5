package com.amigoscode.car;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private CarDAO carDAO;

    public CarService(@Qualifier("postgres") CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public void registerNewCar(Car car) {
        // business logic. check if reg number is valid and not take
        if (car.getPrice() <= 0) {
            throw new IllegalStateException("Car price cannot be 0 or less");
        }
        int result = carDAO.insertCar(car);

        if (result != 1) {
            throw new IllegalStateException("Could not save car...");
        }
    }

    public List<Car> getCars() {
        return carDAO.selectAllCars();
    }
    public Car getCarByid(Integer idNumber){
        Car aCar = carDAO.selectCarById(idNumber);
        if (aCar.getId() == null){
            throw new IllegalStateException();
        }
       return carDAO.selectCarById(idNumber);
    }


    private Car getCarOrThrowException(Integer id){
        Car car = carDAO.selectCarById(id);
        if (car == null){
            throw new IllegalStateException("car does not exist");
        }
        return car;
    }

    public void deleteCarByID(Integer idNumber){
        getCarOrThrowException(idNumber);
        int deleted = carDAO.deleteCar(idNumber);
        if (deleted != 1){
            throw new IllegalStateException("could not delete car");
        }
         carDAO.deleteCar(idNumber);
    }


    public void updateCar (Integer idNumber, Car update){
        carDAO.updateCar(idNumber, update);
    }
}
