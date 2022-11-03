package com.example.pisosweb.document;

import com.mongodb.lang.NonNull;
import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document(collection = "comentarios")
public class Comment implements Serializable {
    @Id
    @NonNull
    private String id;

    @NonNull
    private String text;

    @NonNull
    private String rating;

    @NonNull
    private String apartment;

    @NonNull
    private String user;
}
