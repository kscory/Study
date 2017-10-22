package com.example.kyung.subwaysearch.model.SubwayLine;

/**
 * Created by Kyung on 2017-10-19.
 */

public class JsonSubwayLine {
    private SearchSTNBySubwayLineService SearchSTNBySubwayLineService;

    public SearchSTNBySubwayLineService getSearchSTNBySubwayLineService() {
        return SearchSTNBySubwayLineService;
    }

    public void setSearchSTNBySubwayLineService(SearchSTNBySubwayLineService SearchSTNBySubwayLineService) {
        this.SearchSTNBySubwayLineService = SearchSTNBySubwayLineService;
    }

    @Override
    public String toString() {
        return "ClassPojo [SearchSTNBySubwayLineService = " + SearchSTNBySubwayLineService + "]";
    }
}