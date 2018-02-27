package ru.bazhenov.md5;

public interface IHashServiceProvider {

    String HexDigest(byte[] data);
    String BinaryDigest(byte[] data);

}
