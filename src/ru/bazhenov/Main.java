package ru.bazhenov;

import ru.bazhenov.md5.IHashServiceProvider;
import ru.bazhenov.md5.Impl.Md5HashServiceProvider;
import ru.bazhenov.md5.Impl.Md5Modes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Main {

    private static long hammingDistance(String a, String b) {
        int length = Math.min(a.length(), b.length());
        return IntStream
                .range(0, length)
                .filter(i -> a.charAt(i) != b.charAt(i))
                .count();
    }

    private static void testScenario(List<String> testData, IHashServiceProvider md5) throws IOException {
        List<String> hashes = new ArrayList<>(testData.size());
        testData.forEach(item -> hashes.add(md5.BinaryDigest(item.getBytes())));

        double mr = 0.0;
        double r = 0.0;

        int divider = (testData.size() - 1) * testData.size();

        for (int i = 0; i < testData.size(); i++) {
            for (int j = 0; j < testData.size(); j++) {
                if (i == j) continue;
                mr += (double)hammingDistance(hashes.get(i), hashes.get(j)) / divider;
            }
        }

        for (int i = 0; i < testData.size(); i++)
        {
            for (int j = 0; j < testData.size(); j++)
            {
                if (i == j) continue;
                r += (mr - hammingDistance(hashes.get(i), hashes.get(j))) * (mr - hammingDistance(hashes.get(i), hashes.get(j))) / divider;
            }
        }

        r = Math.sqrt(r);

        System.out.println(String.format("MR: %f", mr));
        System.out.println(String.format("R: %f", r));

    }

    public static void main(String[] args) throws IOException {
        System.out.println(Integer.toBinaryString(127));
        IHashServiceProvider provider = new Md5HashServiceProvider(Md5Modes::DEFAULT);

        List<String> testData = Arrays.asList("aaaaa", "aaaab", "aaaba", "aabaa", "abaaa", "baaaa");
        testScenario(testData, provider);
        testData.forEach(item -> System.out.println(provider.BinaryDigest(item.getBytes())));

    }
}
