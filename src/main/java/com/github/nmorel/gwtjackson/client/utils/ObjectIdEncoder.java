package com.github.nmorel.gwtjackson.client.utils;

import com.github.nmorel.gwtjackson.client.JsonEncodingContext;
import com.github.nmorel.gwtjackson.client.JsonMapper;
import com.github.nmorel.gwtjackson.client.stream.JsonWriter;

/** @author Nicolas Morel */
public class ObjectIdEncoder<I>
{
    private I id;
    private JsonMapper<I> mapper;

    public ObjectIdEncoder( I id, JsonMapper<I> mapper )
    {
        this.id = id;
        this.mapper = mapper;
    }

    public void encodeId( JsonWriter writer, JsonEncodingContext ctx )
    {
        mapper.encode( writer, id, ctx );
    }
}
