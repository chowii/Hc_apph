package au.com.holcim.holcimapp.network;

import java.io.IOException;

public class HttpStatusException extends IOException {

    public String error;
    public HttpErrorType errorType;

    public HttpStatusException(String error, HttpErrorType errorType) {
        this.error = error;
        this.errorType = errorType;
    }

}
