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

import java.util.Arrays;
import java.util.List;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.IntegerBox;
import org.gwtbootstrap3.client.ui.Row;

/**
 * @author Nicolas Morel
 */
public class BenchmarkView extends Composite {

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
    Row resultRow;

    private final List<Mechanism> mechanisms;

    public BenchmarkView() {
        mechanisms = Arrays.<Mechanism>asList( new GwtJackson() );

        initWidget( ourUiBinder.createAndBindUi( this ) );
    }

    @UiHandler( {"nbIterations", "nbItems"} )
    void onChangeNbIterations( ValueChangeEvent<Integer> event ) {
        launchBtn.setEnabled( (nbIterations.getValue() != null && nbIterations.getValue() > 0) && (nbItems.getValue() != null && nbItems
                .getValue() > 0) );
    }

    @UiHandler( "launchBtn" )
    void onClickLaunch( ClickEvent event ) {
        int colLength = 12 / mechanisms.size();
        String colClass = "col-lg-" + colLength;

        resultRow.clear();

        for ( Mechanism mechanism : mechanisms ) {
            mechanism.clear();
            SimplePanel panel = new SimplePanel( mechanism.getResult() );
            panel.addStyleName( colClass );
            resultRow.add( panel );
        }

        // initialize data
        final List<Person> persons = DataProvider.generateData( nbItems.getValue() );

        // launch the test
        for ( Mechanism mechanism : mechanisms ) {
            mechanism.test( persons, nbIterations.getValue() );
        }
    }
}
