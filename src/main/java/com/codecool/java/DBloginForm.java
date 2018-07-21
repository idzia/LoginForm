package com.codecool.java;

import java.util.HashMap;
import java.util.Map;

public class DBloginForm {

    private Map<String, String> dbpairs;


    public DBloginForm () {
        dbpairs = new HashMap<String, String>();
        dbpairs.put("idzia", "12345");
        dbpairs.put("pidzi", "54321");
        dbpairs.put("mela", "12345");
        dbpairs.put("tymi", "12345");
    }

    public Map<String, String> getPairs() {
        return dbpairs;
    }

}
