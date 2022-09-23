package ru.alexey.RESTful.project3REST.util;

public class RegistrationException extends RuntimeException{

    public RegistrationException(String message){
        super(message);
    }

    public RegistrationException(){
        super("Сенсор с таким именем уже зарегестрирован");
    }
}
