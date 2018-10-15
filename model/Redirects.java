package testproject.com.app.testproject.model;

import java.io.Serializable;

public class Redirects implements Serializable
{

    private String to;

    private String index;

    private String from;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof String))
            return false;
        return obj.equals(this.index);
    }
}
