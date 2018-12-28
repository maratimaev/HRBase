package ru.bellintegrator.hrbase.view.result;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@ControllerAdvice
public class ResponseAdviser implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object object,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (object instanceof Success) {
            return object;
        }
        if (object instanceof Error) {
            if (((Error) object).getHttpStatus() != null) {
                serverHttpResponse.setStatusCode(((Error) object).getHttpStatus());
            }
            return object;
        }
        if (object instanceof List) {
            return new Wrapper<>((List<Object>) object);
        }
        return new Wrapper<>(object);
    }
}
