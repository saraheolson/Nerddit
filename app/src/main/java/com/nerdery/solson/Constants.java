package com.nerdery.solson;

/**
 * Constants File The one stop place for changing values in an application. Hard and fast rules, any
 * value that may change between dev/prod builds should go here so there is only one file to look at
 * and go through
 */
public final class Constants {

    /**
     * Suppress default constructor for noninstantiability
     */
    private Constants() {
        throw new AssertionError();
    }

    /**
     * The base URL for the Reddit API.
     */
    public static final String ENDPOINT_URL = "http://reddit.com/";
}
