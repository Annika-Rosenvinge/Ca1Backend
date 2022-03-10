package facades;

import dtos.PersonDTO;
import dtos.PersonsDTO;

public interface IPersonFacade {

    //alle større metoder skal laves herinde inden de kommer ind i personfacade

    PersonDTO createPerson(PersonDTO personDTO);
}
