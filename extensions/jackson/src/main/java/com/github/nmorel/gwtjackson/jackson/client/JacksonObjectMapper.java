package com.github.nmorel.gwtjackson.jackson.client;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.github.nmorel.gwtjackson.client.stream.impl.DefaultJsonReader;
import com.github.nmorel.gwtjackson.client.stream.impl.StringReader;
import com.github.nmorel.gwtjackson.jackson.client.deser.JacksonJsonParser;

public class JacksonObjectMapper extends ObjectCodec {

    private JsonNodeFactory jsonNodeFactory;

    public JacksonObjectMapper() {
        jsonNodeFactory = JsonNodeFactory.instance;
    }

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    // TODO: Support these methods for Number, JsonNode, Boolean and String
    @Override
    public <T> T readValue(JsonParser p, Class<T> valueType) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public <T> T readValue(JsonParser p, TypeReference<?> valueTypeRef) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public <T> T readValue(JsonParser p, ResolvedType valueType) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser p, Class<T> valueType) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser p, TypeReference<?> valueTypeRef) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser p, ResolvedType valueType) throws IOException {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public void writeValue(JsonGenerator gen, Object value) throws IOException {
        gen.writeObject(value);
    }

    @Override
    public <T extends TreeNode> T readTree(JsonParser p) throws IOException {
        JsonToken token = p.nextToken();
        if (token != JsonToken.START_OBJECT && token != JsonToken.START_ARRAY) {
            throw new JacksonJsonProcessingException("Expected START_OBJECT or START_ARRAY, got " + token.toString());
        }
        int braceCount = 1;

        for (token = p.nextToken(); braceCount > 0; token = p.nextToken()) {
            switch (token) {
                case START_ARRAY:
                case START_OBJECT:
                    braceCount++;
                    break;

                case END_ARRAY:
                case END_OBJECT:
                    braceCount--;
                    break;

                default:
                    break;

            }
        }
        return (T) p.getCurrentValue();
    }

    @Override
    public void writeTree(JsonGenerator gen, TreeNode tree) throws IOException {
        gen.writeTree(tree);
    }

    @Override
    public TreeNode createObjectNode() {
        return jsonNodeFactory.objectNode();
    }

    @Override
    public TreeNode createArrayNode() {
        return jsonNodeFactory.arrayNode();
    }

    @Override
    public JsonParser treeAsTokens(TreeNode n) {
        JsonReader reader = new DefaultJsonReader(new StringReader(n.toString()));
        return new JacksonJsonParser(reader);
    }

    @Override
    public <T> T treeToValue(TreeNode n, Class<T> valueType) throws JsonProcessingException {
        JsonReader reader = new DefaultJsonReader(new StringReader(n.toString()));
        JsonParser p = new JacksonJsonParser(reader);
        try {
            T out = p.readValueAsTree();
            p.close();
            return out;
        } catch (IOException e) {
            throw new JacksonJsonProcessingException(e);
        }
    }

    private static class JacksonJsonProcessingException extends JsonProcessingException {

        public JacksonJsonProcessingException(String msg) {
            super(msg);
        }

        public JacksonJsonProcessingException(String msg, Throwable rootCause) {
            super(msg, rootCause);
        }

        public JacksonJsonProcessingException(Throwable rootCause) {
            super(rootCause);
        }

    }

}
