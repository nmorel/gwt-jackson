package com.github.nmorel.gwtjackson.rebind;

import com.github.nmorel.gwtjackson.rebind.PropertyInfo.AdditionalMethod;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.google.gwt.thirdparty.guava.common.base.Preconditions;

/**
 * @author Nicolas Morel
 */
public abstract class FieldAccessor {

    public static class Accessor {

        private final String accessor;

        private final Optional<AdditionalMethod> additionalMethod;

        public Accessor( String accessor ) {
            Preconditions.checkNotNull( accessor );
            this.accessor = accessor;
            this.additionalMethod = Optional.absent();
        }

        public Accessor( String accessor, AdditionalMethod additionalMethod ) {
            Preconditions.checkNotNull( accessor );
            Preconditions.checkNotNull( additionalMethod );

            this.accessor = accessor;
            this.additionalMethod = Optional.of( additionalMethod );
        }

        public String getAccessor() {
            return accessor;
        }

        public Optional<AdditionalMethod> getAdditionalMethod() {
            return additionalMethod;
        }
    }

    protected final String propertyName;

    protected final Optional<JField> field;

    protected final Optional<JMethod> method;

    protected FieldAccessor( String propertyName, JField field, JMethod method ) {
        Preconditions.checkNotNull( propertyName );
        Preconditions.checkArgument( null != field || null != method, "At least one of the field or method must be given" );

        this.propertyName = propertyName;
        this.field = Optional.fromNullable( field );
        this.method = Optional.fromNullable( method );
    }

    protected abstract Accessor getAccessor( String beanName, boolean samePackage );
}
