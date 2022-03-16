/* AUTHOR
 * Annika R Jespersen & Louise S. Estrup
 */
package entities;

import dtos.PhoneDTO;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
//Bemærk at denne her er skrevet med lille efter det første bogstav, i know, ikke det smarteste men nu er det sådan her
@Table(name = "Phone")

@NamedQueries({
        @NamedQuery(name = "PHONE.getAll", query = "SELECT p from Phone p "),
        @NamedQuery(name = "PHONE.deleteAllRows", query = "DELETE FROM Phone "),
        @NamedQuery(name = "PHONE.getPhone", query = "SELECT p from Phone p WHERE p.phonenumber = :phonenumber")
        //måske en mere hvor man kan vælge telefon nummer på baggrund af user id, kan være at den måske skal være som metode
})

public class Phone implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "phonenumber", updatable = true, nullable = false)
    private Integer phonenumber;
    @Column(name = "info")
    private String info;

    @ManyToOne
    private Person person;

    public Phone (){

    }

    public Phone (Integer phonenumber, String description){
        this.phonenumber = phonenumber;
        this.info = description;
    }

    public Phone (Integer phonenumber){
        this.phonenumber = phonenumber;
    }

    public Phone (PhoneDTO phoneDTO){
        this.phonenumber = phoneDTO.getPhonenumber();
        this.info = phoneDTO.getDescription();
    }

    public static java.util.List<Phone> getEntites(java.util.List<PhoneDTO> phoneDTOS) {
        List<Phone> phones = new ArrayList<>();
        if(phoneDTOS != null){
            phoneDTOS.forEach(phoneDTO -> phones.add(new Phone(phoneDTO.getPhonenumber(), phoneDTO.getDescription())));
        }
        return phones;
    }

    public Integer getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Integer phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getDescription() {
        return info;
    }

    public void setDescription(String description) {
        this.info = description;
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
                ", description: '" + info + '\'' +
                '}';
    }
}
