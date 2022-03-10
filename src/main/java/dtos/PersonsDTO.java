package dtos;

import entities.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonsDTO {

    List<PersonDTO> personDTOS = new ArrayList<>();

    public PersonsDTO(List<Person> persons){
        persons.forEach((p) ->{
            personDTOS.add(new PersonDTO(p));
        });
    }

    public List<PersonDTO> getAll(){
        return personDTOS;
    }

    @Override
    public String toString() {
        return "PersonsDTO{" +
                "personDTOS=" + personDTOS +
                '}';
    }
}
