package com.github.nmorel.gwtjackson.objectify.shared;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.github.nmorel.gwtjackson.shared.AbstractTester;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.impl.ref.DeadRef;

public abstract class ObjectifyAbstractTester extends AbstractTester {

    public static final com.google.appengine.api.datastore.Key RAW_KEY1;

    public static final com.google.appengine.api.datastore.Key RAW_KEY2;

    public static final Key<Object> KEY1;

    public static final Key<Object> KEY2;

    public static final Ref<Object> REF1;

    public static final Ref<Object> REF2;

    public static final Bean BEAN = new Bean( 1L, "string1", Collections.singleton( 1 ) );

    public static final Ref<Bean> BEAN_REF = new DeadRef<Bean>( Key.<Bean>create( KeyFactory.createKey( null, "Bean", 1L ) ), BEAN );

    public static final Map<com.google.appengine.api.datastore.Key, Object> RAW_KEY_MAP1;

    public static final Map<com.google.appengine.api.datastore.Key, Object> RAW_KEY_MAP2;

    public static final Map<Key<Object>, Object> KEY_MAP1;

    public static final Map<Key<Object>, Object> KEY_MAP2;

    public static final Map<Ref<Object>, Object> REF_MAP1;

    public static final Map<Ref<Object>, Object> REF_MAP2;

    static {
        RAW_KEY1 = KeyFactory.createKey( null, "Foo", 1L );
        RAW_KEY2 = KeyFactory.createKey( RAW_KEY1, "Foo", "foo" );
        KEY1 = Key.create( RAW_KEY1 );
        KEY2 = Key.create( RAW_KEY2 );
        REF1 = new DeadRef<Object>( KEY1 );
        REF2 = new DeadRef<Object>( KEY2 );
        RAW_KEY_MAP1 = Collections.singletonMap( RAW_KEY1, null );
        RAW_KEY_MAP2 = Collections.singletonMap( RAW_KEY2, (Object) "value2" );
        KEY_MAP1 = Collections.singletonMap( KEY1, null );
        KEY_MAP2 = Collections.singletonMap( KEY2, (Object) "value2" );
        REF_MAP1 = Collections.singletonMap( REF1, null );
        REF_MAP2 = Collections.singletonMap( REF2, (Object) "value2" );
    }

    @Entity
    public static class Bean {

        @Id
        public Long id;

        public String string;

        public Set<Integer> set;

        public Bean() {
        }

        public Bean( Long id, String string, Set<Integer> set ) {
            this.id = id;
            this.string = string;
            this.set = set;
        }

        @Override
        public boolean equals( Object o ) {
            if ( this == o ) {
                return true;
            }
            if ( !(o instanceof Bean) ) {
                return false;
            }

            Bean bean = (Bean) o;

            if ( id != null ? !id.equals( bean.id ) : bean.id != null ) {
                return false;
            }
            if ( string != null ? !string.equals( bean.string ) : bean.string != null ) {
                return false;
            }
            return set != null ? set.equals( bean.set ) : bean.set == null;
        }

        @Override
        public int hashCode() {
            int result = id != null ? id.hashCode() : 0;
            result = 31 * result + (string != null ? string.hashCode() : 0);
            result = 31 * result + (set != null ? set.hashCode() : 0);
            return result;
        }
    }
}
