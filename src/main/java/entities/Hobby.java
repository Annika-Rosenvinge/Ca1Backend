package entities;

import dtos.HobbyDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
@Table(name = "HOBBY")
@NamedQueries({
        @NamedQuery(name = "HOBBY.getAllRows", query = "SELECT h from HOBBY h"),
        @NamedQuery(name = "HOBBY.deleteAllRows", query = "DELETE FROM HOBBY")
})
public class Hobby implements Serializable {
    private static final long serialVersionUID = 1L;
    //ID generation
    @Id
    @Column(name = "name", length = 150, nullable = false, unique = false)
    private String name;

    @Column(name = "category", length = 90, nullable = true, unique = false)
    private String category;

    @Column(name = "type", length = 90, nullable = true, unique = false)
    private String type;

    @Column(name = "wikiLink", length = 90, nullable = true, unique = false)
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
