package com.github.nmorel.gwtjackson.objectify.shared;

import java.util.Collections;
import java.util.Map;

import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.impl.ref.DeadRef;

public class JsonConstant {

    public static String RAW_KEY1_JSON = "{\"parent\":null,\"kind\":\"Foo\",\"id\":1,\"name\":null}";

    public static String RAW_KEY2_JSON = "{\"parent\":{\"parent\":null,\"kind\":\"Foo\",\"id\":1,\"name\":null},\"kind\":\"Foo\"," +
            "\"id\":0,\"name\":\"foo\"}";

    public static String KEY1_JSON = "{\"raw\":{\"parent\":null,\"kind\":\"Foo\",\"id\":1,\"name\":null}}";

    public static String KEY2_JSON = "{\"raw\":{\"parent\":{\"parent\":null,\"kind\":\"Foo\",\"id\":1,\"name\":null},\"kind\":\"Foo\"," +
            "\"id\":0,\"name\":\"foo\"}}";

    public static String REF1_JSON = "{\"key\":{\"raw\":{\"parent\":null,\"kind\":\"Foo\",\"id\":1,\"name\":null}},\"value\":null}";

    public static String REF2_JSON = "{\"key\":{\"raw\":{\"parent\":{\"parent\":null,\"kind\":\"Foo\",\"id\":1,\"name\":null}," +
            "\"kind\":\"Foo\",\"id\":0,\"name\":\"foo\"}},\"value\":null}";

    public static String BEAN1_JSON = "{\"id\":1,\"string\":\"string1\",\"set\":[1]}";

    public static String BEAN_REF_JSON = "{\"key\":{\"raw\":{\"parent\":null,\"kind\":\"Bean\",\"id\":1,\"name\":null}}," +
            "\"value\":{\"id\":1,\"string\":\"string1\",\"set\":[1]}}";

    public static String RAW_KEY_MAP1_JSON = "{\"{\\\"parent\\\":null,\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1,\\\"name\\\":null}\":null}";

    public static String RAW_KEY_MAP2_JSON = "{\"{\\\"parent\\\":{\\\"parent\\\":null,\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1," +
            "\\\"name\\\":null},\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":0,\\\"name\\\":\\\"foo\\\"}\":\"value2\"}";

    public static String KEY_MAP1_JSON = "{\"{\\\"raw\\\":{\\\"parent\\\":null,\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1," +
            "\\\"name\\\":null}}\":null}";

    public static String KEY_MAP2_JSON = "{\"{\\\"raw\\\":{\\\"parent\\\":{\\\"parent\\\":null,\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1," +
            "\\\"name\\\":null},\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":0,\\\"name\\\":\\\"foo\\\"}}\":\"value2\"}";

    public static String REF_MAP1_JSON = "{\"{\\\"key\\\":{\\\"raw\\\":{\\\"parent\\\":null,\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":1," +
            "\\\"name\\\":null}},\\\"value\\\":null}\":null}";

    public static String REF_MAP2_JSON = "{\"{\\\"key\\\":{\\\"raw\\\":{\\\"parent\\\":{\\\"parent\\\":null,\\\"kind\\\":\\\"Foo\\\"," +
            "\\\"id\\\":1,\\\"name\\\":null},\\\"kind\\\":\\\"Foo\\\",\\\"id\\\":0,\\\"name\\\":\\\"foo\\\"}}," +
            "\\\"value\\\":null}\":\"value2\"}";

    public static com.google.appengine.api.datastore.Key RAW_KEY1, RAW_KEY2;

    public static Key<Object> KEY1, KEY2;

    public static Ref<Object> REF1, REF2;

    public static Bean BEAN = new Bean( 1L, "string1", Collections.singleton( 1 ) );

    public static Ref<Bean> BEAN_REF = new DeadRef<>( Key.<Bean>create( KeyFactory.createKey( null, "Bean", 1L ) ), BEAN );

    public static Map<com.google.appengine.api.datastore.Key, Object> RAW_KEY_MAP1, RAW_KEY_MAP2;

    public static Map<Key<Object>, Object> KEY_MAP1, KEY_MAP2;

    public static Map<Ref<Object>, Object> REF_MAP1, REF_MAP2;

    static {
        RAW_KEY1 = KeyFactory.createKey( null, "Foo", 1L );
        RAW_KEY2 = KeyFactory.createKey( RAW_KEY1, "Foo", "foo" );
        KEY1 = Key.create( RAW_KEY1 );
        KEY2 = Key.create( RAW_KEY2 );
        REF1 = new DeadRef( KEY1 );
        REF2 = new DeadRef( KEY2 );
        RAW_KEY_MAP1 = Collections.singletonMap( RAW_KEY1, null );
        RAW_KEY_MAP2 = Collections.singletonMap( RAW_KEY2, (Object) "value2" );
        KEY_MAP1 = Collections.singletonMap( KEY1, null );
        KEY_MAP2 = Collections.singletonMap( KEY2, (Object) "value2" );
        REF_MAP1 = Collections.singletonMap( REF1, null );
        REF_MAP2 = Collections.singletonMap( REF2, (Object) "value2" );
    }

}
