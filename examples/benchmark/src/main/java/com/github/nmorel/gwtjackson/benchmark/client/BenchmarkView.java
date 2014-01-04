/*
 * Copyright 2014 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.benchmark.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Nicolas Morel
 */
public class BenchmarkView extends Composite {

    public static class Person {

        private String firstName;

        private String lastName;

        private List<Person> childs;

        public Person() {
        }

        public Person( String firstName, String lastName, Person... childs ) {
            this.firstName = firstName;
            this.lastName = lastName;
            if ( null == childs || childs.length == 0 ) {
                this.childs = Collections.emptyList();
            } else {
                this.childs = Arrays.asList( childs );
            }
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName( String firstName ) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName( String lastName ) {
            this.lastName = lastName;
        }

        public List<Person> getChilds() {
            return childs;
        }

        public void setChilds( List<Person> childs ) {
            this.childs = childs;
        }
    }

    public static interface PersonMapper extends ObjectMapper<List<Person>> {}

    interface BenchmarkViewUiBinder extends UiBinder<Widget, BenchmarkView> {}

    private static BenchmarkViewUiBinder ourUiBinder = GWT.create( BenchmarkViewUiBinder.class );

    @UiField
    IntegerBox nbItems;

    @UiField
    IntegerBox nbIterations;

    @UiField
    Button launchBtn;

    @UiField
    Label resultSer;

    @UiField
    Label resultDeser;

    public BenchmarkView() {
        initWidget( ourUiBinder.createAndBindUi( this ) );
    }

    @UiHandler( "nbIterations" )
    void onChangeNbIterations( ValueChangeEvent<Integer> event ) {
        launchBtn.setEnabled( event.getValue() != null && event.getValue() > 0 );
    }

    @UiHandler( "launchBtn" )
    void onClickLaunch( ClickEvent event ) {
        resultSer.setText( null );
        resultDeser.setText( null );

        final int nbItem = nbItems.getValue();
        final int nbIter = nbIterations.getValue();

        // initialize data and warmup the mapper
        final PersonMapper mapper = GWT.create( PersonMapper.class );
        final List<Person> persons = new ArrayList<>( nbItem );
        for ( int i = 0; i < nbItem; i++ ) {
            persons.add(new Person( "John", "Doe", new Person( "Jane", "Doe" ), new Person( "Billy", "Doe", new Person( "Lily", "Doe" ) ) ));
        }
        final String jsonInput = mapper.write( persons );
        mapper.read( jsonInput );

        Operation<String> serCommand = new Operation<String>( nbIter ) {

            @Override
            protected String doExecute() {
                return mapper.write( persons );
            }

            @Override
            protected void onEnd() {
                resultSer.setText( "" + getTotalTime() / nbIter );
            }
        };

        Operation<List<Person>> deserCommand = new Operation<List<Person>>( nbIter ) {

            @Override
            protected List<Person> doExecute() {
                return mapper.read( jsonInput );
            }

            @Override
            protected void onEnd() {
                resultDeser.setText( "" + getTotalTime() / nbIter );
            }
        };

        Scheduler.get().scheduleIncremental( serCommand );
        Scheduler.get().scheduleIncremental( deserCommand );

        //        long startSerialization = System.currentTimeMillis();
        //        for ( int i = 0; i < nb; i++ ) {
        //            json = mapper.write( input );
        //        }
        //        long endSerialization = System.currentTimeMillis();
        //
        //        Person person = null;
        //        long startDeserialization = System.currentTimeMillis();
        //        for ( int i = 0; i < nb; i++ ) {
        //            person = mapper.read( json );
        //        }
        //        long endDeserialization = System.currentTimeMillis();
        //
        //        result.setText( "Serialization average time (ms) : " + ((endSerialization - startSerialization) / nb) + " / Deserialization " +
        //                "average time (ms) : " + ((endDeserialization - startDeserialization) / nb) );
    }
}
