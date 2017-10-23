package com.example.kyung.subwaysearch.model.SubwayNameService;

/**
 * Created by Kyung on 2017-10-19.
 */

public class JsonSubwayService {
    private SearchInfoBySubwayNameService SearchInfoBySubwayNameService;

    public SearchInfoBySubwayNameService getSearchInfoBySubwayNameService() {
        return SearchInfoBySubwayNameService;
    }

    public void setSearchInfoBySubwayNameService(SearchInfoBySubwayNameService SearchInfoBySubwayNameService) {
        this.SearchInfoBySubwayNameService = SearchInfoBySubwayNameService;
    }

    @Override
    public String toString() {
        return "ClassPojo [SearchInfoBySubwayNameService = " + SearchInfoBySubwayNameService + "]";
    }
}