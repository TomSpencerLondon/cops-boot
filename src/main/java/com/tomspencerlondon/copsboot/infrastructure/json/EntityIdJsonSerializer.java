package com.tomspencerlondon.copsboot.infrastructure.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.tomspencerlondon.orm.jpa.EntityId;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;


@JsonComponent
public class EntityIdJsonSerializer extends JsonSerializer<EntityId> {

  @Override
  public void serialize(EntityId entityId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(entityId.asString());
  }
}
