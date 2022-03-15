/* AUTHOR
* Annika R Jespersen
*/

package dtos;

import entities.Address;

public class AddressDTO {
    private String street;
    private CityInfoDTO cityInfoDTO;
    String zipcode;
    String city;


    public AddressDTO(){

    }
    public AddressDTO(String street, CityInfoDTO cityInfoDTO){
        this.street = street;
        this.cityInfoDTO = cityInfoDTO;
    }

    public AddressDTO(String street, String zipcode, String city){
        this.street = street;
        this.zipcode = zipcode;

    }

    public AddressDTO (Address address){
        this.street = address.getStreet() == null ? null : address.getStreet();
        this.cityInfoDTO = address.getCityInfo() == null ? null : new CityInfoDTO(address.getCityInfo());
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public CityInfoDTO getCityInfoDTO() {
        return cityInfoDTO;
    }

    public void setCityInfoDTO(CityInfoDTO cityInfoDTO) {
        this.cityInfoDTO = cityInfoDTO;
    }

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

    @Override
    public String toString() {
        return "AddressDTO{" +
                "street: '" + street + '\'' +
                ", cityinfo: " + cityInfoDTO +
                '}';
    }
}
