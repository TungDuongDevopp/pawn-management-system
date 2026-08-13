package com.tungduong.pawnmanagement.helper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter @Setter
public class ApiResponse<T> {
    private String message;
    private T data;
    private String error;
    private String status;
    private LocalDateTime timestamp;

    public ApiResponse(HttpStatus httpStatus, String message, T data, String errorCode) {
        this.message = message;
        this.data = data;
        this.error = errorCode;
        this.timestamp = LocalDateTime.now();
        this.status = httpStatus.is2xxSuccessful() ? "success" : "error";
    }
    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.OK, "Call API success.", data, null);
        return ResponseEntity.ok(response);
    }

    // 2. Success: Trả về HTTP 200 OK + Data + Message tùy chỉnh
    public static <T> ResponseEntity<ApiResponse<T>> success(T data,String message) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.OK, message, data, null);
        return ResponseEntity.ok(response);
    }

    // 3. Created: Trả về HTTP 201 Created (Dùng cho tạo mới)
    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.CREATED, "Created successfully", data, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //4. Deleted Trả về HTTP Status 404
    public static <T> ResponseEntity<ApiResponse<T>> delete(String message) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.NO_CONTENT,"Deleted successfully",null,null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
    // 4. Error: Trả về HTTP Status tùy chỉnh (400, 404, 500...) + Error Code
    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message, String errorCode) {
        ApiResponse<T> response = new ApiResponse<>(status, message, null, errorCode);
        return ResponseEntity.status(status).body(response);
    }

    // 5. Error cơ bản: Chỉ cần Status và Message
    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message) {
        return error(status, message, String.valueOf(status.value()));
    }
}
