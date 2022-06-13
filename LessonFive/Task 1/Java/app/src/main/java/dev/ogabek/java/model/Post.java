package dev.ogabek.java.model;

public class Post {

    private String caption = "";
    private String image = "";

    public Post(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public String getImage() {
        return image;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
