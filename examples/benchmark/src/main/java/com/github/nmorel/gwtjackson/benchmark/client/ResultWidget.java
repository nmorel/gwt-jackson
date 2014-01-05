package com.github.nmorel.gwtjackson.benchmark.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.PanelHeader;

/**
 * @author Nicolas Morel
 */
public class ResultWidget extends Composite {

    interface MechanismUiBinder extends UiBinder<Widget, ResultWidget> {}

    private static MechanismUiBinder ourUiBinder = GWT.create( MechanismUiBinder.class );

    @UiField
    PanelHeader header;

    @UiField
    ResultLineWidget resultSer;

    @UiField
    ResultLineWidget resultDeser;

    public ResultWidget( String title ) {
        initWidget( ourUiBinder.createAndBindUi( this ) );
        header.setText( title );
    }

    public ResultLineWidget getResultSer() {
        return resultSer;
    }

    public ResultLineWidget getResultDeser() {
        return resultDeser;
    }
}
