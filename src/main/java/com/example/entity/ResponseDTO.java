package com.example.entity;

/**
 * Created by codedrinker on 02/07/2017.
 */
public class ResponseDTO {
    private int status = 200;
    private String message;
    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseDTO ok(Object o) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setData(o);
        responseDTO.setStatus(200);
        responseDTO.setMessage("success");
        return responseDTO;
    }

    public static ResponseDTO error(String message) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus(400);
        responseDTO.setMessage(message);
        return responseDTO;
    }
}
