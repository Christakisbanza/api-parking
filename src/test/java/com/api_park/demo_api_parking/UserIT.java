package com.api_park.demo_api_parking;

import com.api_park.demo_api_parking.web.controller.dto.UserCreateDto;
import com.api_park.demo_api_parking.web.controller.dto.UserPassWordDto;
import com.api_park.demo_api_parking.web.controller.dto.UserResponseDto;
import com.api_park.demo_api_parking.web.controller.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUser_ifUserNameAndPasswordValid_ReturnUserCreatedWithStatus201(){
        UserResponseDto userResponseDto = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("test@gmail.com","123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(userResponseDto).isNotNull();
        Assertions.assertThat(userResponseDto.getId()).isNotNull();
        Assertions.assertThat(userResponseDto.getUserName()).isEqualTo("test@gmail.com");
        Assertions.assertThat(userResponseDto.getRole()).isEqualTo("Client");
    }

    @Test
    public void createUser_ifUserNameNotValid_ReturnErrorMessageWithStatus422(){
        ErrorMessage errorMsg = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("","123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMsg).isNotNull();
        Assertions.assertThat(errorMsg.getStatus()).isEqualTo(422);

        testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("@gmail","123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMsg).isNotNull();
        Assertions.assertThat(errorMsg.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUser_ifPasswordNotValid_ReturnErrorMessageWithStatus422(){
        ErrorMessage errorMsg = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("test@gmail.com","1234567"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMsg).isNotNull();
        Assertions.assertThat(errorMsg.getStatus()).isEqualTo(422);

        testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("@gmail","12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(errorMsg).isNotNull();
        Assertions.assertThat(errorMsg.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUser_ifUserNameDuplicate_ReturnErrorMessageWithStatus409(){
        ErrorMessage userResponseDto = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDto("naruto@gmail.com","123456"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(userResponseDto).isNotNull();
        Assertions.assertThat(userResponseDto.getStatus()).isEqualTo(409);
    }

    @Test
    public void fondUser_ifIdExist_ReturnUserWithStatus200(){
        UserResponseDto userResponseDto = testClient
                .get()
                .uri("/api/v1/users/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(userResponseDto).isNotNull();
        Assertions.assertThat(userResponseDto.getId()).isEqualTo(100);
        Assertions.assertThat(userResponseDto.getUserName()).isEqualTo("Boruto@gmail.com");
        Assertions.assertThat(userResponseDto.getRole()).isEqualTo("Admin");
    }

    @Test
    public void fondUser_ifIdNotExist_ReturnErrorMessageWithStatus404(){
        ErrorMessage userResponseDto = testClient
                .get()
                .uri("/api/v1/users/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(userResponseDto).isNotNull();
        Assertions.assertThat(userResponseDto.getStatus()).isEqualTo(404);
    }

    @Test
    public void upDatePassword_ifPasswordValid_ReturnStatus204(){
        testClient
                .patch()
                .uri("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassWordDto("888888","565656","565656"))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    public void upDatePassword_ifIdNotExist_ReturnErrorMessageWithStatus404(){
        ErrorMessage userResponseDto = testClient
                .patch()
                .uri("/api/v1/users/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassWordDto("888888","565656","565656"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(userResponseDto).isNotNull();
        Assertions.assertThat(userResponseDto.getStatus()).isEqualTo(404);
    }

    @Test
    public void upDatePassword_ifInvalidInput_ReturnErrorMessageWithStatus422(){
        ErrorMessage userResponseDto = testClient
                .patch()
                .uri("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassWordDto("","",""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(userResponseDto).isNotNull();
        Assertions.assertThat(userResponseDto.getStatus()).isEqualTo(422);

        userResponseDto = testClient
                .patch()
                .uri("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassWordDto("88888","56565","56565"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(userResponseDto).isNotNull();
        Assertions.assertThat(userResponseDto.getStatus()).isEqualTo(422);

        userResponseDto = testClient
                .patch()
                .uri("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassWordDto("8888888","5656556","5656556"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(userResponseDto).isNotNull();
        Assertions.assertThat(userResponseDto.getStatus()).isEqualTo(422);
    }

    @Test
    public void upDatePassword_ifPasswordInvalid_ReturnErrorMessageWithStatus400() {
        ErrorMessage userResponseDto = testClient
                .patch()
                .uri("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassWordDto("888888", "565656", "000000"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(userResponseDto).isNotNull();
        Assertions.assertThat(userResponseDto.getStatus()).isEqualTo(400);

        userResponseDto = testClient
                .patch()
                .uri("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserPassWordDto("888889", "565656", "565656"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(userResponseDto).isNotNull();
        Assertions.assertThat(userResponseDto.getStatus()).isEqualTo(400);

    }
}
