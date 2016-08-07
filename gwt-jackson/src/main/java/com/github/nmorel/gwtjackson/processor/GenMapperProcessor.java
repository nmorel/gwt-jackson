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

package com.github.nmorel.gwtjackson.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by nicolasmorel on 29/05/2016.
 */
public class GenMapperProcessor extends AbstractProcessor {

    private Filer filer;

    private Messager messager;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add( GenMapper.class.getCanonicalName() );
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init( ProcessingEnvironment processingEnv ) {
        super.init( processingEnv );

        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process( Set<? extends TypeElement> annotations, RoundEnvironment roundEnv ) {
        for ( Element element : roundEnv.getElementsAnnotatedWith( GenMapper.class ) ) {
            System.out.println("azeazeaze" + element.toString());
        }
        return true;
    }

    /**
     * Prints a note message
     *
     * @param e The element which has caused the error. Can be null
     * @param msg The error message
     * @param args if the error message contains %s, %d etc. placeholders this arguments will be used
     * to replace them
     */
    public void note( Element e, String msg, Object... args ) {
        messager.printMessage( Diagnostic.Kind.NOTE, String.format( msg, args ), e );
    }

    /**
     * Prints a warning message
     *
     * @param e The element which has caused the error. Can be null
     * @param msg The error message
     * @param args if the error message contains %s, %d etc. placeholders this arguments will be used
     * to replace them
     */
    public void warn( Element e, String msg, Object... args ) {
        messager.printMessage( Diagnostic.Kind.WARNING, String.format( msg, args ), e );
    }

    /**
     * Prints an error message
     *
     * @param e The element which has caused the error. Can be null
     * @param msg The error message
     * @param args if the error message contains %s, %d etc. placeholders this arguments will be used
     * to replace them
     */
    public void error( Element e, String msg, Object... args ) {
        messager.printMessage( Diagnostic.Kind.ERROR, String.format( msg, args ), e );
    }
}
