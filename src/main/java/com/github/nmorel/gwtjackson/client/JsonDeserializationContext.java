package com.github.nmorel.gwtjackson.client;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractQueue;
import java.util.AbstractSequentialList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.github.nmorel.gwtjackson.client.deser.BooleanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.CharacterJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.DateJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.EnumJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.NumberJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.StringJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.UUIDJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.ArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.PrimitiveBooleanArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.PrimitiveByteArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.PrimitiveCharacterArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.PrimitiveDoubleArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.PrimitiveFloatArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.PrimitiveIntegerArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.PrimitiveLongArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.array.PrimitiveShortArrayJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.AbstractCollectionJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.AbstractListJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.AbstractQueueJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.AbstractSequentialListJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.AbstractSetJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.ArrayListJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.CollectionJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.EnumSetJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.HashSetJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.IterableJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.LinkedHashSetJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.LinkedListJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.ListJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.PriorityQueueJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.QueueJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.SetJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.SortedSetJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.StackJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.TreeSetJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.collection.VectorJsonDeserializer;
import com.github.nmorel.gwtjackson.client.exception.JsonDeserializationException;
import com.github.nmorel.gwtjackson.client.ser.IterableJsonSerializer;
import com.github.nmorel.gwtjackson.client.stream.JsonReader;
import com.google.gwt.logging.client.LogConfiguration;

/**
 * Context for the deserialization process.
 *
 * @author Nicolas Morel
 */
public class JsonDeserializationContext extends JsonMappingContext {

    private static final Logger logger = Logger.getLogger( "JsonDeserialization" );

    private final JsonReader reader;

    private Map<IdKey, Object> idToObject;

    public JsonDeserializationContext( JsonReader reader ) {
        this.reader = reader;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    public JsonDeserializer<boolean[]> getPrimitiveBooleanArrayJsonDeserializer() {
        return PrimitiveBooleanArrayJsonDeserializer.getInstance();
    }

    public JsonDeserializer<byte[]> getPrimitiveByteArrayJsonDeserializer() {
        return PrimitiveByteArrayJsonDeserializer.getInstance();
    }

    public JsonDeserializer<char[]> getPrimitiveCharacterArrayJsonDeserializer() {
        return PrimitiveCharacterArrayJsonDeserializer.getInstance();
    }

    public JsonDeserializer<double[]> getPrimitiveDoubleArrayJsonDeserializer() {
        return PrimitiveDoubleArrayJsonDeserializer.getInstance();
    }

    public JsonDeserializer<float[]> getPrimitiveFloatArrayJsonDeserializer() {
        return PrimitiveFloatArrayJsonDeserializer.getInstance();
    }

    public JsonDeserializer<int[]> getPrimitiveIntegerArrayJsonDeserializer() {
        return PrimitiveIntegerArrayJsonDeserializer.getInstance();
    }

    public JsonDeserializer<long[]> getPrimitiveLongArrayJsonDeserializer() {
        return PrimitiveLongArrayJsonDeserializer.getInstance();
    }

    public JsonDeserializer<short[]> getPrimitiveShortArrayJsonDeserializer() {
        return PrimitiveShortArrayJsonDeserializer.getInstance();
    }

    public JsonDeserializer<BigDecimal> getBigDecimalJsonDeserializer() {
        return NumberJsonDeserializer.getBigDecimalInstance();
    }

    public JsonDeserializer<BigInteger> getBigIntegerJsonDeserializer() {
        return NumberJsonDeserializer.getBigIntegerInstance();
    }

    public JsonDeserializer<Boolean> getBooleanJsonDeserializer() {
        return BooleanJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Byte> getByteJsonDeserializer() {
        return NumberJsonDeserializer.getByteInstance();
    }

    public JsonDeserializer<Character> getCharacterJsonDeserializer() {
        return CharacterJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Date> getDateJsonDeserializer() {
        return DateJsonDeserializer.getDateInstance();
    }

    public JsonDeserializer<Double> getDoubleJsonDeserializer() {
        return NumberJsonDeserializer.getDoubleInstance();
    }

    public JsonDeserializer<Float> getFloatJsonDeserializer() {
        return NumberJsonDeserializer.getFloatInstance();
    }

    public JsonDeserializer<Integer> getIntegerJsonDeserializer() {
        return NumberJsonDeserializer.getIntegerInstance();
    }

    public JsonDeserializer<Long> getLongJsonDeserializer() {
        return NumberJsonDeserializer.getLongInstance();
    }

    public JsonDeserializer<Short> getShortJsonDeserializer() {
        return NumberJsonDeserializer.getShortInstance();
    }

    public JsonDeserializer<java.sql.Date> getSqlDateJsonDeserializer() {
        return DateJsonDeserializer.getSqlDateInstance();
    }

    public JsonDeserializer<Time> getSqlTimeJsonDeserializer() {
        return DateJsonDeserializer.getSqlTimeInstance();
    }

    public JsonDeserializer<Timestamp> getSqlTimestampJsonDeserializer() {
        return DateJsonDeserializer.getSqlTimestampInstance();
    }

    public JsonDeserializer<String> getStringJsonDeserializer() {
        return StringJsonDeserializer.getInstance();
    }

    public JsonDeserializer<UUID> getUUIDJsonDeserializer() {
        return UUIDJsonDeserializer.getInstance();
    }

    public <T> JsonDeserializer<T[]> newArrayJsonDeserializer( JsonDeserializer<T> deserializer,
                                                               ArrayJsonDeserializer.ArrayCreator<T> arrayCreator ) {
        return ArrayJsonDeserializer.newInstance( deserializer, arrayCreator );
    }

    public <T> JsonDeserializer<Iterable<T>> newIterableJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return IterableJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<Collection<T>> newCollectionJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return CollectionJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<AbstractCollection<T>> newAbstractCollectionJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return AbstractCollectionJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<List<T>> newListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return ListJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<AbstractList<T>> newAbstractListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return AbstractListJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<ArrayList<T>> newArrayListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return ArrayListJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<AbstractSequentialList<T>> newAbstractSequentialListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return AbstractSequentialListJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<LinkedList<T>> newLinkedListJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return LinkedListJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<Vector<T>> newVectorJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return VectorJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<Stack<T>> newStackJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return StackJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<Set<T>> newSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return SetJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<AbstractSet<T>> newAbstractSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return AbstractSetJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<HashSet<T>> newHashSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return HashSetJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<LinkedHashSet<T>> newLinkedHashSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return LinkedHashSetJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<SortedSet<T>> newSortedSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return SortedSetJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<TreeSet<T>> newTreeSetJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return TreeSetJsonDeserializer.newInstance( deserializer );
    }

    public <T extends Enum<T>> JsonDeserializer<EnumSet<T>> newEnumSetJsonDeserializer( Class<T> enumClass,
                                                                                        JsonDeserializer<T> deserializer ) {
        return EnumSetJsonDeserializer.newInstance( enumClass, deserializer );
    }

    public <T> JsonDeserializer<Queue<T>> newQueueJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return QueueJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<AbstractQueue<T>> newAbstractQueueJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return AbstractQueueJsonDeserializer.newInstance( deserializer );
    }

    public <T> JsonDeserializer<PriorityQueue<T>> newPriorityQueueJsonDeserializer( JsonDeserializer<T> deserializer ) {
        return PriorityQueueJsonDeserializer.newInstance( deserializer );
    }

    public <T extends Enum<T>> JsonDeserializer<T> newEnumJsonDeserializer( Class<T> enumClass ) {
        return EnumJsonDeserializer.newInstance( enumClass );
    }

    public <T> JsonSerializer<Iterable<T>> newIterableJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    /**
     * Trace an error with current reader state and returns a corresponding exception.
     *
     * @param message error message
     *
     * @return a {@link JsonDeserializationException} with the given message
     */
    public JsonDeserializationException traceError( String message ) {
        getLogger().log( Level.SEVERE, message );
        traceReaderInfo();
        return new JsonDeserializationException( message );
    }

    /**
     * Trace an error with current reader state and returns a corresponding exception.
     *
     * @param cause cause of the error
     *
     * @return a {@link JsonDeserializationException} with the given cause
     */
    public JsonDeserializationException traceError( Exception cause ) {
        getLogger().log( Level.SEVERE, "Error during deserialization", cause );
        traceReaderInfo();
        return new JsonDeserializationException( cause );
    }

    /**
     * Trace the current reader state
     */
    private void traceReaderInfo() {
        if ( LogConfiguration.loggingIsEnabled( Level.INFO ) ) {
            getLogger().log( Level.INFO, "Error at line " + reader.getLineNumber() + " and column " + reader
                .getColumnNumber() + " of input <" + reader.getInput() + ">" );
        }
    }

    public void addObjectId( IdKey id, Object instance ) {
        if ( null == idToObject ) {
            idToObject = new HashMap<IdKey, Object>();
        }
        idToObject.put( id, instance );
    }

    public Object getObjectWithId( IdKey id ) {
        if ( null != idToObject ) {
            return idToObject.get( id );
        }
        return null;
    }
}
