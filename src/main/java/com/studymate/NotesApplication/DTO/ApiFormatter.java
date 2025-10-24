package com.studymate.NotesApplication.DTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiFormatter {
    private final String message ;
    private final boolean check ;
    private final Object data ;

    public ApiFormatter(String message, boolean check, Object data) {
        this.message = message;
        this.check = check;
        this.data = data;
    }

    public String getMessage(){ return message ;};
    public boolean isCheck() { return check;};
    public Object getData(){return data;};

    public static ResponseEntity<ApiFormatter> success(Object data , String message){
        ApiFormatter response = new ApiFormatter(message , true , data);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    public static ResponseEntity<ApiFormatter> created(Object data, String message){
        ApiFormatter response = new ApiFormatter(message , true , data);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    public static ResponseEntity<ApiFormatter> notfound(Object data , String message){
        ApiFormatter response = new ApiFormatter(message , false , data);
        return new ResponseEntity<>(response , HttpStatus.NOT_FOUND);
    }

}
