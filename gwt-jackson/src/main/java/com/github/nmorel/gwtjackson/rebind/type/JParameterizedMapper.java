/*
 * Copyright 2016 Nicolas Morel
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

package com.github.nmorel.gwtjackson.rebind.type;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

public abstract class JParameterizedMapper<T extends JMapperType> {

    private final T key;

    private final T json;

    public JParameterizedMapper( T key, T json ) {
        this.key = key;
        this.json = json;
    }

    public T getKey() {
        return key;
    }

    public T getJson() {
        return json;
    }

    protected abstract Class getMainClass();

    protected abstract Class getKeyClass();

    protected abstract Class getJsonClass();

    public CodeBlock getInstance() {
        return CodeBlock.builder()
                .add( "new $T() {\n", ClassName.get( getMainClass() ) )
                .indent()
                .add( buildCreateMethod( getKeyClass(), key.getInstance() ) )
                .add( "\n" )
                .add( buildCreateMethod( getJsonClass(), json.getInstance() ) )
                .unindent()
                .add( "}" )
                .build();

    }

    private CodeBlock buildCreateMethod( Class clazz, CodeBlock instance ) {
        return CodeBlock.builder()
                .add( "@Override\n" )
                .add( "protected $T create$N() {\n", ClassName.get( clazz ), clazz.getSimpleName() )
                .indent()
                .add( "return " )
                .add( instance )
                .add( ";\n" )
                .unindent()
                .add( "}\n" )
                .build();
    }
}
