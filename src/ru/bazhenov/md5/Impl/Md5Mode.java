package ru.bazhenov.md5.Impl;

import java.util.Arrays;
import java.util.List;

public final class Md5Mode {

    private final static Function<Integer, Integer> F = (x, y, z) -> (x & y) | (~x & z);
    private final static Function<Integer, Integer> G = (x, y, z) -> (x & z) | (y & ~z);
    private final static Function<Integer, Integer> H = (x, y, z) -> x ^ y ^ z;
    private final static Function<Integer, Integer> I = (x, y, z) -> y ^ (x | ~z);

    private final List<Function<Integer, Integer>> operations;

    public Md5Mode(Integer id) {
        switch (id) {
            case 1:
                operations = Arrays.asList(F, G, H, I);
                break;
            case 2:
                operations = Arrays.asList(G, H, I, F);
                break;
            case 3:
                operations = Arrays.asList(H, I, F, G);
                break;
            case 4:
                operations = Arrays.asList(I, F, G, H);
                break;
            default:
                operations = Arrays.asList(F, G, H, I);
        }
    }

    public List<Function<Integer, Integer>> getOperations() {
        return operations;
    }

}
