package culturemedia.advice;

import culturemedia.exception.CultureMediaException;
import culturemedia.exception.VideoNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CultureMediaControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CultureMediaControllerAdvice.class);

    @ExceptionHandler(VideoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Throwable> handleVideoNotFoundException(VideoNotFoundException ex) {
        logger.error("VideoNotFoundException handled: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex);
    }

    @ExceptionHandler(CultureMediaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Throwable> handleCultureMediaException(CultureMediaException ex) {
        logger.error("CultureMediaException handled: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
    }


}

