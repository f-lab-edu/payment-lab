package org.collaborators.paymentslab.account.presentation

import org.collaborators.paymentslab.AbstractApiTest
import org.collaborators.paymentslab.account.presentation.request.RegisterAccountRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthenticationApiTest: AbstractApiTest() {
    @Test
    @DisplayName("회원가입 api 동작")
    fun registerTest() {
        val requestDto = RegisterAccountRequest("hello@gmail.com", "qwer1234", "helloUsername")
        val reqBody = this.objectMapper.writeValueAsString(requestDto)

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
        ).andExpect(status().is2xxSuccessful)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("email")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 email 주소"),
                        PayloadDocumentation.fieldWithPath("password")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 password.\n " +
                                    "- 문자의 종류 2가지 이하일 경우 최소 10자 이상 50자 이하\n" +
                                    "- 문자의 종류 3가지 이상일 경우 최소 8자 이상 50자 이하"),
                        PayloadDocumentation.fieldWithPath("username")
                            .type(JsonFieldType.STRING)
                            .description("회원 등록을 하고 싶은 username")
                    )
                )
            )
    }
}