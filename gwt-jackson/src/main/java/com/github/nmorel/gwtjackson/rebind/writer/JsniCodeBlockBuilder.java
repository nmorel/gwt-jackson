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

import com.squareup.javapoet.CodeBlock;

/**
 * Helper class to build {@link CodeBlock} containing JSNI code.
 *
 * @author Nicolas Morel
 */
public final class JsniCodeBlockBuilder {

    public static JsniCodeBlockBuilder builder() {
        return new JsniCodeBlockBuilder();
    }

    private final CodeBlock.Builder builder;

    private JsniCodeBlockBuilder() {
        builder = CodeBlock.builder().add( " /*-{\n" ).indent();
    }

    public JsniCodeBlockBuilder add( String format, Object... args ) {
        builder.add( format, args );
        return this;
    }

    public JsniCodeBlockBuilder addStatement( String format, Object... args ) {
        builder.addStatement( format, args );
        return this;
    }

    public CodeBlock build() {
        return builder.unindent().add( "}-*/" ).build();
    }

}
