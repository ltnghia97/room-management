package org.rootle.rentalroom.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rootle.rentalroom.constant.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {
    @JsonProperty("result")
    private String result;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private T data;
    @JsonProperty("timestamp")
    private String timestamp;


    public static <T> ResponseData<T> execute(T data, String message, String result) {
        ResponseData<T> response = new ResponseData<>();
        response.result = Optional.ofNullable(result).orElse(Constant.RESULT_OK);
        response.message = Optional.ofNullable(message).orElse(Constant.MESSAGE_OK);
        response.data = data;
        response.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return response;
    }
}