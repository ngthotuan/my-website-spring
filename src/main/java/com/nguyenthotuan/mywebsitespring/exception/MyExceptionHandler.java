package com.nguyenthotuan.mywebsitespring.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {


    /**
     * All Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllException(Exception ex){
        log.error("An Exception: ", ex);
        return "error/500";
    }

    /**
     * NotFound Exception
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleNotFoundRoute(Exception ex) {
        log.warn("NoHandlerFoundException: ", ex);
        return "error/404";
    }

    /**
     * StorageException Exception
     */
    @ExceptionHandler(StorageException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleStorageException(Exception ex, Model model) {
        log.error("StorageException: ", ex);
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }
}
