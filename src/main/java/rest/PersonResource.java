package rest;

//Her er alle vores endpoints. Husk ikke at lave endpoints før test er lavet i PersonFacadeTest
//og efter endpoint er lavet så skal der laves test i PersonResourceTest

import com.google.gson.*;
import dtos.*;
import entities.Phone;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.faces.component.PartialStateHolder;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


//Det her er hvad der kommer efter /api
//så denne her er www.arosenvinge.dk/ca1backend/api/person, alle de andre endpoints kommer efter /person
@Path("/person")
public class PersonResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PersonFacade FACADE= PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @Path("/status")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String status(){
        return "{\" message: \":\" The API is up and running\"}";
    }

    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPerson(String person){
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO newPerson = FACADE.createPerson(personDTO);

        return GSON.toJson(newPerson);
    }

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findAllPersons(){
        PersonsDTO personsDTO = FACADE.findAllPersons();

        return GSON.toJson(personsDTO);

    }

    @Path("/{id}")
    //person facade test virker
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findPersonById(@PathParam("id") Integer id){
        PersonDTO personDTO = FACADE.findPersonById(id);

        return GSON.toJson(personDTO);
    }

    @Path("/edit/{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String editPerson(@PathParam("id") Integer id, String person) {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        personDTO.setId(id);
        PersonDTO newPersonDTO = FACADE.editPerson(personDTO);

        return GSON.toJson(newPersonDTO);
    }


    @Path("/editphone/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editphone(@PathParam("id") Integer id, String phone){
        PhoneDTO phoneDTO = GSON.fromJson(phone, PhoneDTO.class);
        PersonDTO personDTO = FACADE.editPhone(id, phoneDTO);

        return GSON.toJson(personDTO);
    }

    @Path("/delete/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String deletePerson(@PathParam("id") Integer id) {
        PersonDTO newPersonDTO = FACADE.deletePersonById(id);

        return GSON.toJson(newPersonDTO);
    }

    @Path("/editaddress/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editaddress(@PathParam("id") Integer id, String address){
        AddressDTO addressDTO = GSON.fromJson(address, AddressDTO.class);
        PersonDTO personDTO = FACADE.editAddress(id, addressDTO);

        return GSON.toJson(personDTO);

    }

    @Path("/allzipcode")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findAllZipCodes(){
        CityInfosDTO cityInfosDTO = FACADE.findAllZipCodes();
        return GSON.toJson(cityInfosDTO);
    }

    /*@Path("/addhobby/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addHobby(@PathParam("id")int id, String person, String hobby){
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        personDTO.setId(id);
        PersonDTO newPersonDTO = FACADE.addHobby(id, hobby);

        return GSON.toJson(newPersonDTO);
    }*/

}
