package org.collaborators.paymentslab.payment.presentation

import org.collaborator.paymentlab.common.Role
import org.collaborators.paymentslab.AbstractApiTest
import org.collaborators.paymentslab.account.domain.AccountRepository
import org.collaborators.paymentslab.payment.application.PaymentService
import org.collaborators.paymentslab.payment.domain.TossPaymentsProcessor
import org.collaborators.paymentslab.payment.presentation.mock.MockPayments
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class PaymentApiTest @Autowired constructor(
    private val accountRepository: AccountRepository) : AbstractApiTest() {

    @Mock
    private lateinit var paymentService: PaymentService

    @Test
    @DisplayName("카드결제 api 동작")
    fun keyIn() {
        val account = testEntityForRegister("keyInTest@gmail.com")
        accountRepository.save(account)

        val requestDto = MockPayments.testTossPaymentsRequest
        val reqBody = this.objectMapper.writeValueAsString(requestDto)
        val tokens = tokenGenerator.generate(account.email, setOf(Role.USER))


        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("/api/v1/toss-payments/key-in")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
                .header("Authorization", "Bearer ${tokens.accessToken}")
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("amount")
                            .type(JsonFieldType.NUMBER)
                            .description("주문 상품의 가격"),
                        PayloadDocumentation.fieldWithPath("orderName")
                            .type(JsonFieldType.STRING)
                            .description("주문 상품 이름"),
                        PayloadDocumentation.fieldWithPath("cardNumber")
                            .type(JsonFieldType.STRING)
                            .description("결제 카드의 카드번호"),
                        PayloadDocumentation.fieldWithPath("cardExpirationYear")
                            .type(JsonFieldType.STRING)
                            .description("결제 카드의 만료 년도"),
                        PayloadDocumentation.fieldWithPath("cardExpirationMonth")
                            .type(JsonFieldType.STRING)
                            .description("결제 카드의 만료 월"),
                        PayloadDocumentation.fieldWithPath("cardPassword")
                            .type(JsonFieldType.STRING)
                            .description("결제 카드 비밀번호"),
                        PayloadDocumentation.fieldWithPath("customerIdentityNumber")
                            .type(JsonFieldType.STRING)
                            .description("카드 소유자의 주민등록번호 앞자리 6")
                    )
                )
            )
    }
}