/*
 * Copyright 2015 Nicolas Morel
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

package com.github.nmorel.gwtjackson.rebind.writer;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.JTypeParameter;
import com.google.gwt.core.ext.typeinfo.JWildcardType;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import com.squareup.javapoet.WildcardTypeName;

/**
 * Helper class to convert {@link JType} into {@link TypeName}
 *
 * @author Nicolas Morel
 */
public final class JTypeName {

    private JTypeName() {}

    /**
     * Default wildcard : ?
     */
    public static final WildcardTypeName DEFAULT_WILDCARD = WildcardTypeName.subtypeOf( Object.class );

    private static final ClassName BOOLEAN_NAME = ClassName.get( Boolean.class );

    private static final ClassName BYTE_NAME = ClassName.get( Byte.class );

    private static final ClassName SHORT_NAME = ClassName.get( Short.class );

    private static final ClassName INTEGER_NAME = ClassName.get( Integer.class );

    private static final ClassName LONG_NAME = ClassName.get( Long.class );

    private static final ClassName CHARACTER_NAME = ClassName.get( Character.class );

    private static final ClassName FLOAT_NAME = ClassName.get( Float.class );

    private static final ClassName DOUBLE_NAME = ClassName.get( Double.class );

    private static final ClassName VOID_NAME = ClassName.get( Void.class );

    /**
     * @param type the type
     *
     * @return the {@link TypeName}
     */
    public static TypeName typeName( JType type ) {
        return typeName( false, type );
    }

    /**
     * @param boxed true if the primitive should be boxed. Useful when use in a parameterized type.
     * @param type the type
     *
     * @return the {@link TypeName}
     */
    public static TypeName typeName( boolean boxed, JType type ) {
        if ( null != type.isPrimitive() ) {
            return primitiveName( type.isPrimitive(), boxed );
        } else if ( null != type.isParameterized() ) {
            return parameterizedName( type.isParameterized() );
        } else if ( null != type.isGenericType() ) {
            return genericName( type.isGenericType() );
        } else if ( null != type.isArray() ) {
            return arrayName( type.isArray() );
        } else if ( null != type.isTypeParameter() ) {
            return typeVariableName( type.isTypeParameter() );
        } else if ( null != type.isWildcard() ) {
            return wildcardName( type.isWildcard() );
        } else {
            return className( type.isClassOrInterface() );
        }
    }

    /**
     * @param clazz the raw type
     * @param types the parameters
     *
     * @return the {@link ParameterizedTypeName}
     */
    public static ParameterizedTypeName parameterizedName( Class clazz, JType... types ) {
        return ParameterizedTypeName.get( ClassName.get( clazz ), typeName( true, types ) );
    }

    /**
     * @param type type to convert
     *
     * @return the raw {@link TypeName} without parameter
     */
    public static TypeName rawName( JType type ) {
        return rawName( false, type );
    }

    /**
     * @param boxed true if the primitive should be boxed. Useful when use in a parameterized type.
     * @param type type to convert
     *
     * @return the raw {@link TypeName} without parameter
     */
    public static TypeName rawName( boolean boxed, JType type ) {
        if ( null != type.isPrimitive() ) {
            return primitiveName( type.isPrimitive(), boxed );
        } else if ( null != type.isParameterized() ) {
            return className( type.isParameterized().getRawType() );
        } else if ( null != type.isGenericType() ) {
            return className( type.isGenericType().getRawType() );
        } else if ( null != type.isArray() ) {
            return arrayName( type.isArray() );
        } else if ( null != type.isTypeParameter() ) {
            return typeVariableName( type.isTypeParameter() );
        } else {
            return className( type.isClassOrInterface() );
        }
    }

    /**
     * @param types the types
     *
     * @return the {@link TypeName}s
     */
    public static TypeName[] typeName( JType... types ) {
        return typeName( false, types );
    }

    private static TypeName[] typeName( boolean boxed, JType... types ) {
        TypeName[] result = new TypeName[types.length];
        for ( int i = 0; i < types.length; i++ ) {
            result[i] = typeName( boxed, types[i] );
        }
        return result;
    }

    private static TypeName primitiveName( JPrimitiveType type, boolean boxed ) {
        if ( "boolean".equals( type.getSimpleSourceName() ) ) {
            return boxed ? BOOLEAN_NAME : TypeName.BOOLEAN;
        } else if ( "byte".equals( type.getSimpleSourceName() ) ) {
            return boxed ? BYTE_NAME : TypeName.BYTE;
        } else if ( "short".equals( type.getSimpleSourceName() ) ) {
            return boxed ? SHORT_NAME : TypeName.SHORT;
        } else if ( "int".equals( type.getSimpleSourceName() ) ) {
            return boxed ? INTEGER_NAME : TypeName.INT;
        } else if ( "long".equals( type.getSimpleSourceName() ) ) {
            return boxed ? LONG_NAME : TypeName.LONG;
        } else if ( "char".equals( type.getSimpleSourceName() ) ) {
            return boxed ? CHARACTER_NAME : TypeName.CHAR;
        } else if ( "float".equals( type.getSimpleSourceName() ) ) {
            return boxed ? FLOAT_NAME : TypeName.FLOAT;
        } else if ( "double".equals( type.getSimpleSourceName() ) ) {
            return boxed ? DOUBLE_NAME : TypeName.DOUBLE;
        } else {
            return boxed ? VOID_NAME : TypeName.VOID;
        }
    }

    private static ParameterizedTypeName parameterizedName( JParameterizedType type ) {
        return ParameterizedTypeName.get( className( type ), typeName( true, type.getTypeArgs() ) );
    }

    private static ParameterizedTypeName genericName( JGenericType type ) {
        return ParameterizedTypeName.get( className( type ), typeName( true, type.getTypeParameters() ) );
    }

    private static ArrayTypeName arrayName( JArrayType type ) {
        return ArrayTypeName.of( typeName( type.getComponentType() ) );
    }

    public static TypeVariableName typeVariableName( JTypeParameter type ) {
        return TypeVariableName.get( type.getName(), typeName( type.getBounds() ) );
    }

    private static WildcardTypeName wildcardName( JWildcardType type ) {
        switch ( type.getBoundType() ) {
            case SUPER:
                return WildcardTypeName.supertypeOf( typeName( type.getFirstBound() ) );
            default:
                return WildcardTypeName.subtypeOf( typeName( type.getFirstBound() ) );
        }
    }

    private static ClassName className( JClassType type ) {
        JClassType enclosingType = type.getEnclosingType();

        if ( null == enclosingType ) {
            return ClassName.get( type.getPackage()
                    .getName(), type.getSimpleSourceName() );
        }

        // We look for all enclosing types and add them at the head.
        List<String> types = new ArrayList<String>();
        types.add( type.getSimpleSourceName() );
        while ( null != enclosingType ) {
            types.add( 0, enclosingType.getSimpleSourceName() );
            enclosingType = enclosingType.getEnclosingType();
        }

        // The parent type is the first one. We remove it to keep only the childs.
        String parentType = types.remove( 0 );
        String[] childs = types.toArray( new String[types.size()] );
        return ClassName.get( type.getPackage()
                .getName(), parentType, childs );
    }
}
