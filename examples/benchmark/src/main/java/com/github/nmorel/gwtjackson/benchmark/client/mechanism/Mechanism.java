package com.github.nmorel.gwtjackson.benchmark.client.mechanism;

import java.util.ArrayList;
import java.util.List;

import com.github.nmorel.gwtjackson.benchmark.client.Criteria;
import com.github.nmorel.gwtjackson.benchmark.client.Operation;
import com.github.nmorel.gwtjackson.benchmark.client.data.Person;
import com.github.nmorel.gwtjackson.benchmark.client.ui.ResultWidget;
import com.github.nmorel.gwtjackson.client.ObjectMapper;

/**
 * @author Nicolas Morel
 */
public abstract class Mechanism {

    interface ObjectMapperProvider {

        ObjectMapper<List<Person>> getMapper();
    }

    class ObjectMapperProviderSingleton implements ObjectMapperProvider {

        private ObjectMapper<List<Person>> singleton = newMapper();

        @Override
        public ObjectMapper<List<Person>> getMapper() {
            return singleton;
        }
    }

    class ObjectMapperProviderPrototype implements ObjectMapperProvider {

        @Override
        public ObjectMapper<List<Person>> getMapper() {
            return newMapper();
        }
    }

    private ResultWidget result;

    protected Mechanism( String title ) {
        this.result = new ResultWidget( title );
    }

    public List<Operation> prepare( final List<Person> datas, Criteria criteria ) {
        final ObjectMapperProvider mapperProvider;
        if ( criteria.isSingletonMapper() ) {
            mapperProvider = new ObjectMapperProviderSingleton();
        } else {
            mapperProvider = new ObjectMapperProviderPrototype();
        }

        result.getResultSer().setVisible( criteria.isSerialization() );
        result.getResultDeser().setVisible( criteria.isDeserialization() );

        List<Operation> operations = new ArrayList<>();

        if ( criteria.isSerialization() ) {
            if ( criteria.isSingletonMapper() ) {
                // warmup the mapper
                mapperProvider.getMapper().write( datas );
            }
            operations.add( new Operation( criteria.getNbIterations(), result.getResultSer() ) {
                @Override
                protected void doExecute() {
                    mapperProvider.getMapper().write( datas );
                }
            } );
        }

        if ( criteria.isDeserialization() ) {
            final String jsonInput = mapperProvider.getMapper().write( datas );
            operations.add( new Operation( criteria.getNbIterations(), result.getResultDeser() ) {
                @Override
                protected void doExecute() {
                    mapperProvider.getMapper().read( jsonInput );
                }
            } );
        }

        return operations;
    }

    public ResultWidget getResult() {
        return result;
    }

    public void clear() {
        result.getResultSer().setResult( null );
        result.getResultDeser().setResult( null );
    }

    protected abstract ObjectMapper<List<Person>> newMapper();
}
