package com.github.nmorel.gwtjackson.benchmark.client.ui;

import java.math.BigDecimal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.Widget;
import org.gwtbootstrap3.client.ui.Progress;
import org.gwtbootstrap3.client.ui.ProgressBar;

/**
 * @author Nicolas Morel
 */
public class ResultLineWidget extends Composite {

    interface ResultWidgetUiBinder extends UiBinder<Widget, ResultLineWidget> {}

    private static ResultWidgetUiBinder ourUiBinder = GWT.create( ResultWidgetUiBinder.class );

    @UiField
    Label label;

    @UiField
    Progress progress;

    @UiField
    ProgressBar progressBar;

    @UiField
    NumberLabel<BigDecimal> resultLabel;

    @UiConstructor
    public ResultLineWidget( String text ) {
        initWidget( ourUiBinder.createAndBindUi( this ) );
        label.setText( text );
    }

    public void setPercent( int percent ) {
        progressBar.setPercent( percent );
        progressBar.setText( percent + "%" );
    }

    public void setResult( BigDecimal result ) {
        progress.setVisible( null == result );
        resultLabel.setVisible( null != result );
        setPercent( 0 );
        resultLabel.setValue( result );
    }
}
