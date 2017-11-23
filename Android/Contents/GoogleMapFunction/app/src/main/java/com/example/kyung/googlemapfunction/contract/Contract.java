package com.example.kyung.googlemapfunction.contract;

import java.util.List;

/**
 * Created by Kyung on 2017-11-23.
 */

public class Contract {
    public interface IClickAction{
        void showList(List<String> objIdList);
        void showDetail(String objId);
    }
}
