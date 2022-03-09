package dtos;

import entities.Hobby;

public class HobbyDTO {
    private String name;
    private String category;
    private String type;
    private String wikiLink;

    public HobbyDTO() {
    }

    public HobbyDTO(String name, String category, String type, String wikiLink) {
        this.name = name;
        this.category = category;
        this.type = type;
        this.wikiLink = wikiLink;
    }

    public HobbyDTO(Hobby hobby){
        this.name = hobby.getName();
        this.category = hobby.getCategory();
        this.type = hobby.getType();
        this.wikiLink = hobby.getWikiLink();
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
        return "HobbyDTO{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", wikiLink='" + wikiLink + '\'' +
                '}';
    }
}
