package br.com.jpribeiro.misr_assignment.exceptions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CustomExceptionHandlerTest {

    CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

    @Test
    public void testMethodArgumentNotValid() {
        BindingResult bindingResult = new AbstractBindingResult("object1") {
            @Override
            public Object getTarget() {
                return null;
            }

            @Override
            protected Object getActualFieldValue(String field) {
                return null;
            }
        };
        bindingResult.addError(new FieldError("object1", "field1", "message1"));
        bindingResult.addError(new FieldError("object1", "field2", "message2"));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Object> responseEntity = customExceptionHandler.handleMethodArgumentNotValid(exception, null, null, null);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);

        Map<String, Map<String, String>> body = (Map<String, Map<String, String>>) responseEntity.getBody();
        assertEquals(body.size(), 1);
        assertEquals(body.get("Validation errors").size(), 2);
        assertEquals(body.get("Validation errors").get("field1"), "message1");
        assertEquals(body.get("Validation errors").get("field2"), "message2");
    }

    @Test
    public void testNotFoundException() {
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleExceptions(new NotFoundException());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testIrrigationException() {
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleExceptions(new IrrigationException());
        assertEquals(responseEntity.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);
    }

}
