package com.github.nmorel.gwtjackson.rebind;

import com.github.nmorel.gwtjackson.rebind.PropertyInfo.AdditionalMethod;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author Nicolas Morel
 */
public class FieldWriteAccessor extends FieldAccessor {

    protected FieldWriteAccessor( String propertyName, JField field, JMethod method ) {
        super( propertyName, field, method );
    }

    @Override
    protected Accessor getAccessor( String beanName, boolean samePackage ) {
        // We first test if we can use the setter
        if ( method.isPresent() && ((samePackage && !method.get().isPrivate()) || (!samePackage && method.get().isPublic())) ) {
            return new Accessor( beanName + "." + method.get().getName() + "(%s)" );
        }

        // Then the field
        if ( field.isPresent() && ((samePackage && !field.get().isPrivate()) || (samePackage && field.get().isPublic())) ) {
            return new Accessor( beanName + "." + field.get().getName() + " = %s" );
        }

        // field/setter has not been detected or is private or is in a different package. We use JSNI to access private setter/field.
        final JType fieldType;
        final JClassType enclosingType;
        if ( method.isPresent() ) {
            fieldType = method.get().getParameterTypes()[0];
            enclosingType = method.get().getEnclosingType();
        } else {
            fieldType = field.get().getType();
            enclosingType = field.get().getEnclosingType();
        }

        final String methodName = "set" + propertyName.substring( 0, 1 ).toUpperCase() + propertyName.substring( 1 );
        String accessor = methodName + "(" + beanName + ", %s)";
        AdditionalMethod additionalMethod = new AdditionalMethod() {
            @Override
            public void write( SourceWriter source ) {
                source.println( "private native void %s(%s bean, %s value) /*-{", methodName, enclosingType
                    .getParameterizedQualifiedSourceName(), fieldType.getParameterizedQualifiedSourceName() );
                source.indent();
                if ( method.isPresent() ) {
                    source.println( "bean.@%s::%s(%s)(value);", enclosingType.getQualifiedSourceName(), method.get().getName(), fieldType
                        .getJNISignature() );
                } else {
                    source.println( "bean.@%s::%s = value;", enclosingType.getQualifiedSourceName(), field.get().getName() );
                }
                source.outdent();
                source.println( "}-*/;" );
            }
        };

        return new Accessor( accessor, additionalMethod );
    }
}
