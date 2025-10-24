package com.studymate.NotesApplication.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "toDos")
@Data
@NoArgsConstructor
public class NewEntry {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id ;
    @NonNull
    private String title ;

    private String desc;

    private LocalDateTime date ;

}
