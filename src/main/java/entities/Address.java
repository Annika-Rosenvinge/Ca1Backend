/* AUTHOR
 * Annika R Jespersen
 */

package entities;

import dtos.AddressDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "Address")
@NamedQueries({
        @NamedQuery(name = "ADDRESS.getAllRows", query = "SELECT a from Address a"),
        @NamedQuery(name = "ADDRESS.deleteAllRows", query = "DELETE FROM Address"),
        @NamedQuery(name = "ADDRESS.getAddress", query = "SELECT a from Address a WHERE a.street = :street")
        })

public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    //ID generations
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    //rest of ADDRESS attributes
    @Column(name = "street", length = 150, nullable = false, unique = false)
    private String street;

    @OneToMany (mappedBy = "address", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List <Person> persons;

    @ManyToOne
    private CityInfo cityInfo;


    public Address(){

    }
    public Address(String street){
        this.street = street;
        this.cityInfo = null;
    }

    public Address(String street, CityInfo cityInfo){
        this.street = street;
        this.cityInfo = cityInfo;
    }

    public Address (AddressDTO addressDTO){
        this.street = addressDTO.getStreet();
        this.cityInfo = new CityInfo(addressDTO.getCityInfoDTO());
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public List<Person> getPersons(){
        return persons;
    }

    public void addPerson (Person person){
        if (person != null){
            person.setAddress(this);
            this.persons.add(person);
        }
    }
    @Override
    public String toString() {
        return "Address{" +
                "street: '" + street + '\'' +
                ", cityInfo: " + cityInfo +
                '}';
    }

}
