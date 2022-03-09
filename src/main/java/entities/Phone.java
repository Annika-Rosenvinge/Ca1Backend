package entities;

import dtos.PhoneDTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PHONE")
public class Phone implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phonenumber", updatable = true, nullable = false)
    private Integer phonenumber;
    @Column(name = "description")
    private String description;

    @ManyToOne
    private Person person;

    public Phone (){

    }

    public Phone (Integer phonenumber, String description){
        this.phonenumber = phonenumber;
        this.description = description;
    }

    public Phone (Integer phonenumber){
        this.phonenumber = phonenumber;
    }

    public Phone (PhoneDTO phoneDTO){
        this.phonenumber = phoneDTO.getPhonenumber();
        this.description = phoneDTO.getDescription();
    }

    public Integer getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Integer phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "number: '" + phonenumber + '\'' +
                ", description: '" + description + '\'' +
                '}';
    }
}
