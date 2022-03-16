package rest;

//Her er alle vores endpoints. Husk ikke at lave endpoints før test er lavet i PersonFacadeTest
//og efter endpoint er lavet så skal der laves test i PersonResourceTest

import com.google.gson.*;
import dtos.PersonDTO;
import dtos.PersonsDTO;
import facades.PersonFacade;
import utils.EMF_Creator;

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

    @Path("/delete/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String deletePerson(@PathParam("id") Integer id) {
        PersonDTO newPersonDTO = FACADE.deletePersonById(id);
        return GSON.toJson(newPersonDTO);
    }

}
