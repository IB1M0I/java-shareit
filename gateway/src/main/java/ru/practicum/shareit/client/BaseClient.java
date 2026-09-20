package ru.practicum.shareit.client;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

// Базовый класс для HTTP клиентов
public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<Object> get(String path) {
        return get(path, null, null, Object.class);
    }

    protected ResponseEntity<Object> get(String path, long userId) {
        return get(path, userId, null, Object.class);
    }

    protected <T> ResponseEntity<T> get(String path, Long userId, @Nullable Map<String, Object> parameters, Class<T> responseType) {
        return makeAndSendRequest(HttpMethod.GET, path, userId, parameters, null, responseType);
    }

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return post(path, null, null, body, Object.class);
    }

    protected <T> ResponseEntity<Object> post(String path, long userId, T body) {
        return post(path, userId, null, body, Object.class);
    }

    protected <T, R> ResponseEntity<R> post(String path, Long userId, @Nullable Map<String, Object> parameters, T body, Class<R> responseType) {
        return makeAndSendRequest(HttpMethod.POST, path, userId, parameters, body, responseType);
    }

    protected <T> ResponseEntity<Object> put(String path, long userId, T body) {
        return put(path, userId, null, body, Object.class);
    }

    protected <T, R> ResponseEntity<R> put(String path, long userId, @Nullable Map<String, Object> parameters, T body, Class<R> responseType) {
        return makeAndSendRequest(HttpMethod.PUT, path, userId, parameters, body, responseType);
    }

    protected <T> ResponseEntity<Object> patch(String path, T body) {
        return patch(path, null, null, body, Object.class);
    }

    protected <T> ResponseEntity<Object> patch(String path, long userId) {
        return patch(path, userId, null, null, Object.class);
    }

    protected <T> ResponseEntity<Object> patch(String path, long userId, T body) {
        return patch(path, userId, null, body, Object.class);
    }

    protected <T, R> ResponseEntity<R> patch(String path, Long userId, @Nullable Map<String, Object> parameters, T body, Class<R> responseType) {
        return makeAndSendRequest(HttpMethod.PATCH, path, userId, parameters, body, responseType);
    }

    protected ResponseEntity<Object> delete(String path) {
        return delete(path, null, null, Object.class);
    }

    protected ResponseEntity<Object> delete(String path, long userId) {
        return delete(path, userId, null, Object.class);
    }

    protected <T> ResponseEntity<T> delete(String path, Long userId, @Nullable Map<String, Object> parameters, Class<T> responseType) {
        return makeAndSendRequest(HttpMethod.DELETE, path, userId, parameters, null, responseType);
    }

    private <T, R> ResponseEntity<R> makeAndSendRequest(HttpMethod method, String path, Long userId, @Nullable Map<String, Object> parameters, @Nullable T body, Class<R> responseType) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders(userId));

        ResponseEntity<R> shareitServerResponse;
        try {
            if (parameters != null) {
                shareitServerResponse = rest.exchange(path, method, requestEntity, responseType, parameters);
            } else {
                shareitServerResponse = rest.exchange(path, method, requestEntity, responseType);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body((R) e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(shareitServerResponse);
    }

    private HttpHeaders defaultHeaders(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        if (userId != null) {
            headers.set("X-Sharer-User-Id", String.valueOf(userId));
        }
        return headers;
    }

    private static <R> ResponseEntity<R> prepareGatewayResponse(ResponseEntity<R> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
