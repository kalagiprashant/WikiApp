package testproject.com.app.testproject.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Terms implements Serializable {

    private ArrayList<String> description;

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }
}
