package ru.bazhenov.md5.Helpers;

public class StringHelper {

    public static String padLeft(String s, int n) {
        String newString = s;
        for (int i = s.length(); i < n; i++) {
            newString = "0" + newString;
        }
        return newString;
    }

}
