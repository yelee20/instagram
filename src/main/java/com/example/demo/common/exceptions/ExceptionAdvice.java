package com.example.demo.common.exceptions;

import com.example.demo.common.response.BaseResponse;
import com.example.demo.common.response.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import javax.validation.UnexpectedTypeException;


@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public BaseResponse<BaseResponseStatus> BaseExceptionHandle(BaseException exception) {
        log.warn("BaseException. error message: {}", exception.getMessage());
        return new BaseResponse<>(exception.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<BaseResponseStatus> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        FieldError fieldError = result.getFieldError();
        log.warn("MethodArgumentNotValidException. field: {}, error: {}", fieldError.getField(), fieldError.getDefaultMessage());
        BaseResponseStatus responseStatus = BaseResponseStatus.INVALID_REQUEST_FORMAT;
        responseStatus.setMessage(fieldError.getDefaultMessage());
        return new BaseResponse<>(responseStatus);
    }

    @ExceptionHandler(BindException.class)
    public BaseResponse<BaseResponseStatus> bindException(BindException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        BaseResponseStatus responseStatus = BaseResponseStatus.INVALID_REQUEST_FORMAT;
        responseStatus.setMessage(fieldErrors.get(0).getDefaultMessage());
        return new BaseResponse<>(responseStatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse<BaseResponseStatus> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        BaseResponseStatus responseStatus = BaseResponseStatus.INVALID_REQUEST_FORMAT;
        responseStatus.setMessage(exception.getMessage());
        return new BaseResponse<>(responseStatus);
    }
    @ExceptionHandler(MissingPathVariableException.class)
    public BaseResponse<BaseResponseStatus> missingPathVariableException(MissingPathVariableException exception) {
        BaseResponseStatus responseStatus = BaseResponseStatus.INVALID_REQUEST_FORMAT;
        responseStatus.setMessage(exception.getMessage());
        return new BaseResponse<>(responseStatus);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public BaseResponse<BaseResponseStatus> unexpectedTypeException(UnexpectedTypeException exception) {
        log.warn("UnexpectedTypeException. error message: {}", exception.getMessage());
        BaseResponseStatus responseStatus = BaseResponseStatus.INVALID_REQUEST_FORMAT;
        responseStatus.setMessage(exception.getMessage());
        return new BaseResponse<>(responseStatus);
    }

    @ExceptionHandler(Throwable.class)
    public BaseResponse<BaseResponseStatus> exceptionHandle(Throwable throwable) {
        log.error("Exception has occurred. ", throwable);
        return new BaseResponse<>(BaseResponseStatus.UNEXPECTED_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<BaseResponseStatus> ExceptionHandle(Exception exception) {
        log.error("Exception has occured. ", exception);
        return new BaseResponse<>(BaseResponseStatus.UNEXPECTED_ERROR);
    }

}
