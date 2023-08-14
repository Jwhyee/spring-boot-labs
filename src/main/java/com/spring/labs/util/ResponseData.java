package com.spring.labs.util;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

public class ResponseData {

    private ResponseData() {
        throw new AssertionError("객체 생성 불가 클래스입니다.");
    }

     /* <T> ApiResult<T>
    T : 제네릭 타입 -> 메소드 매개변수로 제네릭 타입을 받을 수 있음
    <T> : 메소드 매개변수로 제네릭 타입을 받을 수 있게 해줌
    ApiResult<T> : 매개변수로 받아온 T 데이터를 ApiResult에 넘겨줄 수 있도록 해줌
    */

    public static <T> ApiResult<T> success(T data, String msg) {
        return new ApiResult<>(true, msg, data, null);
    }

    /* ApiResult<?>
    ? : 와일드카드 타입 -> ApiResult record가 <T>가 필요하기 때문에 붙여줌
    T는 메소드 호출 시 data의 반환 타입을 구체적인 타입으로 대체되지만,
    ?는 메소드 호출 시 data의 반환 타입을 구체적인 타입으로 대체되지 않음!
     */
    public static ApiResult<?> error(String msg, HttpStatus status) {
        return new ApiResult<>(false, null, null, new ApiError(status, msg));
    }

    // record == (static)class + Getter + Setter + hashCode + equals + toString
    public record ApiResult<T>(boolean success, String message, T data, ApiError error) {
    }

    @Getter
    @ToString
    public static class ApiError {
        private final int code;
        private final HttpStatus status;
        private final String message;

        public ApiError(HttpStatus status, String message) {
            this.status = status;
            this.code = status.value();
            this.message = message;
        }
    }

}
