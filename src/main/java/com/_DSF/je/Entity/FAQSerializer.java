package com._DSF.je.Entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class FAQSerializer extends StdSerializer<FAQ> {

    public FAQSerializer() {
        this(null);
    }

    public FAQSerializer(Class<FAQ> t) {
        super(t);
    }

    @Override
    public void serialize(FAQ faq, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", faq.getId());
        jsonGenerator.writeStringField("question", faq.getQuestion());
        jsonGenerator.writeStringField("answer", faq.getAnswer());

        // Serialize the course without the teacher's FAQs to avoid circular reference
        jsonGenerator.writeObjectFieldStart("course");
        jsonGenerator.writeNumberField("id", faq.getCourse().getId());
        jsonGenerator.writeStringField("title", faq.getCourse().getTitle());

        // Check if the teacher is null before attempting to serialize
        if (faq.getCourse().getTeacher() != null) {
            jsonGenerator.writeObjectFieldStart("teacher");
            jsonGenerator.writeNumberField("id", faq.getCourse().getTeacher().getId());
            jsonGenerator.writeStringField("username", faq.getCourse().getTeacher().getUsername());
            jsonGenerator.writeStringField("email", faq.getCourse().getTeacher().getEmail());
            jsonGenerator.writeEndObject(); // End teacher
        } else {
            jsonGenerator.writeNullField("teacher"); // Handle null teacher
        }

        jsonGenerator.writeEndObject(); // End course

        jsonGenerator.writeEndObject(); // End FAQ
    }
}
