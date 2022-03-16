package entities;

import dtos.HobbyDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Hobby")
@NamedQueries({
        @NamedQuery(name = "HOBBY.getAllRows", query = "SELECT h from Hobby h"),
        @NamedQuery(name = "HOBBY.deleteAllRows", query = "DELETE FROM Hobby")
})
public class Hobby implements Serializable {
    private static final long serialVersionUID = 1L;
    //ID generation
    @Id
    @Column(name = "name", length = 170, nullable = false, unique = false)
    private String name;

    @Column(name = "category", length = 170, nullable = true, unique = false)
    private String category;

    @Column(name = "type", length = 170, nullable = true, unique = false)
    private String type;

    @Column(name = "wikiLink", length = 170, nullable = true, unique = false)
    private String wikiLink;

    @ManyToMany
    private List <Person> persons;

    public Hobby() {
    }

    public Hobby(String name, String category, String type, String wikiLink) {
        this.name = name;
        this.category = category;
        this.type = type;
        this.wikiLink = wikiLink;
        this.persons = new ArrayList<>();
    }

    public Hobby(HobbyDTO hobbyDTO){
        this.name = hobbyDTO.getName();
        this.category = hobbyDTO.getCategory();
        this.type = hobbyDTO.getType();
        this.wikiLink = hobbyDTO.getWikiLink();
        this.persons = new ArrayList<>();
    }

    public static List<Hobby> getEntites(List<HobbyDTO> hobbyDTOS) {
        List<Hobby> hobbies = new ArrayList<>();
        if(hobbyDTOS != null){
            hobbyDTOS.forEach(hobbyDTO -> hobbies.add(new Hobby(hobbyDTO.getName(), hobbyDTO.getWikiLink(), hobbyDTO.getCategory(), hobbyDTO.getType())));
        }
        return hobbies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public List<Person> getPersons() {
        return persons;
    }


    @Override
    public String toString() {
        return "Hobby{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", wikiLink='" + wikiLink + '\'' +
                '}';
    }
}
