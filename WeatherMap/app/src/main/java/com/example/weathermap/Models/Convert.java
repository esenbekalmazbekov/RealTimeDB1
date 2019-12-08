package com.example.weathermap.Models;

public class Convert {
    private static Integer answer;
    public static Integer convert(Integer ans){
        answer = (( 5 *(ans - 32)) / 9);
        return answer;
    }
}
