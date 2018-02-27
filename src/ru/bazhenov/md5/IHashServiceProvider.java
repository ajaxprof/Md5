package ru.bazhenov.md5;

import java.io.IOException;

public interface IHashServiceProvider {

    String HexDigest(byte[] data) throws IOException;

}
