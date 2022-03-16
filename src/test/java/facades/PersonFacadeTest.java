package facades;

import entities.*;
import dtos.*;
import org.eclipse.persistence.internal.jpa.EntityManagerFactoryDelegate;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonFacadeTest {
    private static EntityManagerFactory emf;
    private static PersonFacade testFacade;

    private static Person person1 = new Person( "Per","Hansen", "PerHansen@Mail.dk");
    private static Person person2 = new Person( "Mia","Hansen", "M.Hansen@Mail.dk");
    private static Person person3 = new Person( "Søren","A. Michaelsen", "sam@gmail.dk");
    private static Person person4 = new Person( "Katrine","Juul Mathiasen", "Kat.JMn@email.com");

    private static Phone phone1 = new Phone(934456224, "Hjemme");
    private static Phone phone2 = new Phone(49343242, "Privat");
    private static Phone phone3 = new Phone(45183528, "Arbejde");
    private static Phone phone4 = new Phone(71204726, "Privat");

    private static CityInfo cityInfo1 = new CityInfo("2610", "Rødovre");
    private static CityInfo cityInfo2 = new CityInfo("3472", "Sandholmlejren");
    private static CityInfo cityInfo3 = new CityInfo("3980", "Ittoqqortoormit");


    private static Address address1 = new Address("Prinsessevej 34, 2th", cityInfo1);
    private static Address address2 = new Address("Ege alle 87", cityInfo2);
    private static Address address3 = new Address( "Hovedvejen", cityInfo3);
    private static Address address4 = new Address ("Mile alle 23", cityInfo1 );

    private static Hobby hobby1 = new Hobby("Golf", "ball", "outdoor", "www.golf.dk");
    private static Hobby hobby2 = new Hobby("Dans", "Kropsbevægelse", "Indendørs", "Dansmedos.dk");
    private static Hobby hobby3 = new Hobby("Løb", "Kropsbevægelse", "Udendørs", "www.løb.dk");
    private static Hobby hobby4 = new Hobby("Crossfit", "Idioti", "Indendørs", " ");

    public PersonFacadeTest(){

    }

    @BeforeAll
    public static void setUpClass(){
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        testFacade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass(){

    }

    @BeforeEach
    void setUp(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNamedQuery("PHONE.deleteAllRows",Phone.class).executeUpdate();
        em.createNamedQuery("HOBBY.deleteAllRows", Hobby.class).executeUpdate();
        em.createNamedQuery("PERSON.deleteAllRows", Person.class).executeUpdate();
        em.createNamedQuery("ADDRESS.deleteAllRows", Address.class).executeUpdate();
        em.createNamedQuery("CITYINFO.deleteAllRows", CityInfo.class).executeUpdate();
        em.getTransaction().commit();

        try{
            em.getTransaction().begin();
            //personer gives hobby og telefon nummer først
            person1.addHobby(hobby1);
            person1.addPhone(phone1);
            person2.addHobby(hobby2);
            person2.addPhone(phone2);
            person3.addHobby(hobby3);
            person3.addPhone(phone3);
            person4.addHobby(hobby4);
            person4.addPhone(phone4);

            //nu gives de adresser en efter en
            //Person 1
            em.persist(cityInfo1);
            em.persist(address1);
            person1.setAddress(address1);
            em.persist(person1);
            em.merge(person1);

            //Person 2
            em.persist(cityInfo2);
            em.persist(address2);
            person2.setAddress(address2);
            em.persist(person2);
            em.merge(person2);

            //Person 3
            em.persist(cityInfo3);
            em.persist(address3);
            person3.setAddress(address3);
            em.persist(person3);
            em.merge(person3);

            //Person 4
            //bemærk at denne er lidt anderledes
            em.persist(address4);
            person4.setAddress(address4);
            em.persist(person4);
            em.merge(person4);

            em.getTransaction().commit();

        }finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown(){

    }

    @Test
    //virker
    void createPerson(){
        EntityManager em = emf.createEntityManager();

        //Lav ny person
        Person person  = new Person("Maria Sofie", "Madsen", "M.Sofia@gmail.com");
        CityInfo cityInfo = new CityInfo("2730", "Herlev");
        Address address = new Address("Byvej 27", cityInfo);
        Phone phone = new Phone(63917428, "privat");

        ArrayList<Hobby> hobbyList = new ArrayList();
        hobbyList.add(hobby4);

        person.setAddress(address);
        person.addPhone(phone);
        person.setHobbyList(hobbyList);

        //lav en try finally med PersonDTO
        try{
            em.getTransaction().begin();

            PersonDTO personDTO = new PersonDTO(person);
            PersonDTO result = testFacade.createPerson(personDTO);
            PersonDTO expResult = new PersonDTO(person);
            assertEquals(expResult.getId(), result.getId());
            System.out.println("Expected result: " + expResult + "Result: " + result);

            em.getTransaction().commit();
        }finally {
            em.close();
        }

    }

    @Test
    //virker
    void findAllPersons(){
        EntityManager em = emf.createEntityManager();
        List <Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        persons.add(person4);

        PersonsDTO expResult = new PersonsDTO(persons);
        try{
            em.getTransaction().begin();
            PersonsDTO result = testFacade.findAllPersons();
            em.getTransaction().commit();
            //System.out.println(result);
            assertEquals(expResult.getAll().size(), result.getAll().size());
        }finally {
            em.close();
        }
    }

    @Test
    void findPersonById(){
       Integer id = person1.getId();
       PersonDTO result = testFacade.findPersonById(id);
       System.out.println("expected: " + person1.getId() + " result: " + result.getId());
       assertEquals(person1.getId(), result.getId());

    }

    @Test
    //virker men beskrivelsen kommer ikke med
    void editPhone(){
        Phone phone = new Phone(81264081, "private");
        PhoneDTO newPhone = new PhoneDTO(phone);

        Integer id = person1.getId();
        PersonDTO result = testFacade.editPhone(id, newPhone);

        List <PhoneDTO> expResult = new ArrayList<>();
        expResult.add(newPhone);

        assertEquals(expResult, result.getPhoneList());

    }

    @Test
    //virker ikke
    void editAddress(){
        //Address address = new Address("Svalevænget 2", cityInfo2);
        CityInfoDTO cityInfoDTO = new CityInfoDTO(cityInfo2);
        AddressDTO newAddress = new AddressDTO("Svalevænget 2", cityInfoDTO);

        Integer id = person3.getId();
        PersonDTO result = testFacade.editAddress(id, newAddress);

        assertEquals(newAddress, result.getAddressDTO());
    }



}
