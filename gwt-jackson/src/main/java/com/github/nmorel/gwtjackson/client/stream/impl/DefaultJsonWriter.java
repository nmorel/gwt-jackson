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

import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.nmorel.gwtjackson.client.exception.JsonSerializationException;
import com.google.gwt.core.client.JsArrayInteger;

/**
 * Writes a JSON (<a href="http://www.ietf.org/rfc/rfc4627.txt">RFC 4627</a>)
 * encoded value to a stream, one token at a time. The stream includes both
 * literal values (strings, numbers, booleans and nulls) as well as the begin
 * and end delimiters of objects and arrays.
 *
 * <h3>Encoding JSON</h3>
 * To encode your data as JSON, create a new {@code JsonWriter}. Each JSON
 * document must contain one top-level array or object. Call methods on the
 * writer as you walk the structure's contents, nesting arrays and objects as
 * necessary:
 * <ul>
 *   <li>To write <strong>arrays</strong>, first call {@link #beginArray()}.
 *       Write each of the array's elements with the appropriate {@link #value}
 *       methods or by nesting other arrays and objects. Finally close the array
 *       using {@link #endArray()}.
 *   <li>To write <strong>objects</strong>, first call {@link #beginObject()}.
 *       Write each of the object's properties by alternating calls to
 *       {@link #name} with the property's value. Write property values with the
 *       appropriate {@link #value} method or by nesting other objects or arrays.
 *       Finally close the object using {@link #endObject()}.
 * </ul>
 *
 * <h3>Example</h3>
 * Suppose we'd like to encode a stream of messages such as the following: <pre> {@code
 * [
 *   {
 *     "id": 912345678901,
 *     "text": "How do I stream JSON in Java?",
 *     "geo": null,
 *     "user": {
 *       "name": "json_newb",
 *       "followers_count": 41
 *      }
 *   },
 *   {
 *     "id": 912345678902,
 *     "text": "@json_newb just use JsonWriter!",
 *     "geo": [50.454722, -104.606667],
 *     "user": {
 *       "name": "jesse",
 *       "followers_count": 2
 *     }
 *   }
 * ]}</pre>
 * This code encodes the above structure: <pre>   {@code
 *   public void writeJsonStream(OutputStream out, List<Message> messages) {
 *     JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
 *     writer.setIndentSpaces(4);
 *     writeMessagesArray(writer, messages);
 *     writer.close();
 *   }
 *
 *   public void writeMessagesArray(JsonWriter writer, List<Message> messages) {
 *     writer.beginArray();
 *     for (Message message : messages) {
 *       writeMessage(writer, message);
 *     }
 *     writer.endArray();
 *   }
 *
 *   public void writeMessage(JsonWriter writer, Message message) {
 *     writer.beginObject();
 *     writer.name("id").value(message.getId());
 *     writer.name("text").value(message.getText());
 *     if (message.getGeo() != null) {
 *       writer.name("geo");
 *       writeDoublesArray(writer, message.getGeo());
 *     } else {
 *       writer.name("geo").nullValue();
 *     }
 *     writer.name("user");
 *     writeUser(writer, message.getUser());
 *     writer.endObject();
 *   }
 *
 *   public void writeUser(JsonWriter writer, User user) {
 *     writer.beginObject();
 *     writer.name("name").value(user.getName());
 *     writer.name("followers_count").value(user.getFollowersCount());
 *     writer.endObject();
 *   }
 *
 *   public void writeDoublesArray(JsonWriter writer, List<Double> doubles) {
 *     writer.beginArray();
 *     for (Double value : doubles) {
 *       writer.value(value);
 *     }
 *     writer.endArray();
 *   }}</pre>
 *
 * <p>Each {@code JsonWriter} may be used to write a single JSON stream.
 * Instances of this class are not thread safe. Calls that would result in a
 * malformed JSON string will fail with an {@link IllegalStateException}.
 *
 * @author Jesse Wilson
 * @since 1.6
 */
public class DefaultJsonWriter implements com.github.nmorel.gwtjackson.client.stream.JsonWriter {

  private static final Logger logger = Logger.getLogger( "JsonWriter" );

  /*
   * From RFC 4627, "All Unicode characters may be placed within the
   * quotation marks except for the characters that must be escaped:
   * quotation mark, reverse solidus, and the control characters
   * (U+0000 through U+001F)."
   *
   * We also escape '\u2028' and '\u2029', which JavaScript interprets as
   * newline characters. This prevents eval() from failing with a syntax
   * error. http://code.google.com/p/google-gson/issues/detail?id=341
   */
  private static final String[] REPLACEMENT_CHARS;
  static {
    REPLACEMENT_CHARS = new String[128];
    for (int i = 0; i <= 0x1f; i++) {
      StringBuilder sb = new StringBuilder( 6 );
      sb.append( "\\u" );
      String hexa = Integer.toHexString( i );
      for ( int j = hexa.length(); j < 4; j++ ) {
        sb.append( '0' );
      }
      sb.append( hexa );
      REPLACEMENT_CHARS[i] = sb.toString();
    }
    REPLACEMENT_CHARS['"'] = "\\\"";
    REPLACEMENT_CHARS['\\'] = "\\\\";
    REPLACEMENT_CHARS['\t'] = "\\t";
    REPLACEMENT_CHARS['\b'] = "\\b";
    REPLACEMENT_CHARS['\n'] = "\\n";
    REPLACEMENT_CHARS['\r'] = "\\r";
    REPLACEMENT_CHARS['\f'] = "\\f";
  }

  /** The output data, containing at most one top-level array or object. */
  private final StringBuilder out;

  private JsArrayInteger stack = JsArrayInteger.createArray().cast();
  private int stackSize = 0;
  {
    push( JsonScope.EMPTY_DOCUMENT);
  }

  /**
   * A string containing a full set of spaces for a single level of
   * indentation, or null for no pretty printing.
   */
  private String indent;

  /**
   * The name/value separator; either ":" or ": ".
   */
  private String separator = ":";

  private boolean lenient;

  private String deferredUnescapeName;

  private String deferredName;

  private boolean serializeNulls = true;

  /**
   * Creates a new instance that writes a JSON-encoded stream to {@code out}.
   */
  public DefaultJsonWriter( StringBuilder out ) {
    if (out == null) {
      throw new NullPointerException("out == null");
    }
    this.out = out;
  }

  @Override
  public final void setIndent( String indent ) {
    if (indent.length() == 0) {
      this.indent = null;
      this.separator = ":";
    } else {
      this.indent = indent;
      this.separator = ": ";
    }
  }

  @Override
  public final void setLenient( boolean lenient ) {
    this.lenient = lenient;
  }

  /**
   * Returns true if this writer has relaxed syntax rules.
   */
  public boolean isLenient() {
    return lenient;
  }

  @Override
  public final void setSerializeNulls( boolean serializeNulls ) {
    this.serializeNulls = serializeNulls;
  }

  /**
   * Returns true if object members are serialized when their value is null.
   * This has no impact on array elements. The default is true.
   */
  @Override
  public final boolean getSerializeNulls() {
    return serializeNulls;
  }

  @Override
  public DefaultJsonWriter beginArray() {
    writeDeferredName();
    return open( JsonScope.EMPTY_ARRAY, "[");
  }

  @Override
  public DefaultJsonWriter endArray() {
    return close( JsonScope.EMPTY_ARRAY, JsonScope.NONEMPTY_ARRAY, "]");
  }

  @Override
  public DefaultJsonWriter beginObject() {
    writeDeferredName();
    return open( JsonScope.EMPTY_OBJECT, "{");
  }

  @Override
  public DefaultJsonWriter endObject() {
    return close( JsonScope.EMPTY_OBJECT, JsonScope.NONEMPTY_OBJECT, "}");
  }

  /**
   * Enters a new scope by appending any necessary whitespace and the given
   * bracket.
   */
  private DefaultJsonWriter open(int empty, String openBracket) {
    beforeValue(true);
    push(empty);
    out.append(openBracket);
    return this;
  }

  /**
   * Closes the current scope by appending any necessary whitespace and the
   * given bracket.
   */
  private DefaultJsonWriter close(int empty, int nonempty, String closeBracket)
      {
    int context = peek();
    if (context != nonempty && context != empty) {
      throw new IllegalStateException("Nesting problem.");
    }
    if (deferredUnescapeName != null || deferredName != null) {
      throw new IllegalStateException("Dangling name: " + (deferredUnescapeName == null ? deferredName : deferredUnescapeName));
    }

    stackSize--;
    if (context == nonempty) {
      newline();
    }
    out.append(closeBracket);
    return this;
  }

  private void push(int newTop) {
    stack.set(stackSize++, newTop);
  }

  /**
   * Returns the value on the top of the stack.
   */
  private int peek() {
    if (stackSize == 0) {
      throw new IllegalStateException("JsonWriter is closed.");
    }
    return stack.get(stackSize - 1);
  }

  /**
   * Replace the value on the top of the stack with the given value.
   */
  private void replaceTop(int topOfStack) {
    stack.set(stackSize - 1, topOfStack);
  }

  @Override
  public DefaultJsonWriter name( String name ) {
    checkName(name);
    deferredName = name;
    return this;
  }

  @Override
  public DefaultJsonWriter unescapeName( String name ) {
    checkName(name);
    deferredUnescapeName = name;
    return this;
  }

  private void checkName(String name) {
    if (name == null) {
      throw new NullPointerException("name == null");
    }
    if (deferredUnescapeName != null || deferredName != null) {
      throw new IllegalStateException();
    }
    if (stackSize == 0) {
      throw new IllegalStateException("JsonWriter is closed.");
    }
  }

  private void writeDeferredName() {
    if (deferredUnescapeName != null) {
      beforeName();
      out.append('\"').append(deferredUnescapeName).append('\"');
      deferredUnescapeName = null;
    } else if (deferredName != null) {
      beforeName();
      string(deferredName);
      deferredName = null;
    }
  }

  @Override
  public DefaultJsonWriter value( String value ) {
    if (value == null) {
      return nullValue();
    }
    writeDeferredName();
    beforeValue(false);
    string(value);
    return this;
  }

  @Override
  public DefaultJsonWriter unescapeValue( String value ) {
    if (value == null) {
      return nullValue();
    }
    writeDeferredName();
    beforeValue(false);
    out.append('\"').append(value).append('\"');
    return this;
  }

  @Override
  public DefaultJsonWriter nullValue() {
    if (deferredUnescapeName != null || deferredName != null) {
      if (serializeNulls) {
        writeDeferredName();
      } else {
        deferredUnescapeName = null;
        deferredName = null;
        return this; // skip the name and the value
      }
    }
    beforeValue(false);
    out.append("null");
    return this;
  }

  @Override
  public DefaultJsonWriter cancelName() {
    if (deferredUnescapeName != null) {
      deferredUnescapeName = null;
    } else if (deferredName != null) {
      deferredName = null;
    }
    return this;
  }

  @Override
  public DefaultJsonWriter value( boolean value ) {
    writeDeferredName();
    beforeValue(false);
    out.append(value ? "true" : "false");
    return this;
  }

  @Override
  public DefaultJsonWriter value( double value ) {
    if (Double.isNaN(value) || Double.isInfinite(value)) {
      throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
    }
    writeDeferredName();
    beforeValue(false);
    out.append(Double.toString(value));
    return this;
  }

  @Override
  public DefaultJsonWriter value( long value ) {
    writeDeferredName();
    beforeValue(false);
    out.append(Long.toString(value));
    return this;
  }

  @Override
  public DefaultJsonWriter value( Number value ) {
    if (value == null) {
      return nullValue();
    }

    writeDeferredName();
    String string = value.toString();
    if (!lenient
        && (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN"))) {
      throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
    }
    beforeValue(false);
    out.append(string);
    return this;
  }

  @Override
  public DefaultJsonWriter rawValue( Object value ) {
    if (value == null) {
      return nullValue();
    }
    writeDeferredName();
    beforeValue(false);
    out.append(value.toString());
    return this;
  }

  @Override
  public void flush() {
    if (stackSize == 0) {
      throw new IllegalStateException("JsonWriter is closed.");
    }
  }

  @Override
  public void close() {
    int size = stackSize;
    if (size > 1 || size == 1 && stack.get(size - 1) != JsonScope.NONEMPTY_DOCUMENT) {
      logger.log(Level.SEVERE, "Incomplete document");
      throw new JsonSerializationException("Incomplete document");
    }
    stackSize = 0;
  }

  private void string(String value) {
    out.append("\"");
    encodeString( value, out );
    out.append("\"");
  }

  private static void encodeString(final String value, final StringBuilder out) {
    String[] replacements = REPLACEMENT_CHARS;
    int last = 0;
    int length = value.length();
    for (int i = 0; i < length; i++) {
      char c = value.charAt(i);
      String replacement;
      if (c < 128) {
        replacement = replacements[c];
        if (replacement == null) {
          continue;
        }
      } else if (c == '\u2028') {
        replacement = "\\u2028";
      } else if (c == '\u2029') {
        replacement = "\\u2029";
      } else {
        continue;
      }
      if (last < i) {
        out.append(value, last, i);
      }
      out.append(replacement);
      last = i + 1;
    }
    if (last < length) {
      out.append(value, last, length);
    }
  }

  public static String encodeString(final String value) {
    StringBuilder out = new StringBuilder();
    encodeString( value, out );
    return out.toString();
  }

  private void newline() {
    if (indent == null) {
      return;
    }

    out.append("\n");
    for (int i = 1, size = stackSize; i < size; i++) {
      out.append(indent);
    }
  }

  /**
   * Inserts any necessary separators and whitespace before a name. Also
   * adjusts the stack to expect the name's value.
   */
  private void beforeName() {
    int context = peek();
    if (context == JsonScope.NONEMPTY_OBJECT) { // first in object
      out.append(',');
    } else if (context != JsonScope.EMPTY_OBJECT) { // not in an object!
      throw new IllegalStateException("Nesting problem.");
    }
    newline();
    replaceTop( JsonScope.DANGLING_NAME);
  }

  /**
   * Inserts any necessary separators and whitespace before a literal value,
   * inline array, or inline object. Also adjusts the stack to expect either a
   * closing bracket or another element.
   *
   * @param root true if the value is a new array or object, the two values
   *     permitted as top-level elements.
   */
  @SuppressWarnings("fallthrough")
  private void beforeValue(boolean root) {
    switch (peek()) {
    case JsonScope.NONEMPTY_DOCUMENT:
      if (!lenient) {
        throw new IllegalStateException(
            "JSON must have only one top-level value.");
      }
      // fall-through
    case JsonScope.EMPTY_DOCUMENT: // first in document
      if (!lenient && !root) {
        throw new IllegalStateException(
            "JSON must start with an array or an object.");
      }
      replaceTop( JsonScope.NONEMPTY_DOCUMENT);
      break;

    case JsonScope.EMPTY_ARRAY: // first in array
      replaceTop( JsonScope.NONEMPTY_ARRAY);
      newline();
      break;

    case JsonScope.NONEMPTY_ARRAY: // another in array
      out.append(',');
      newline();
      break;

    case JsonScope.DANGLING_NAME: // value for name
      out.append(separator);
      replaceTop( JsonScope.NONEMPTY_OBJECT);
      break;

    default:
      throw new IllegalStateException("Nesting problem.");
    }
  }

  @Override
  public String getOutput() {
    return out.toString();
  }
}
//@formatter:on