package resttemplate.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import resttemplate.model.User;

import java.util.List;

@Component
public class RestTemplateClient {
    private static final String urlUsers = "http://91.241.64.178:7081/api/users";

    private final RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    public RestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<List<User>> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(urlUsers, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
                });
        return responseEntity;
    }

    public ResponseEntity<String> addUser(User user, String cookies) {
        //устанавливаем заголовки запроса
        headers.set("Cookie", cookies);
        //оборачиваем объект запроса
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        //указываем URL-адрес, HttpMethod и тип возвращаемого значения для метода Exchange ().
        ResponseEntity<String> responseEntity = restTemplate.exchange(urlUsers, HttpMethod.POST, entity, String.class);
        return responseEntity;
    }

    public ResponseEntity<String> updateUser(User user, String cookies) {

        headers.set("Cookie", cookies);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(urlUsers, HttpMethod.PUT, entity, String.class);
        return responseEntity;
    }

    public ResponseEntity<String> deleteUser(User user, String cookies) {

        headers.set("Cookie", cookies);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(urlUsers + "/" + user.getId(), HttpMethod.DELETE, entity, String.class);
        return responseEntity;
    }
}
