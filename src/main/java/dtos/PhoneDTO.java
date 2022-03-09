/* AUTHOR
 * Annika R Jespersen & Louise S. Estrup
 */
package dtos;

import entities.Phone;

public class PhoneDTO {
    private Integer phonenumber;
    private String description;

    public PhoneDTO(){

    }

    public PhoneDTO(Phone phone){
        this.phonenumber = phone.getPhonenumber();
        this.description = phone.getDescription();
    }

    public PhoneDTO(Integer phonenumber){
        this.phonenumber = phonenumber;
    }

    public PhoneDTO(Integer phonenumber, String description){
        this.phonenumber = phonenumber;
        this.description = description;

    }

    public PhoneDTO(Integer phonenumber, String description, PersonDTO personDTO){
        this.phonenumber = phonenumber;
        this.description = description;
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

    @Override
    public String toString(){
        return "PhoneDTO{"+
                "number: '" + phonenumber + '\'' +
                ", description: '" + description + '\'' +
                '}';
    }

}
