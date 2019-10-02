package pl.adcom.carapi.service;

import org.springframework.stereotype.Service;
import pl.adcom.carapi.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService{

    List<Car> listCars;

    public CarServiceImpl() {
        listCars = new ArrayList<>();
        listCars.add(new Car(1L,"Opel", "Corsa",  "czarny"));
        listCars.add(new Car(2L, "Nissan", "Juke", "biały"));
        listCars.add(new Car(3L, "Honda", "Civic", "czarny"));
        listCars.add(new Car(4L, "Honda", "Civic", "srebrny"));
    }

    @Override
    public List<Car> getAllCars() {
        return listCars;
    }

    @Override
    public Optional<Car> getFirst(long id) {
        Optional<Car> first = listCars.stream().filter(car -> car.getId() == id).findFirst();
        return first;
    }

    @Override
    public List<Car> getAllCarsByColor(String color) {
        List<Car> allCars = listCars.stream().filter(car1 -> car1.getColor().equals(color)).collect(Collectors.toList());
        return allCars;
    }

    @Override
    public boolean addCar(Car car) {
        return listCars.add(car);
    }

    @Override
    public boolean changeCar(Car myCar) {
        Optional<Car> modCar = listCars.stream().filter(car1 -> car1.getId() == myCar.getId()).findFirst();

        if(modCar.isPresent()){
            listCars.remove(modCar.get());
            listCars.add(myCar);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeColorOfCar(String color, long id) {
        Optional<Car> modCarColor = listCars.stream().filter(car -> car.getId() == id).findFirst();

        if(modCarColor.isPresent()){
            listCars.remove(modCarColor.get());
            modCarColor.get().setColor(color);
            listCars.add(modCarColor.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCar(long id) {
        Optional<Car> delCar = listCars.stream().filter(car -> car.getId() == id).findFirst();

        if(delCar.isPresent()){
            listCars.remove(delCar.get());
            return true;
        }
        return false;
    }
}
