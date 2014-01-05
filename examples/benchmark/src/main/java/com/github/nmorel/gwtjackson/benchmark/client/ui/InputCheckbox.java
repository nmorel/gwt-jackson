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

package com.github.nmorel.gwtjackson.benchmark.client.ui;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasValue;

/**
 * @author Nicolas Morel
 */
public class InputCheckbox extends FocusWidget implements HasValue<Boolean>, IsEditor<LeafValueEditor<Boolean>> {

    private LeafValueEditor<Boolean> editor;

    private boolean valueChangeHandlerInitialized;

    private final InputElement inputElem;

    public InputCheckbox() {
        super( DOM.createInputCheck() );
        inputElem = getElement().cast();
    }

    @Override
    public Boolean getValue() {
        if ( isAttached() ) {
            return inputElem.isChecked();
        } else {
            return inputElem.isDefaultChecked();
        }
    }

    @Override
    public void setValue( Boolean value ) {
        setValue( value, false );
    }

    @Override
    public void setValue( Boolean value, boolean fireEvents ) {
        if ( value == null ) {
            value = Boolean.FALSE;
        }

        Boolean oldValue = getValue();
        inputElem.setChecked( value );
        inputElem.setDefaultChecked( value );
        if ( value.equals( oldValue ) ) {
            return;
        }
        if ( fireEvents ) {
            ValueChangeEvent.fire( this, value );
        }
    }

    public void setForId( String id ) {
        inputElem.setPropertyString( "id", id );
    }

    @Override
    public HandlerRegistration addValueChangeHandler( ValueChangeHandler<Boolean> handler ) {
        // Is this the first value change handler? If so, time to add handlers
        if ( !valueChangeHandlerInitialized ) {
            ensureDomEventHandlers();
            valueChangeHandlerInitialized = true;
        }
        return addHandler( handler, ValueChangeEvent.getType() );
    }

    protected void ensureDomEventHandlers() {
        addClickHandler( new ClickHandler() {
            @Override
            public void onClick( ClickEvent event ) {
                // Checkboxes always toggle their value, no need to compare
                // with old value. Radio buttons are not so lucky, see
                // overrides in RadioButton
                ValueChangeEvent.fire( InputCheckbox.this, getValue() );
            }
        } );
    }

    @Override
    public LeafValueEditor<Boolean> asEditor() {
        if ( editor == null ) {
            editor = TakesValueEditor.of( this );
        }
        return editor;
    }
}
