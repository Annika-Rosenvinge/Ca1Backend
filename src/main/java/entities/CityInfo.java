package entities;

import dtos.CityInfoDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "CITYINFO")
@NamedQueries({
        @NamedQuery(name = "CITYINFO.deleteAllRows", query = "DELETE from CITYINFO"),
        @NamedQuery(name = "CITYINFO.getCityInfo", query = "SELECT c from CITYINFO c WHERE c.zipcode = :zipcode"),
        @NamedQuery(name = "CITYINFO.getAllRows", query = "SELECT c FROM CITYINFO c")
})


public class CityInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //måske id column skal med.
    @Column(name = "zipcode", length = 10, unique = true)
    private String zipcode;
    @Column(name = "city", length=90, unique = false)
    private String city;

    @OneToMany (mappedBy = "cityInfo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Address> addresses;

    //Constructors

    public CityInfo(){

    }
    public CityInfo(String zipcode, String city){
        this.zipcode = zipcode;
        this.city = city;
    }

    public CityInfo (CityInfoDTO cityInfoDTO){
        this.zipcode = cityInfoDTO.getZipcode();
        this.city = cityInfoDTO.getCity();
    }

    //Getters and setters

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    //others
    public void addAddress (Address address) {
        if (address != null){
            address.setCityInfo(this);
            this.addresses.add(address);
        }
    }

    @Override
    public String toString (){
        return "CityInfo{" + "zipcode: ' "+ zipcode + '\'' +
                ", city: '" + city + '\'' +
                ", addresses: " + addresses +
                '}';
    }
}
