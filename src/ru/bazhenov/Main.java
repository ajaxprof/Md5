package ru.bazhenov;

import ru.bazhenov.md5.IHashServiceProvider;
import ru.bazhenov.md5.Impl.Md5HashServiceProvider;
import ru.bazhenov.md5.Impl.Md5Modes;

public class Main {

    public static void main(String[] args) throws Exception {

        IHashServiceProvider md5 = new Md5HashServiceProvider(Md5Modes::DEFAULT);

        String s = "ngfesnguidfshniughdsiufghidufsghidufshgidfsngjvfsdngoewrhutifnsdigbwerfuiauyth542h5i34hu5i2h34uy53iu";

        String digest = md5.HexDigest(s.getBytes());

        System.out.println(digest);

    }
}
