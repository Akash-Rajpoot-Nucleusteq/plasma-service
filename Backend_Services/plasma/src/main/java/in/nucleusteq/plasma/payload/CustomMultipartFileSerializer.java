package in.nucleusteq.plasma.payload;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomMultipartFileSerializer
        extends
            JsonSerializer<CustomMultipartFile> {
    @Override
    public void serialize(CustomMultipartFile value, JsonGenerator gen,
            SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("fileName", value.getOriginalFilename());
        gen.writeFieldName("fileContent");
        gen.writeBinary(value.getBytes());
        gen.writeEndObject();
    }
}
