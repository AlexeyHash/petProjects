package ru.alexey.RESTful.project3REST.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alexey.RESTful.project3REST.models.Measurement;
import ru.alexey.RESTful.project3REST.models.Sensor;
import ru.alexey.RESTful.project3REST.repository.MeasurementRepository;
import ru.alexey.RESTful.project3REST.util.RegistrationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    private final MeasurementRepository repository;
    private final SensorService sensorService;

    private final static String ERR = "Данный сенсор не зарегистрирован";

    @Autowired
    public MeasurementService(MeasurementRepository repository, SensorService sensorService) {
        this.repository = repository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll()
    {
        return repository.findAll();
    }
    @Transactional
    public void save(Measurement measurement){
        measurement.setTimestamp(LocalDateTime.now());
        Optional<Sensor> optional = sensorService.findByName(measurement.getSensor().getName());
        if (optional.isEmpty()){
            throw new RegistrationException(ERR);
        }
        measurement.setSensor(optional.get());
        repository.save(measurement);
    }
}
