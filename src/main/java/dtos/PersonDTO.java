package dtos;

import entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonDTO {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private List<PhoneDTO> phoneList;
    private List <HobbyDTO> hobbyList;
    private AddressDTO addressDTO;

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.firstname = person.getFirstname();
        this.lastname = person.getLastname();
        this.email = person.getEmail();
        this.phoneList = person.getPhoneList() != null ? person.getPhonesDTOList(person.getPhoneList()) : new ArrayList<>();
        this.hobbyList = person.getHobbyList() != null ? person.getHobbyDTOList(person.getHobbyList()) : new ArrayList<>();
        this.addressDTO = person.getAddress() == null ? new AddressDTO() : new AddressDTO(person.getAddress());
    }

    public PersonDTO() {
    }

    public static List<PersonDTO> getDtos(List<Person> persons) {
        List<PersonDTO> personDTOs = new ArrayList();
        if(persons != null) {
            persons.forEach(person -> personDTOs.add(new PersonDTO(person)));
        }
        return personDTOs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<PhoneDTO> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<PhoneDTO> phoneList) {
        this.phoneList = phoneList;
    }

    public List<HobbyDTO> getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(List<HobbyDTO> hobbyList) {
        this.hobbyList = hobbyList;
    }

    public AddressDTO getAddressDTO() { return addressDTO; }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phoneList=" + phoneList +
                ", hobbyList=" + hobbyList +
                ", addressDTO=" + addressDTO +
                '}';
    }
}
