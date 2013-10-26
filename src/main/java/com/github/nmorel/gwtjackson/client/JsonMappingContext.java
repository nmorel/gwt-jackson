package com.github.nmorel.gwtjackson.client;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSequentialList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Logger;

import com.github.nmorel.gwtjackson.client.deser.BaseDateJsonDeserializer.DateJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseDateJsonDeserializer.SqlDateJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseDateJsonDeserializer.SqlTimeJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseDateJsonDeserializer.SqlTimestampJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.BigDecimalJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.BigIntegerJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.ByteJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.DoubleJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.FloatJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.IntegerJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.LongJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BaseNumberJsonDeserializer.ShortJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.BooleanJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.CharacterJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.EnumJsonDeserializer;
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
import com.github.nmorel.gwtjackson.client.deser.map.AbstractMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.EnumMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.HashMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.IdentityHashMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.LinkedHashMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.MapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.SortedMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.TreeMapJsonDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BaseDateKeyDeserializer.DateKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BaseDateKeyDeserializer.SqlDateKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BaseDateKeyDeserializer.SqlTimeKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BaseDateKeyDeserializer.SqlTimestampKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.BigDecimalKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.BigIntegerKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.ByteKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.DoubleKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.FloatKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.IntegerKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.LongKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BaseNumberKeyDeserializer.ShortKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.BooleanKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.CharacterKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.EnumKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.KeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.StringKeyDeserializer;
import com.github.nmorel.gwtjackson.client.deser.map.key.UUIDKeyDeserializer;
import com.github.nmorel.gwtjackson.client.ser.BaseDateJsonSerializer.DateJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BaseDateJsonSerializer.SqlDateJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BaseDateJsonSerializer.SqlTimeJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BaseDateJsonSerializer.SqlTimestampJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.BigDecimalJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.BigIntegerJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.ByteJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.DoubleJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.FloatJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.IntegerJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.LongJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BaseNumberJsonSerializer.ShortJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.BooleanJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.CharacterJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.EnumJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.IterableJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.RawValueJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.StringJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.UUIDJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.ArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveBooleanArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveByteArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveCharacterArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveDoubleArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveFloatArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveIntegerArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveLongArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.array.PrimitiveShortArrayJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.map.MapJsonSerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BaseDateKeySerializer.DateKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BaseDateKeySerializer.SqlDateKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BaseDateKeySerializer.SqlTimeKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BaseDateKeySerializer.SqlTimestampKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.BigDecimalKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.BigIntegerKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.ByteKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.DoubleKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.FloatKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.IntegerKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.LongKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BaseNumberKeySerializer.ShortKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.BooleanKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.CharacterKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.EnumKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.KeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.StringKeySerializer;
import com.github.nmorel.gwtjackson.client.ser.map.key.UUIDKeySerializer;

/**
 * Base class for serialization and deserialization context
 *
 * @author Nicolas Morel
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class JsonMappingContext {

    public abstract Logger getLogger();

    // TODO should we move those to a factory ? having this delegation to the static method allows to override by context instance

    /*##############################*/
    /*#####    Deserializers   #####*/
    /*##############################*/

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
        return BigDecimalJsonDeserializer.getInstance();
    }

    public JsonDeserializer<BigInteger> getBigIntegerJsonDeserializer() {
        return BigIntegerJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Boolean> getBooleanJsonDeserializer() {
        return BooleanJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Byte> getByteJsonDeserializer() {
        return ByteJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Character> getCharacterJsonDeserializer() {
        return CharacterJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Date> getDateJsonDeserializer() {
        return DateJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Double> getDoubleJsonDeserializer() {
        return DoubleJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Float> getFloatJsonDeserializer() {
        return FloatJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Integer> getIntegerJsonDeserializer() {
        return IntegerJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Long> getLongJsonDeserializer() {
        return LongJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Short> getShortJsonDeserializer() {
        return ShortJsonDeserializer.getInstance();
    }

    public JsonDeserializer<java.sql.Date> getSqlDateJsonDeserializer() {
        return SqlDateJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Time> getSqlTimeJsonDeserializer() {
        return SqlTimeJsonDeserializer.getInstance();
    }

    public JsonDeserializer<Timestamp> getSqlTimestampJsonDeserializer() {
        return SqlTimestampJsonDeserializer.getInstance();
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

    public <K, V> JsonDeserializer<AbstractMap<K, V>> newAbstractMapJsonDeserializer( KeyDeserializer<K> keyDeserializer,
                                                                                      JsonDeserializer<V> valueDeserializer ) {
        return AbstractMapJsonDeserializer.newInstance( keyDeserializer, valueDeserializer );
    }

    public <E extends Enum<E>, V> JsonDeserializer<EnumMap<E, V>> newEnumMapJsonDeserializer( Class<E> enumClass,
                                                                                              KeyDeserializer<E> keyDeserializer,
                                                                                              JsonDeserializer<V> valueDeserializer ) {
        return EnumMapJsonDeserializer.newInstance( enumClass, keyDeserializer, valueDeserializer );
    }

    public <K, V> JsonDeserializer<HashMap<K, V>> newHashMapJsonDeserializer( KeyDeserializer<K> keyDeserializer,
                                                                              JsonDeserializer<V> valueDeserializer ) {
        return HashMapJsonDeserializer.newInstance( keyDeserializer, valueDeserializer );
    }

    public <K, V> JsonDeserializer<IdentityHashMap<K, V>> newIdentityHashMapJsonDeserializer( KeyDeserializer<K> keyDeserializer,
                                                                                              JsonDeserializer<V> valueDeserializer ) {
        return IdentityHashMapJsonDeserializer.newInstance( keyDeserializer, valueDeserializer );
    }

    public <K, V> JsonDeserializer<LinkedHashMap<K, V>> newLinkedHashMapJsonDeserializer( KeyDeserializer<K> keyDeserializer,
                                                                                          JsonDeserializer<V> valueDeserializer ) {
        return LinkedHashMapJsonDeserializer.newInstance( keyDeserializer, valueDeserializer );
    }

    public <K, V> JsonDeserializer<Map<K, V>> newMapJsonDeserializer( KeyDeserializer<K> keyDeserializer,
                                                                      JsonDeserializer<V> valueDeserializer ) {
        return MapJsonDeserializer.newInstance( keyDeserializer, valueDeserializer );
    }

    public <K, V> JsonDeserializer<SortedMap<K, V>> newSortedMapJsonDeserializer( KeyDeserializer<K> keyDeserializer,
                                                                                  JsonDeserializer<V> valueDeserializer ) {
        return SortedMapJsonDeserializer.newInstance( keyDeserializer, valueDeserializer );
    }

    public <K, V> JsonDeserializer<TreeMap<K, V>> newTreeMapJsonDeserializer( KeyDeserializer<K> keyDeserializer,
                                                                              JsonDeserializer<V> valueDeserializer ) {
        return TreeMapJsonDeserializer.newInstance( keyDeserializer, valueDeserializer );
    }

    /*##############################*/
    /*####   Key deserializers  ####*/
    /*##############################*/

    public KeyDeserializer<Boolean> getBooleanKeyDeserializer() {
        return BooleanKeyDeserializer.getInstance();
    }

    public KeyDeserializer<Character> getCharacterKeyDeserializer() {
        return CharacterKeyDeserializer.getInstance();
    }

    public <T extends Enum<T>> KeyDeserializer<T> newEnumKeyDeserializer( Class<T> enumClass ) {
        return EnumKeyDeserializer.newInstance( enumClass );
    }

    public KeyDeserializer<String> getStringKeyDeserializer() {
        return StringKeyDeserializer.getInstance();
    }

    public KeyDeserializer<UUID> getUUIDKeyDeserializer() {
        return UUIDKeyDeserializer.getInstance();
    }

    public KeyDeserializer<BigDecimal> getBigDecimalKeyDeserializer() {
        return BigDecimalKeyDeserializer.getInstance();
    }

    public KeyDeserializer<BigInteger> getBigIntegerKeyDeserializer() {
        return BigIntegerKeyDeserializer.getInstance();
    }

    public KeyDeserializer<Byte> getByteKeyDeserializer() {
        return ByteKeyDeserializer.getInstance();
    }

    public KeyDeserializer<Double> getDoubleKeyDeserializer() {
        return DoubleKeyDeserializer.getInstance();
    }

    public KeyDeserializer<Float> getFloatKeyDeserializer() {
        return FloatKeyDeserializer.getInstance();
    }

    public KeyDeserializer<Integer> getIntegerKeyDeserializer() {
        return IntegerKeyDeserializer.getInstance();
    }

    public KeyDeserializer<Long> getLongKeyDeserializer() {
        return LongKeyDeserializer.getInstance();
    }

    public KeyDeserializer<Short> getShortKeyDeserializer() {
        return ShortKeyDeserializer.getInstance();
    }

    public KeyDeserializer<Date> getDateKeyDeserializer() {
        return DateKeyDeserializer.getInstance();
    }

    public KeyDeserializer<java.sql.Date> getSqlDateKeyDeserializer() {
        return SqlDateKeyDeserializer.getInstance();
    }

    public KeyDeserializer<Time> getSqlTimeKeyDeserializer() {
        return SqlTimeKeyDeserializer.getInstance();
    }

    public KeyDeserializer<Timestamp> getSqlTimestampKeyDeserializer() {
        return SqlTimestampKeyDeserializer.getInstance();
    }

    /*##############################*/
    /*#######   Serializers  #######*/
    /*##############################*/

    public JsonSerializer<boolean[]> getPrimitiveBooleanArrayJsonSerializer() {
        return PrimitiveBooleanArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<byte[]> getPrimitiveByteArrayJsonSerializer() {
        return PrimitiveByteArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<char[]> getPrimitiveCharacterArrayJsonSerializer() {
        return PrimitiveCharacterArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<double[]> getPrimitiveDoubleArrayJsonSerializer() {
        return PrimitiveDoubleArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<float[]> getPrimitiveFloatArrayJsonSerializer() {
        return PrimitiveFloatArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<int[]> getPrimitiveIntegerArrayJsonSerializer() {
        return PrimitiveIntegerArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<long[]> getPrimitiveLongArrayJsonSerializer() {
        return PrimitiveLongArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<short[]> getPrimitiveShortArrayJsonSerializer() {
        return PrimitiveShortArrayJsonSerializer.getInstance();
    }

    public JsonSerializer<BigDecimal> getBigDecimalJsonSerializer() {
        return BigDecimalJsonSerializer.getInstance();
    }

    public JsonSerializer<BigInteger> getBigIntegerJsonSerializer() {
        return BigIntegerJsonSerializer.getInstance();
    }

    public JsonSerializer<Boolean> getBooleanJsonSerializer() {
        return BooleanJsonSerializer.getInstance();
    }

    public JsonSerializer<Byte> getByteJsonSerializer() {
        return ByteJsonSerializer.getInstance();
    }

    public JsonSerializer<Character> getCharacterJsonSerializer() {
        return CharacterJsonSerializer.getInstance();
    }

    public JsonSerializer<Date> getDateJsonSerializer() {
        return DateJsonSerializer.getInstance();
    }

    public JsonSerializer<Double> getDoubleJsonSerializer() {
        return DoubleJsonSerializer.getInstance();
    }

    public JsonSerializer<Float> getFloatJsonSerializer() {
        return FloatJsonSerializer.getInstance();
    }

    public JsonSerializer<Integer> getIntegerJsonSerializer() {
        return IntegerJsonSerializer.getInstance();
    }

    public JsonSerializer<Long> getLongJsonSerializer() {
        return LongJsonSerializer.getInstance();
    }

    public <T> JsonSerializer<T> getRawValueJsonSerializer() {
        return RawValueJsonSerializer.getInstance();
    }

    public JsonSerializer<Short> getShortJsonSerializer() {
        return ShortJsonSerializer.getInstance();
    }

    public JsonSerializer<java.sql.Date> getSqlDateJsonSerializer() {
        return SqlDateJsonSerializer.getInstance();
    }

    public JsonSerializer<Time> getSqlTimeJsonSerializer() {
        return SqlTimeJsonSerializer.getInstance();
    }

    public JsonSerializer<Timestamp> getSqlTimestampJsonSerializer() {
        return SqlTimestampJsonSerializer.getInstance();
    }

    public JsonSerializer<String> getStringJsonSerializer() {
        return StringJsonSerializer.getInstance();
    }

    public JsonSerializer<UUID> getUUIDJsonSerializer() {
        return UUIDJsonSerializer.getInstance();
    }

    public <E extends Enum<E>> JsonSerializer<E> getEnumJsonSerializer() {
        return EnumJsonSerializer.getInstance();
    }

    public <T> JsonSerializer<T[]> newArrayJsonSerializer( JsonSerializer<T> serializer ) {
        return ArrayJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<AbstractCollection<T>> newAbstractCollectionJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<AbstractList<T>> newAbstractListJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<AbstractQueue<T>> newAbstractQueueJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<AbstractSequentialList<T>> newAbstractSequentialListJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<AbstractSet<T>> newAbstractSetJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<ArrayList<T>> newArrayListJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<Collection<T>> newCollectionJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <E extends Enum<E>> JsonSerializer<EnumSet<E>> newEnumSetJsonSerializer( JsonSerializer<E> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<HashSet<T>> newHashSetJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<Iterable<T>> newIterableJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<LinkedHashSet<T>> newLinkedHashSetJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<LinkedList<T>> newLinkedListJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<List<T>> newListJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<PriorityQueue<T>> newPriorityQueueJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<Queue<T>> newQueueJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<Set<T>> newSetJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<SortedSet<T>> newSortedSetJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<Stack<T>> newStackJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<TreeSet<T>> newTreeSetJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <T> JsonSerializer<Vector<T>> newVectorJsonSerializer( JsonSerializer<T> serializer ) {
        return IterableJsonSerializer.newInstance( serializer );
    }

    public <K, V> JsonSerializer<AbstractMap<K, V>> newAbstractMapJsonSerializer( KeySerializer<K> keySerializer,
                                                                                  JsonSerializer<V> valueSerializer ) {
        return MapJsonSerializer.newInstance( keySerializer, valueSerializer );
    }

    public <E extends Enum<E>, V> JsonSerializer<EnumMap<E, V>> newEnumMapJsonSerializer( KeySerializer<E> keySerializer,
                                                                                          JsonSerializer<V> valueSerializer ) {
        return MapJsonSerializer.newInstance( keySerializer, valueSerializer );
    }

    public <K, V> JsonSerializer<HashMap<K, V>> newHashMapJsonSerializer( KeySerializer<K> keySerializer,
                                                                          JsonSerializer<V> valueSerializer ) {
        return MapJsonSerializer.newInstance( keySerializer, valueSerializer );
    }

    public <K, V> JsonSerializer<IdentityHashMap<K, V>> newIdentityHashMapJsonSerializer( KeySerializer<K> keySerializer,
                                                                                          JsonSerializer<V> valueSerializer ) {
        return MapJsonSerializer.newInstance( keySerializer, valueSerializer );
    }

    public <K, V> JsonSerializer<LinkedHashMap<K, V>> newLinkedHashMapJsonSerializer( KeySerializer<K> keySerializer,
                                                                                      JsonSerializer<V> valueSerializer ) {
        return MapJsonSerializer.newInstance( keySerializer, valueSerializer );
    }

    public <K, V> JsonSerializer<Map<K, V>> newMapJsonSerializer( KeySerializer<K> keySerializer, JsonSerializer<V> valueSerializer ) {
        return MapJsonSerializer.newInstance( keySerializer, valueSerializer );
    }

    public <K, V> JsonSerializer<SortedMap<K, V>> newSortedMapJsonSerializer( KeySerializer<K> keySerializer,
                                                                              JsonSerializer<V> valueSerializer ) {
        return MapJsonSerializer.newInstance( keySerializer, valueSerializer );
    }

    public <K, V> JsonSerializer<TreeMap<K, V>> newTreeMapJsonSerializer( KeySerializer<K> keySerializer,
                                                                          JsonSerializer<V> valueSerializer ) {
        return MapJsonSerializer.newInstance( keySerializer, valueSerializer );
    }

    /*##############################*/
    /*#####   Key serializers  #####*/
    /*##############################*/

    public KeySerializer<Boolean> getBooleanKeySerializer() {
        return BooleanKeySerializer.getInstance();
    }

    public KeySerializer<Character> getCharacterKeySerializer() {
        return CharacterKeySerializer.getInstance();
    }

    public <T extends Enum<T>> KeySerializer<T> getEnumKeySerializer() {
        return EnumKeySerializer.getInstance();
    }

    public KeySerializer<String> getStringKeySerializer() {
        return StringKeySerializer.getInstance();
    }

    public KeySerializer<UUID> getUUIDKeySerializer() {
        return UUIDKeySerializer.getInstance();
    }

    public KeySerializer<BigDecimal> getBigDecimalKeySerializer() {
        return BigDecimalKeySerializer.getInstance();
    }

    public KeySerializer<BigInteger> getBigIntegerKeySerializer() {
        return BigIntegerKeySerializer.getInstance();
    }

    public KeySerializer<Byte> getByteKeySerializer() {
        return ByteKeySerializer.getInstance();
    }

    public KeySerializer<Double> getDoubleKeySerializer() {
        return DoubleKeySerializer.getInstance();
    }

    public KeySerializer<Float> getFloatKeySerializer() {
        return FloatKeySerializer.getInstance();
    }

    public KeySerializer<Integer> getIntegerKeySerializer() {
        return IntegerKeySerializer.getInstance();
    }

    public KeySerializer<Long> getLongKeySerializer() {
        return LongKeySerializer.getInstance();
    }

    public KeySerializer<Short> getShortKeySerializer() {
        return ShortKeySerializer.getInstance();
    }

    public KeySerializer<Date> getDateKeySerializer() {
        return DateKeySerializer.getInstance();
    }

    public KeySerializer<java.sql.Date> getSqlDateKeySerializer() {
        return SqlDateKeySerializer.getInstance();
    }

    public KeySerializer<Time> getSqlTimeKeySerializer() {
        return SqlTimeKeySerializer.getInstance();
    }

    public KeySerializer<Timestamp> getSqlTimestampKeySerializer() {
        return SqlTimestampKeySerializer.getInstance();
    }
}
