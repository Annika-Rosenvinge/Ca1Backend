package facades;

import dtos.*;
import entities.*;
import utils.Utility;

import javax.persistence.*;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;


    private PersonFacade(){
    }

    public static PersonFacade getPersonFacade(EntityManagerFactory _emf){
        if(instance == null){
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public PersonDTO getPersonById(int id) {
        EntityManager em = getEntityManager();
        try {
            Person person = em.find(Person.class, id);
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

    //mindre metoder til de større
    //Checker om email er i brug hos en anden bruger
    private boolean emailTaken(PersonDTO personDTO){
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Person p WHERE p.email = :email", Person.class);
            query.setParameter("email", personDTO.getEmail());
            Person person = (Person) query.getSingleResult();
            if (person != null) {
                return true;
            } else {
                return false;
            }
        } catch (NoResultException e) {
            return false;
        } catch (RuntimeException e){
            throw new WebApplicationException("Error 500", 500);
        } finally {
            em.close();
        }
    }

    //tjekker om telefon nummeret er i brug hos en anden bruger
    private boolean phoneTaken(Phone phone){
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Phone p WHERE p.phonenumber = :phonenumber", Phone.class);
            query.setParameter("phonenumber", phone.getPhonenumber());
            phone = (Phone) query.getSingleResult();
            if (phone != null) {
                return true;
            } else {
                return false;
            }
        }catch (NoResultException e){
            return false;
        }catch (RuntimeException e){
            throw new WebApplicationException("Error 500", 500);
        } finally {
            em.close();
        }
    }
    //Skal testes
    public HobbyDTO createHobby(String name, String link, String type, String category) {
        EntityManager em = emf.createEntityManager();
        Hobby hobby = new Hobby();

        try {
            em.getTransaction().begin();
            hobby.setName(name);
            hobby.setCategory(category);
            hobby.setWikiLink(link);
            hobby.setType(type);
            em.persist(hobby);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new HobbyDTO(hobby);
    }


    //CRUD METODER FOR PERSON
    //CREATE - test og endpoint er lavet
    public synchronized PersonDTO createPerson(PersonDTO personDTO) {

        if (Utility.ValidatePersonDTO(personDTO) && !emailTaken(personDTO)){
            Person person;
            List<HobbyDTO> hobbyDTOList = personDTO.getHobbyList();
            List<HobbyDTO> hobbies = new ArrayList<>();
            personDTO.setHobbyList(hobbies);
            EntityManager em = emf.createEntityManager();
            try {
                person = new Person(personDTO);
                em.getTransaction().begin();
                if (person.getAddress() != null && person.getAddress().getCityInfo() != null){
                    Address address = person.getAddress();
                    CityInfo cityInfo = address.getCityInfo();
                    em.persist(cityInfo);
                    address.setCityInfo(cityInfo);
                    em.persist(address);

                }
                if (person.getPhoneList() != null){
                    for (Phone phone : person.getPhoneList()){
                        if (!phoneTaken(phone)){
                            em.persist(phone);
                            phone.setPerson(person);
                            em.merge(phone);
                        }
                    }
                }

                em.persist(person);
                if (person.getHobbyList() != null){
                    for (HobbyDTO hobbyDTO : hobbyDTOList){
                        HobbyDTO h = createHobby(hobbyDTO.getName(), hobbyDTO.getCategory(), hobbyDTO.getType(), hobbyDTO.getWikiLink());
                        Hobby hobbyEntity = new Hobby(h);
                        person.addHobby(hobbyEntity);
                        em.merge(person);
                    }
                }
                em.merge(person);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new PersonDTO(person);
        } else {
            throw new WebApplicationException("Error 400", 400);
        }
    }

    //FIND ALL - test og endpoint er lavet
    public PersonsDTO findAllPersons(){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            TypedQuery <Person> typedQuery = em.createNamedQuery("PERSON.getAllRows", Person.class);
            List<Person> allPersons = typedQuery.getResultList();
            PersonsDTO personsDTO = new PersonsDTO(allPersons);

            em.getTransaction().commit();

            return personsDTO;
        }finally {
            em.close();
        }
    }

    //FIND ONE - test og endpoint er lavet
    public PersonDTO findPersonById(Integer id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Person person = em.find(Person.class, id);

        em.getTransaction().commit();
        em.close();

        if(person != null){
            person.setId(id);
            return new PersonDTO(person);
        }else{
            return null;
        }

    }

    //EDIT PHONE
    //beskrivelsen kommer ikke med af den ene eller anden årsag, måske skal det kigges på
    public synchronized PersonDTO editPhone(Integer id, PhoneDTO phoneDTO){
        EntityManager em = emf.createEntityManager();

        Person updPerson = em.find(Person.class, id);
        List<Phone> phones = updPerson.getPhoneList();

        Phone newPhone = new Phone(phoneDTO);
        phones.set(phones.size()-1,newPhone);

        try{
            em.getTransaction().begin();

            updPerson.setPhoneList(phones);
            em.merge(updPerson);

            em.getTransaction().commit();

            return new PersonDTO(updPerson);
        }finally {
            em.close();
        }

    }

    public CityInfosDTO findAllZipCodes(){
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            TypedQuery <CityInfo> query = em.createNamedQuery("CITYINFO.getAllRows", CityInfo.class);
            List<CityInfo> allZipcodes = query.getResultList();
            CityInfosDTO cityInfosDTO = new CityInfosDTO(allZipcodes);
            em.getTransaction().commit();

            return cityInfosDTO;
          }finally{
            em.close();
          }
        }

    //EDIT ADDRESS
    //ikke testet
    public synchronized PersonDTO editAddress(Integer id, AddressDTO addressDTO){
        EntityManager em = emf.createEntityManager();

        Person updPerson = em.find(Person.class, id);
        Address currentAdd = updPerson.getAddress();
        CityInfo currentCity = updPerson.getAddress().getCityInfo();

        currentAdd.setStreet(addressDTO.getStreet());

        currentCity.setCity(addressDTO.getCity());
        currentCity.setZipcode(addressDTO.getZipcode());

        try{
            em.getTransaction().begin();

            currentAdd.setCityInfo(currentCity);
            updPerson.setAddress(currentAdd);
            em.merge(updPerson);

            em.getTransaction().commit();

            return new PersonDTO(updPerson);
        }finally {
            em.close();
        }

    }

    public synchronized PersonDTO addHobby(int id, String hobbyName){
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, id);
        try {
            em.getTransaction().begin();
            TypedQuery <Hobby> query = em.createQuery("SELECT h FROM Hobby h WHERE h.name = ;name", Hobby.class);
            query.setParameter("name", hobbyName);
            Hobby hobby = query.getSingleResult();
            person.addHobby(hobby);
            em.merge(person);
            em.getTransaction().commit();

            return new PersonDTO(person);
        }finally {
            em.close();
        }
      }

    //UPDATE
    //ikke testet
    public PersonDTO editPerson(PersonDTO personDTO) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Person person = em.find(Person.class, personDTO.getId());
            person.setEmail(personDTO.getEmail());
            person.setFirstname(personDTO.getFirstname());
            person.setLastname(personDTO.getLastname());
            person.setPhoneList(Phone.getEntites(personDTO.getPhoneList()));
            person.setHobbyList(Hobby.getEntites(personDTO.getHobbyList()));

            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }


    //DELETE
    //ikke testet
    public PersonDTO deletePersonById(Integer id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            em.createNamedQuery("PERSON.deleteById").setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }



}
