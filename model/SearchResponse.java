package testproject.com.app.testproject.model;

import java.io.Serializable;

public class SearchResponse implements Serializable {

    private Query query;

    private String batchcomplete;


    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String getBatchcomplete() {
        return batchcomplete;
    }

    public void setBatchcomplete(String batchcomplete) {
        this.batchcomplete = batchcomplete;
    }
}
