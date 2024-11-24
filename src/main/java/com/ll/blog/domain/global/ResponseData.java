package com.ll.blog.domain.global;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class ResponseData <T> {
    private int statusCode;
    private String responseMessage;
    private T data;

    public ResponseData(final int statusCode, final String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = null;
    }
    public static<T> ResponseData<T> res(final int statusCode, final String responseMessage, final T data) {
        return ResponseData.<T>builder()
                .data(data)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }
    public static<T> ResponseData<T> res(final int statusCode, final String responseMessage) {
        return res(statusCode, responseMessage, null);
    }

    public static<T> ResponseData<T> res(final int statusCode , final T data) {
        return res(statusCode, null, data);
    }
}
