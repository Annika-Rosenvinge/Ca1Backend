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

public class Populator {

    private static Person person1 = new Person ( "Hans", "Pedersen", "HansPedersen@Mail.dk");
    private static Person person2 = new Person("Anna", "Andersen", "Anna.a@gmail.com");
    private static Person person3 = new Person("Kurt", "Mogenesen", "KM@minemail.dk");
    private static Person person4 = new Person ("Marianne", "Olafsen", "Marianne235@email.nu");
    private static Person person5 = new Person ("Ursula", "Rasmussen", "UrsulasElektroniskeMail@email.dk");
    private static Person person6 = new Person("Fin", "Hansen", "Hansen652@mail.dk");
    private static Person person7 = new Person("Kat", "Mathisen Sørensen", "Kms@mail.com");
    private static Person person8 = new Person("Jonna", "Christensen", "jo.ch@gmail.com");
    private static Person person9 = new Person("Lonni","Larsen", "ll2729@mail.dk");
    private static Person person10 = new Person("Mads", "Hagge Hjul Hansen", "mhhh1098@mail.dk");

    private static Phone phone1 = new Phone(34983434, "privat");
    private static Phone phone2 = new Phone(34125678, "Arbejde");
    private static Phone phone3 = new Phone(19325421, "Privat");
    private static Phone phone4 = new Phone(65438732, "Arbejde");
    private static Phone phone5 = new Phone(54327654, "Private");
    private static Phone phone6 = new Phone(62831460, "privat");
    private static Phone phone7 = new Phone(87712910, "arbejde");
    private static Phone phone8 = new Phone(87654567, "privat");
    private static Phone phone9 = new Phone(987654167,"privat" );
    private static Phone phone10 = new Phone(123287268, "arbejde");

    private static CityInfo cityInfo1 = new CityInfo("2610", "Rødovre");
    private static CityInfo cityInfo2 = new CityInfo("2800", "Kongens Lyngby");
    private static CityInfo cityInfo3 = new CityInfo("2605", "Brøndby");
    private static CityInfo cityInfo4 = new CityInfo("1000", "København K");
    private static CityInfo cityInfo5 = new CityInfo("3700", "Rønne");

    private static Address address1 = new Address("Milevej 2", cityInfo1);
    private static Address address2 = new Address("Nybrovej 305, 6-g", cityInfo2);
    private static Address address3 = new Address("Adressevej 1", cityInfo3);
    private static Address address4 = new Address("Jernbanevej 2",  cityInfo4);
    private static Address address5 = new Address("Godtgemt 3",  cityInfo5);
    private static Address address6 = new Address("Veronikavej 7", cityInfo1);
    private static Address address7 = new Address("Ege alle 9, 2th", cityInfo2);
    private static Address address8 = new Address("Randskilevej 87", cityInfo3);
    private static Address address9 = new Address("Stærevej 95", cityInfo4);
    private static Address address10 = new Address("Rødskærvej 391, 18mf", cityInfo5);

    private static Hobby hobby1 = new Hobby("Dans", "for alle", "indendørs", "www.dansdigihjel.dk");
    private static Hobby hobby2 = new Hobby("Svømmning","Voksen", "Udendørs", "www.vandhundene.dk");
    private static Hobby hobby3 = new Hobby("Fuglekiggeri", "For unge", "Udendørs", "www.spotendue.dk");
    private static Hobby hobby4 = new Hobby("Fægtning", "for babyer", "indedørs", "www.læratkæmpemoddinbaby.dk");
    private static Hobby hobby5 = new Hobby("Knivstikkeri", "for unge mænd der ønsker en tilværelse i fængsel", "i nattelivet", "www.politi.dk" );
    private static Hobby hobby6 = new Hobby("Yoga", "Kropsbevægelse", "Indendørs", "yoga.dk");
    private static Hobby hobby7 = new Hobby("Traktortræk", "Landbrug", "Udendørs", "Traktortræk.dk");


    public static void populate(){
        //alle personerne er i database, ingen grund til at køre denne igen
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        PersonFacade personFacade = PersonFacade.getPersonFacade(emf);
        try{
            em.getTransaction().begin();
            //Først gives person phone
            person1.addPhone(phone1);
            person2.addPhone(phone2);
            person3.addPhone(phone3);
            person4.addPhone(phone4);
            person5.addPhone(phone5);
            person6.addPhone(phone6);
            person7.addPhone(phone7);
            person8.addPhone(phone8);
            person9.addPhone(phone9);
            person10.addPhone(phone10);

            //og så en hobby
            person1.addHobby(hobby1);
            person2.addHobby(hobby2);
            person3.addHobby(hobby3);
            person4.addHobby(hobby4);
            person5.addHobby(hobby5);
            person6.addHobby(hobby6);
            person7.addHobby(hobby7);
            person8.addHobby(hobby6);
            person9.addHobby(hobby2);
            person10.addHobby(hobby3);

            //cityinfo persisteres
            em.persist(cityInfo1);
            em.persist(cityInfo2);
            em.persist(cityInfo3);
            em.persist(cityInfo4);
            em.persist(cityInfo5);

            //personerne får en adresse og der efter bliver de persisteret
            person1.setAddress(address1);
            em.persist(address1);
            em.persist(person1);
            em.merge(person1);


            person2.setAddress(address2);
            em.persist(address2);
            em.persist(person2);
            em.merge(person2);


            person3.setAddress(address3);
            em.persist(address3);
            em.persist(person3);
            em.merge(person3);


            person4.setAddress(address4);
            em.persist(address4);
            em.persist(person4);
            em.merge(person4);

            person5.setAddress(address5);
            em.persist(address5);
            em.persist(person5);
            em.merge(person5);


            person6.setAddress(address6);
            em.persist(address6);
            em.persist(person6);
            em.merge(person6);


            person7.setAddress(address7);
            em.persist(address7);
            em.persist(person7);
            em.merge(person7);

            person8.setAddress(address8);
            em.persist(address8);
            em.persist(person8);
            em.merge(person8);

            person9.setAddress(address9);
            em.persist(address9);
            em.persist(person9);
            em.merge(person9);

            person10.setAddress(address10);
            em.persist(address10);
            em.persist(person10);
            em.merge(person10);
            
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }
    
    public static void main(String[] args) {
        populate();
    }
}
