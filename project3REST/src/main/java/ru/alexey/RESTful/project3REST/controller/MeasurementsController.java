package ru.alexey.RESTful.project3REST.controller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.alexey.RESTful.project3REST.dto.MeasurementDTO;
import ru.alexey.RESTful.project3REST.models.Measurement;
import ru.alexey.RESTful.project3REST.models.Sensor;
import ru.alexey.RESTful.project3REST.services.MeasurementService;
import ru.alexey.RESTful.project3REST.services.SensorService;
import ru.alexey.RESTful.project3REST.util.MeasurementErrorResponse;
import ru.alexey.RESTful.project3REST.util.MeasurementValueException;
import ru.alexey.RESTful.project3REST.util.RegistrationException;
import ru.alexey.RESTful.project3REST.util.SensorErrorResponse;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/measurement")
public class MeasurementsController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurements(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult result) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        if (result.hasErrors()){
            StringBuilder builder = new StringBuilder();
            for (FieldError error : result.getFieldErrors()){
                builder.append(error.getField()).append(" - ").append(error.getDefaultMessage());
            }
            throw new MeasurementValueException(builder.toString());
        }
        measurementService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> measurErrHandle(MeasurementValueException ex){
        MeasurementErrorResponse response = new MeasurementErrorResponse(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> regErrHandle(RegistrationException ex){
        SensorErrorResponse response = new SensorErrorResponse(ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public List<MeasurementDTO> measurements() {
        ModelMapper mapper = new ModelMapper();
        List<MeasurementDTO> listDTO = new ArrayList<>();
        for (Measurement measurement : measurementService.findAll()){
            MeasurementDTO measurementDTO = mapper.map(measurement, MeasurementDTO.class);
            listDTO.add(measurementDTO);
        }
        return listDTO;
    }

    @GetMapping("/rainyDaysCount")
    public Integer rainyDaysCount(){
        int count  = 0;
        for (Measurement measurement : measurementService.findAll()){
            if (measurement.isRaining()){
                count++;
            }
        }
        return count;

    }
}
