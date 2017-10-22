package com.example.kyung.subwaysearch.model.SubwayInfo;

/**
 * Created by Kyung on 2017-10-19.
 */

public class JsonSubwayInfo {
    private SearchSTNTimeTableByFRCodeService SearchSTNTimeTableByFRCodeService;

    public SearchSTNTimeTableByFRCodeService getSearchSTNTimeTableByFRCodeService() {
        return SearchSTNTimeTableByFRCodeService;
    }

    public void setSearchSTNTimeTableByFRCodeService(SearchSTNTimeTableByFRCodeService SearchSTNTimeTableByFRCodeService) {
        this.SearchSTNTimeTableByFRCodeService = SearchSTNTimeTableByFRCodeService;
    }

    @Override
    public String toString() {
        return "ClassPojo [SearchSTNTimeTableByFRCodeService = " + SearchSTNTimeTableByFRCodeService + "]";
    }
}