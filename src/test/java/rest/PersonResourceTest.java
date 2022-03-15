package rest;


import entities.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    Person person1 = new Person("Rick", "Smith", "RickRules@TheGalaxy.com");
    Person person2 = new Person("Morty", "Smith", "OhJeez@JessicaIsNice.com");

    Phone phone1 = new Phone(93228538, "Private");
    Phone phone2 = new Phone(38197318, "Private");

    Hobby hobby1 = new Hobby("Drinking", "Unhealthy", "Adults only", "adultswim.com");
    Hobby hobby2 = new Hobby("Staring at Jessica", "Unhealthy", "Creepy", "adultswim.com");

    CityInfo cityInfo1 = new CityInfo("80901", "Colorado Springs");

    Address address1 = new Address("Ordinary Road 4", cityInfo1);

    static HttpServer startServer(){
        ResourceConfig resourceConfig = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);
    }

    @BeforeAll
    public static void setUpClass(){
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();

        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @BeforeEach
    void setup(){
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();
            em.createNamedQuery("PHONE.deleteAllRows", Phone.class).executeUpdate();
            em.createNamedQuery("HOBBY.deleteAllRows", Hobby.class).executeUpdate();
            em.createNamedQuery("USER.deleteAllRows", Person.class).executeUpdate();
            em.createNamedQuery("ADDRESS.deleteAllRows", Address.class).executeUpdate();
            em.createNamedQuery("CITYINFO.deleteAllRows", CityInfo.class).executeUpdate();

            //personerne og deres informationer, persisteres
            em.persist(cityInfo1);
            em.persist(address1);
            person1.setAddress(address1);
            person1.addPhone(phone1);
            person1.addHobby(hobby1);
            em.persist(person1);
            em.merge(person1);

            person2.setAddress(address1);
            person2.addPhone(phone2);
            person2.addHobby(hobby2);
            em.persist(person2);
            em.merge(person2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }


    @AfterAll
    public static void closeServer(){
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @Test
    void createPerson(){

    }

    @Test
    void findAllPersons(){

    }


    /*@Test
    void findPersonById() throws Exception{
        System.out.println("Searching for the person with id:" + person1.getId());
        String expResult = person1.getFirstname();
        given()
                .pathParam("id", person1.getId())
                .contentType("application/json")
                .get("/person/{id}")
                .then()
                .assertThat()
                .statusCode(200);
    }*/

}
