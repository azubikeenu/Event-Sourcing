package com.azubike.ellipsis.events;

import java.util.*;

public record TestRecord (String firstName , String lastName) {
    public  TestRecord {
        if(firstName == null) firstName = "Richard";
        if(lastName == null) lastName = "Michael";
    }

    public static void main(String[] args) {
        TestRecord testRecord = new TestRecord(null, null);
        System.out.println(testRecord);

        Map< String , List<String>> map = new HashMap<>();
        map.computeIfAbsent("Richard"  , x -> new ArrayList<>());
        System.out.println(map);
    }
}


