package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.*;
import utils.Utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
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
}
