package com.ahmed.move.error.handelars;
import com.ahmed.move.dto.GeneralResponseDto;
import com.ahmed.move.error.dto.ErrorResponse;
import com.ahmed.move.error.exceptions.AbstractMovieException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    ErrorResponse errorResponse = new ErrorResponse();


                                                                                            //,ErrorResponse errorResponse
    public ResponseEntity<GeneralResponseDto> error(HttpStatus status, Exception e, String msg, ErrorResponse errorResponse) {
        errorResponse.setSuccess(false);
        errorResponse.setMessage("Error while fetching movie");
        errorResponse.setDateTime(LocalDateTime.now());
        errorResponse.setDetails(List.of("Movie with given title not found in the database"));

        log.error("error message :" + e.getMessage());
        return ResponseEntity.status(status).body(
                GeneralResponseDto.builder()
                        .time(LocalDateTime.now())
                        .error(Map.of(

                                "errorMessage", msg,
                                "error data", errorResponse
                        ))
                        .build()
        );
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<GeneralResponseDto> handleException(Exception e) {
                                                        //,new ErrorResponse()
        return error(INTERNAL_SERVER_ERROR, e, e.getMessage(),errorResponse);
    }


    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<GeneralResponseDto> handleRunTimeException(RuntimeException e) {
                                                        //,new ErrorResponse()
        return error(INTERNAL_SERVER_ERROR, e, e.getMessage(),errorResponse);

    }

    @ExceptionHandler({AbstractMovieException.class})
    public ResponseEntity<GeneralResponseDto> handleCustomException(AbstractMovieException e) {
                                                         //,new ErrorResponse()
        return error(INTERNAL_SERVER_ERROR, e, e.getMessage(),errorResponse);
    }

}
