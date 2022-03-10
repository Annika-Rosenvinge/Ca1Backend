/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dtos.*;
import entities.*;

import java.util.*;

import com.google.gson.*;
import entities.Person;
import entities.Phone;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Utility {
    private static Gson gson = new GsonBuilder().create();
    
    public static void printAllProperties() {
            Properties properties = System.getProperties();
            Set<Object> keyset = properties.keySet();
            for(Object obj : keyset){
                System.out.println("System Property :{" + obj.toString() +
                        "," + System.getProperty(obj.toString()) + "}");
            }
    }

    public static boolean ValidatePerson(Person person){
        return person.getEmail() != null || person.getFirstname() != null || person.getLastname() != null;
    }

    public static boolean ValidatePersonDTO(PersonDTO person){
        System.out.println("Validation: " + person.getFirstname() != null || person.getLastname() != null || person.getEmail() != null);
        return person.getEmail() != null || person.getFirstname() != null || person.getLastname() != null;
    }

    
    public static void main(String[] args) throws UnsupportedEncodingException {

    }

    public static List convertList(List list, Class<?> type){
        List newList = new ArrayList();
        for (Object ent : list){
            if (type == Phone.class) newList.add(new Phone((PhoneDTO) ent));
            if (type == PhoneDTO.class) newList.add(new PhoneDTO((Phone) ent));
            if (type == Hobby.class) newList.add(new Hobby((HobbyDTO) ent));
            if (type == HobbyDTO.class) newList.add(new Hobby((HobbyDTO) ent));
            if (type == CityInfo.class) newList.add(new CityInfoDTO((CityInfo) ent));
            if (type == PersonDTO.class) newList.add(new PersonDTO((Person) ent));

        }
        return newList;
    }

}
