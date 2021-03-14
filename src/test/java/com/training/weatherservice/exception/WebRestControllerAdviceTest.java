package com.training.weatherservice.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WebRestControllerAdviceTest {

    @InjectMocks
    WebRestControllerAdvice controllerAdvice;

    @Test
    public void shouldReturnBadRequestErrorWhenIsMethodArgumentNotValidException() {
        Exception ex = new Exception();

        ResponseEntity<Object> response = controllerAdvice.handleArgumentException(ex);
        ResponseMsg responseMsg = (ResponseMsg) response.getBody();

        assertEquals("Bad arguments", responseMsg.getMessage());
    }

    @Test
    public void shouldReturnInternalServerErrorWhenIsRestClientException() {
        String expected = "Error";
        ResponseEntity<Object> data = controllerAdvice.handleException(new RestClientException(expected));
        String result = ((ResponseMsg) data.getBody()).getDetail();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, data.getStatusCode());
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnNotFoundExceptionWhenWeatherInformationIsEmpty() {
        String expected = "Weather Information not found";
        ResponseEntity<Object> data = controllerAdvice.handleNotFoundException(new CustomNotFoundException(expected));
        String result = ((ResponseMsg) data.getBody()).getDetail();

        assertEquals(HttpStatus.NOT_FOUND, data.getStatusCode());
        assertEquals(expected, result);
    }
}
