package entities;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
@NamedQueries({
        @NamedQuery(name = "USER.deleteAllRows", query = "DELETE from Person"),
        @NamedQuery(name = "USER.getAllRows", query = "SELECT p from Person p")
        //@NamedQuery(name = "USER.getPersonById", query = "")
})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "firstname", nullable = false)
    private String firstname;
    @Column(name = "lastname", nullable = false)
    private String lastname;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Phone> phoneList;

    @ManyToOne
    private Address address;

    @ManyToMany (mappedBy = "persons", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List <Hobby> hobbyList;

    public Person() {
    }

    public Person(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneList = new ArrayList<>();
        this.hobbyList = new ArrayList<>();
        this.address = null;
    }

    public Person(int id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneList = new ArrayList<>();
        this.hobbyList = new ArrayList<>();
        this.address = null;
    }

    public Person(int id, String firstname, String lastname, String email, List<Phone> phoneList, Address address, List<Hobby> hobbies) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneList = phoneList;
        this.address = address;
        this.hobbyList = hobbies;

    }

    public Person(PersonDTO personDTO) {
        this.firstname = personDTO.getFirstname();
        this.lastname = personDTO.getLastname();
        this.email = personDTO.getEmail();
        this.phoneList = personDTO.getPhoneList() != null ? getNumberList(personDTO.getPhoneList()) : new ArrayList<>();
        this.hobbyList = personDTO.getHobbyList() != null ? getHobbyList(personDTO.getHobbyList()) : new ArrayList<>();
        this.address = personDTO.getAddressDTO() != null ? new Address(personDTO.getAddressDTO()) : null;

    }
    public void addHobby(Hobby hobby) {
        if (hobby != null) {
            this.hobbyList.add(hobby);
            hobby.getPersons().add(this);
        }
    }

    public void addPhone(Phone phone) {
        if (phone != null) {
            phone.setPerson(this);
            this.phoneList.add(phone);
        }
    }

    public void removeHobby(Hobby hobby) {
        if (hobby != null) {
            this.hobbyList.remove(hobby);
            hobby.getPersons().remove(this);
        }
    }

    public List<Phone> getNumberList(List<PhoneDTO> phoneDTOS) {
        ArrayList<Phone> list = new ArrayList<>();
        for (PhoneDTO p : phoneDTOS) {
            list.add(new Phone(p.getPhonenumber()));
        }
        return list;
    }

    public List<PhoneDTO> getPhonesDTOList(List<Phone> phones) {
        ArrayList<PhoneDTO> list = new ArrayList<>();
        if (phones != null) {
            for (Phone p : phones) {
                list.add(new PhoneDTO(p.getPhonenumber()));
            }
        }
        return list;
    }

    public List<Hobby> getHobbyList(List<HobbyDTO> hobbyDTOS) {
        ArrayList<Hobby> list = new ArrayList<>();
        for (HobbyDTO h : hobbyDTOS) {
            list.add(new Hobby(h.getName(), h.getWikiLink(), h.getCategory(), h.getType()));
        }
        return list;
    }

    public List<HobbyDTO> getHobbyDTOList(List<Hobby> hobby) {
        ArrayList<HobbyDTO> list = new ArrayList<>();
        for (Hobby h : hobby) {
            list.add(new HobbyDTO(h));
        }
        return list;
    }

    public void replaceHobbies(ArrayList<Hobby> hobbies) {
        this.hobbyList = hobbies;
    }




    public int getId() {
        return id;
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

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Hobby> getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(List<Hobby> hobbyList) {
        this.hobbyList = hobbyList;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstname + '\'' +
                ", lastName='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phones=" + phoneList +
                ", address=" + address +
                ", hobbies=" + hobbyList +
                '}';
    }
}