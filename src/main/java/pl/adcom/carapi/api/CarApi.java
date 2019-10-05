package pl.adcom.carapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.adcom.carapi.model.Car;
import pl.adcom.carapi.service.CarService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarApi {

    private CarService carService;

    @Autowired
    public CarApi(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars(){
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",
                produces = {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Car> getCarById(@PathVariable long id){
        Optional<Car> firstCar = carService.getFirst(id);
        if(firstCar.isPresent()){
            return new ResponseEntity<>(firstCar.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/color/{color}",
                produces = {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Car>> getCarsByColor(@PathVariable String color){
        List<Car> carList = carService.getAllCarsByColor(color);
        if(!carList.isEmpty()){
            return new ResponseEntity<>(carList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/addCar",
                produces = {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Car> addNewCar(@RequestBody Car car){
        boolean newCar = carService.addCar(car);
        if(newCar){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(value = "/modCar",
                produces = {MediaType.APPLICATION_JSON_VALUE,
                            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity changeCar(@RequestBody Car car){
        boolean makeChange = carService.changeCar(car);
        if(makeChange){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping(value = "/{id}",
                  produces = {MediaType.APPLICATION_JSON_VALUE,
                              MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity changeCarColor(@RequestParam String newColor,
                                         @PathVariable long id){
        if(carService.changeColorOfCar(newColor, id)){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}",
                   produces = {MediaType.APPLICATION_JSON_VALUE,
                               MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity deleteCar(@PathVariable long id){
        if(carService.deleteCar(id)){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
