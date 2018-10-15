package testproject.com.app.testproject.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Query implements Serializable {

    private ArrayList<Pages> pages;

    private ArrayList<Redirects> redirects;

    public ArrayList<Pages> getPages() {
        return pages;
    }

    public void setPages(ArrayList<Pages> pages) {
        this.pages = pages;
    }

    public ArrayList<Redirects> getRedirects() {
        return redirects;
    }

    public void setRedirects(ArrayList<Redirects> redirects) {
        this.redirects = redirects;
    }
}
