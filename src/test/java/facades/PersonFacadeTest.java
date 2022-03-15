package facades;

import entities.*;
import dtos.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

    private static Address address1 = new Address ("Mile alle 23", "2610" );
    private static Address address2 = new Address("Prinsessevej 34, 2th", cityInfo2);
    private static Address address3 = new Address("Ege alle 87", cityInfo3);
    private static Address address4 = new Address( "Hovedvejen", cityInfo1);

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
        //slet


        em.getTransaction().commit();

        try{
            em.getTransaction().begin();
            //lav personerne, addresser ect, sørg for at alle kommer med


            em.getTransaction().commit();

        }finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown(){

    }

    @Test
    void createPerson(){
        EntityManager em = emf.createEntityManager();

        //Lav ny person
        Person person  = new Person("Maria Sofie", "Madsen", "M.Sofia@gmail.com");
        CityInfo cityInfo = new CityInfo("2730", "Herlev");
        Address address = new Address("Byvej 27", cityInfo);
        Phone phone = new Phone(63917428, "privat");

        person.setAddress(address);
        person.addPhone(phone);
        person.addHobby(hobby4);

        //lav en try finally med PersonDTO
        try{
            em.getTransaction().begin();

            PersonDTO personDTO = new PersonDTO(person);
            PersonDTO result = testFacade.createPerson(personDTO);
            PersonDTO expResult = new PersonDTO(person);
            assertEquals(expResult.getId(), result.getId());

            em.getTransaction().commit();
        }finally {
            em.close();
        }



    }

    //Andre test, husk at bruge assertEquals eller en anden måde der sammenligner det man tester


}
