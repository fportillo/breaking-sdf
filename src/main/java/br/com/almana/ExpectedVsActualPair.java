package br.com.almana;

public class ExpectedVsActualPair {

    private String expected;
    private String actual;

    public ExpectedVsActualPair(String expected, String actual) {
        super();
        this.expected = expected;
        this.actual = actual;
    }

    public String getExpected() {
        return expected;
    }

    public String getActual() {
        return actual;
    }

}
