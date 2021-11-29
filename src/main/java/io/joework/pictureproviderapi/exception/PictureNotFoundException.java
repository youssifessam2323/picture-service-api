package io.joework.pictureproviderapi.exception;

public class PictureNotFoundException extends RuntimeException {
    
    public PictureNotFoundException(String msg){
        super(msg);
    }
}
