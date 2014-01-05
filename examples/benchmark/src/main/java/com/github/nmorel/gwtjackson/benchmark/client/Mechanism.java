package com.github.nmorel.gwtjackson.benchmark.client;

import java.util.List;

/**
 * @author Nicolas Morel
 */
public abstract class Mechanism {

    private ResultWidget result;

    protected Mechanism( ResultWidget result ) {
        this.result = result;
    }

    public abstract void test( List<Person> datas, int nbIterations );

    public ResultWidget getResult() {
        return result;
    }

    public void clear() {
        result.getResultSer().setResult( null );
        result.getResultDeser().setResult( null );
    }
}
