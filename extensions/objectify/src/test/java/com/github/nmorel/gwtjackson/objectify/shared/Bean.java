package com.github.nmorel.gwtjackson.objectify.shared;

import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Bean {

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
