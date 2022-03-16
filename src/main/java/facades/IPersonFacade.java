package facades;

import dtos.AddressDTO;
import dtos.PersonDTO;
import dtos.PersonsDTO;
import dtos.PhoneDTO;

public interface IPersonFacade {

    //alle større metoder skal laves herinde inden de kommer ind i personfacade

    PersonDTO createPerson(PersonDTO personDTO);

    PersonsDTO findAllPersons();

    PersonDTO findPersonById(Integer id);

    PersonDTO editPhone(Integer id, PhoneDTO phoneDTO);

    PersonDTO editAddress(Integer id, AddressDTO addressDTO);

}
