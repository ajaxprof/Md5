package ru.bazhenov.md5.Impl;

@FunctionalInterface
interface Function<T, R> {
    R apply(T x, T y, T z);
}
