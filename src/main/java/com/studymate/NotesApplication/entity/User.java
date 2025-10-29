package com.studymate.NotesApplication.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("users")
public class User {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id ;
    @Indexed(unique = true)
    @NonNull
    private String username ;
    @NonNull
    private String password ;
    @NonNull
    private String name ;
    @DBRef
    private List<NewEntry> userEntries = new ArrayList<>();
    private List<String> roles = new ArrayList<>();
}
