package com.hcb.hotchairs.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HotChairsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoDate.class)
    protected ResponseEntity<ExceptionMessage> handleWrongData(){
        return new ResponseEntity<>(new ExceptionMessage("There is no selected days in chosen period")
                , HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class ExceptionMessage {
        private String message;
    }

}
