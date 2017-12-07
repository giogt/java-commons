package org.giogt.commons.uri;

public enum URIScheme {

    FILE("file"),
    FTP("ftp"),
    HTTP("http"),
    HTTPS("https"),
    SFTP("sftp");

    private final String scheme;

    URIScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getScheme() {
        return scheme;
    }
}
