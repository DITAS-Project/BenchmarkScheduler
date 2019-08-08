/*
 *
 *  * Copyright 2018 Information Systems Engineering, TU Berlin, Germany
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *                       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *  * This is being developed for the DITAS Project: https://www.ditas-project.eu/
 *
 */

package de.tub.benchmarkscheduler.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CsvConverter<T> extends AbstractHttpMessageConverter<T> {

    private final ObjectMapper objectMapper;

    public CsvConverter(ObjectMapper objectMapper) {
        super(new MediaType("text", "csv"));
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(T object, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        try {
            ObjectWriter objectWriter = getCsvWriter(object);
            try (PrintWriter outputWriter = new PrintWriter(outputMessage.getBody())) {
                outputWriter.write(objectWriter.writeValueAsString(object));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ObjectWriter getCsvWriter(T object) {
        Set<String> fields = getUniqueFieldNames(object);
        CsvSchema.Builder schemaBuilder = CsvSchema.builder().setUseHeader(true);
        for (String field : fields) {
            schemaBuilder.addColumn(field);
        }
        return new CsvMapper().writerFor(List.class).with(schemaBuilder.build());
    }

    Set<String> getUniqueFieldNames(T object) {
        try {
            JsonNode root = objectMapper.readTree(objectMapper.writeValueAsString(object));
            Set<String> uniqueFieldNames = new LinkedHashSet<>();
            root.forEach(element -> {
                Iterator<String> it = element.fieldNames();
                while (it.hasNext()) {
                    String field = it.next();
                    uniqueFieldNames.add(field);
                }
            });
            return uniqueFieldNames;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
