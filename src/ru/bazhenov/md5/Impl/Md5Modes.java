package ru.bazhenov.md5.Impl;

public final class Md5Modes {

    public static Md5Mode FGHI() {
        return new Md5Mode(1);
    }

    public static Md5Mode GHIF() {
        return new Md5Mode(2);
    }

    public static Md5Mode HIFG() {
        return new Md5Mode(3);
    }

    public static Md5Mode IFGH() {
        return new Md5Mode(4);
    }

    public static Md5Mode DEFAULT() {
        return FGHI();
    }

}
