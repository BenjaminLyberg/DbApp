package andben.com.dbapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Benjamin on 26.06.2017.
 */

// Klasse for individuelle artikkel-objekter

public class Article {
    private int id;
    private String type;
    private String title;
    private String image;
    private String label;

    public Article() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}
