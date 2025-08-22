package org.rootle.rentalroom.base;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rootle.rentalroom.constant.Constant;
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
    private long timestamp;


    public static <T> ResponseData<T> execute(T data, String message, String result) {
        ResponseData<T> response = new ResponseData<>();
        response.timestamp = System.currentTimeMillis();
        response.result = result;
        response.message = message;
        response.data = data;
        return response;
    }
}