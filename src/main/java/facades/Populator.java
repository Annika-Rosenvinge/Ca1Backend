/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {

    private static Person person1 = new Person ("Hans", "Pedersen", "HansElskerKo@gamil.com");
    private static Person person2 =  new Person("Anna", "Andersen", "AnnaAnd@gmail.com");
    private static Person person3 = new Person("Kurt", "Mogenesen", "KM@minemail.dk");
    private static Person person4 = new Person ("Marianne", "Olafsen", "Marianne235@email.nu");
    private static Person person5 = new Person ("Ursula", "Rasmussen", "UrsulasElektroniskeMail@email.dk");

    private static Phone phone1 = new Phone(34983434, "privat");
    private static Phone phone2 = new Phone(34125678, "Arbejde");
    private static Phone phone3 = new Phone(19325421, "Privat");
    private static Phone phone4 = new Phone(65438732, "Arbejde");
    private static Phone phone5 = new Phone(54327654, "Private");

    private static CityInfo cityInfo1 = new CityInfo("2610", "Rødovre");
    private static CityInfo cityInfo2 = new CityInfo("2800", "Kongens Lyngby");
    private static CityInfo cityInfo3 = new CityInfo("2605", "Brøndby");
    private static CityInfo cityInfo4 = new CityInfo("1000", "København K");
    private static CityInfo cityInfo5 = new CityInfo("3700", "Rønne");

    private static Address address1 = new Address("Milevej 2", "2 th", cityInfo1);
    private static Address address2 = new Address("Nybrovej 305","R-55", cityInfo2);
    private static Address address3 = new Address("Adressevej 1","a", cityInfo3);
    private static Address address4 = new Address("Jernbanevej 2", "", cityInfo4);
    private static Address address5 = new Address("Godtgemt 3", "", cityInfo5);

    private static Hobby hobby1 = new Hobby("Dans", "for alle", "indendørs", "www.dansdigihjel.dk");
    private static Hobby hobby2 = new Hobby("Svømmning","Voksen", "Udendørs", "www.vandhundene.dk");
    private static Hobby hobby3 = new Hobby("Fuglekiggeri", "For unge", "Udendørs", "www.spotendue.dk");
    private static Hobby hobby4 = new Hobby("Fægtning", "for babyer", "indedørs", "www.læratkæmpemoddinbaby.dk");
    private static Hobby hobby5 = new Hobby("Knivstikkeri", "for unge mænd der ønsker en tilværelse i fængsel", "i nattelivet", "www.politi.dk" );


    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        PersonFacade personFacade = PersonFacade.getPersonFacade(emf);
        try{
            em.getTransaction().begin();

            //Person 1
            //giv personen en hobby
            person1.addHobby(hobby1);
            //giv personen et nummer
            person1.addPhone(phone1);
            //giv addressen en city info og giv personen en adresse
            em.persist(cityInfo1);
            em.persist(address1);
            address1.setCityInfo(cityInfo1);
            em.merge(address1);
            em.persist(person1);
            person1.setAddress(address1);
            em.merge(person1);

            //Person
            person2.addHobby(hobby2);
            //giv personen et nummer
            person2.addPhone(phone2);
            //giv addressen en city info og giv personen en adresse
            em.persist(cityInfo2);
            em.persist(address2);
            address2.setCityInfo(cityInfo2);
            em.merge(address2);
            em.persist(person2);
            person2.setAddress(address2);
            em.merge(person2);

            //person3
            person3.addHobby(hobby3);
            //giv personen et nummer
            person3.addPhone(phone3);
            //giv addressen en city info og giv personen en adresse
            em.persist(cityInfo3);
            em.persist(address3);
            address3.setCityInfo(cityInfo3);
            em.merge(address3);
            em.persist(person3);
            person3.setAddress(address3);
            em.merge(person3);

            //person4
            person4.addHobby(hobby4);
            //giv personen et nummer
            person4.addPhone(phone4);
            //giv addressen en city info og giv personen en adresse
            em.persist(cityInfo4);
            em.persist(address4);
            address4.setCityInfo(cityInfo4);
            em.merge(address4);
            em.persist(person4);
            person4.setAddress(address4);
            em.merge(person4);

            //person5
            person5.addHobby(hobby5);
            //giv personen et nummer
            person5.addPhone(phone5);
            //giv addressen en city info og giv personen en adresse
            em.persist(cityInfo5);
            em.persist(address5);
            address5.setCityInfo(cityInfo5);
            em.merge(address5);
            em.persist(person5);
            person5.setAddress(address5);
            em.merge(person5);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }
    
    public static void main(String[] args) {
        populate();
    }
}
