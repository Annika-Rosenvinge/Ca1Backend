/* AUTHOR
 * Annika R Jespersen
 */

package dtos;

import entities.CityInfo;

public class CityInfoDTO {
    private String zipcode;
    private String city;

    public CityInfoDTO(){

    }

    public CityInfoDTO(String zipcode, String city){
        this.zipcode = zipcode;
        this.city = city;

    }

    public CityInfoDTO (CityInfo cityInfo){
        if(cityInfo.getZipcode() != null){
            this.zipcode = cityInfo.getZipcode();
        }
        this.city = cityInfo.getCity();
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
}
