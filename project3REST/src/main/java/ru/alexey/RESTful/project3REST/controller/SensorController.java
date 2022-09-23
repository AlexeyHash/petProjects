package ru.alexey.RESTful.project3REST.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alexey.RESTful.project3REST.dto.SensorDTO;
import ru.alexey.RESTful.project3REST.models.Sensor;
import ru.alexey.RESTful.project3REST.services.SensorService;
import ru.alexey.RESTful.project3REST.util.RegistrationException;
import ru.alexey.RESTful.project3REST.util.SensorErrorResponse;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult result) {
        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        Optional<Sensor> optional = sensorService.findByName(sensor.getName());
        if (optional.isPresent()){
            throw new RegistrationException();
        } else if (result.hasErrors()){
            StringBuilder builder = new StringBuilder();
            for (FieldError err : result.getFieldErrors()){
                builder.append(err.getField()).append(" - ").append(err.getDefaultMessage());
            }
            throw new RegistrationException(builder.toString());
        }
        sensorService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/{name}")
    public SensorDTO getOne(@PathVariable String name){
        SensorDTO dto = new SensorDTO();
        Sensor sensor = sensorService.findByName(name).get();
        modelMapper.map(sensor,dto);
        return dto;
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> regErrHandle(RegistrationException ex){
        SensorErrorResponse response = new SensorErrorResponse(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
