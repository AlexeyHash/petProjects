package ru.alexey.RESTful.project3REST.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexey.RESTful.project3REST.models.Sensor;
import ru.alexey.RESTful.project3REST.repository.SensorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void save(Sensor sensor){

        sensor.setTimestamp(LocalDateTime.now());
        sensorRepository.save(sensor);
    }

    public List<Sensor> findAll(){
        return sensorRepository.findAll();
    }

    public Optional<Sensor> findByName(String name){
        return sensorRepository.findByName(name);
    }

}
