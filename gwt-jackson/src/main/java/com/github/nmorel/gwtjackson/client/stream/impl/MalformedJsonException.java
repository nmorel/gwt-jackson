//@formatter:off
/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.nmorel.gwtjackson.client.stream.impl;

import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;

/**
 * Thrown when a reader encounters malformed JSON. Some syntax errors can be
 * ignored by calling {@link com.github.nmorel.gwtjackson.client.stream.JsonReader#setLenient(boolean)}.
 *
 * @author nicolasmorel
 * @version $Id: $
 */
public final class MalformedJsonException extends JsonDeserializationException
{
  private static final long serialVersionUID = 1L;

  private MalformedJsonException(){}

  /**
   * <p>Constructor for MalformedJsonException.</p>
   *
   * @param msg a {@link java.lang.String} object.
   */
  public MalformedJsonException(String msg) {
    super(msg);
  }

}
//@formatter:on
