package ru.fordexsys.solomatintest.data.model;

/**
 * Created by Altair on 03-Feb-17.
 */

public class OkResponse {

    public static final int OK  = 1;
    public static final int FAIL  = 0;

    private int result;

    public OkResponse(int result) {
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
