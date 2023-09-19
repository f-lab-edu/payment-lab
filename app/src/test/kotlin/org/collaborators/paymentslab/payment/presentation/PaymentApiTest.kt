package org.collaborators.paymentslab.payment.presentation

import org.collaborator.paymentlab.common.Role
import org.collaborator.paymentlab.common.V1_API_TOSS_PAYMENTS
import org.collaborators.paymentslab.AbstractApiTest
import org.collaborators.paymentslab.account.domain.AccountRepository
import org.collaborators.paymentslab.payment.domain.entity.PaymentHistory
import org.collaborators.paymentslab.payment.domain.entity.PaymentOrder
import org.collaborators.paymentslab.payment.domain.entity.PaymentsStatus
import org.collaborators.paymentslab.payment.domain.repository.PaymentHistoryRepository
import org.collaborators.paymentslab.payment.domain.repository.PaymentOrderRepository
import org.collaborators.paymentslab.payment.presentation.mock.MockPayments
import org.collaborators.paymentslab.payment.presentation.request.PaymentOrderRequest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDateTime

class PaymentApiTest @Autowired constructor(
    private val accountRepository: AccountRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository,
    private val paymentOrderRepository: PaymentOrderRepository
) : AbstractApiTest() {

    @Test
    @DisplayName("카드결제 api 동작")
    fun keyIn() {
        val account = testEntityForRegister("keyInTest@gmail.com")
        accountRepository.save(account)

        val requestDto = MockPayments.testTossPaymentsRequest

        val paymentOrder = PaymentOrder.newInstance(
            account.id!!,
            requestDto.orderName,
            requestDto.amount
        )
        paymentOrderRepository.save(paymentOrder)

        val reqBody = this.objectMapper.writeValueAsString(requestDto)
        val tokens = tokenGenerator.generate(account.email, setOf(Role.USER))

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("$V1_API_TOSS_PAYMENTS/key-in/{paymentOrderId}", paymentOrder.id)
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
                    keyInRequestFieldsSnippet()
                )
            )
    }

    @Test
    @DisplayName("카드번호 입력에 숫자가 아닌 값이 입력되거나 총 16자가 아니면 에러가 발생한다.")
    fun cardNumErrorKeyIn() {
        val account = testEntityForRegister("keyInCardNumTest@gmail.com")
        accountRepository.save(account)

        val requestDto = MockPayments.invalidCardNumberTestTossPaymentsRequest

        val paymentOrder = PaymentOrder.newInstance(
            account.id!!,
            requestDto.orderName,
            requestDto.amount
        )
        paymentOrderRepository.save(paymentOrder)

        val reqBody = this.objectMapper.writeValueAsString(requestDto)
        val tokens = tokenGenerator.generate(account.email, setOf(Role.USER))

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("$V1_API_TOSS_PAYMENTS/key-in/{paymentOrderId}", paymentOrder.id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
                .header("Authorization", "Bearer ${tokens.accessToken}")
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    keyInRequestFieldsSnippet(),
                    errorResponseFieldsSnippet()
                )
            )
    }

    @Test
    @DisplayName("주문 결제 정보가 존재하지 않는 주문 결제로 변조된 경우 결제 승인이 실패한다.")
    fun paymentError() {
        val account = testEntityForRegister("keyInWrongPaymentOrderTest@gmail.com")
        accountRepository.save(account)

        val requestDto = MockPayments.invalidCardNumberTestTossPaymentsRequest

        val paymentOrder = PaymentOrder.newInstance(
            account.id!!,
            requestDto.orderName,
            requestDto.amount
        )
        paymentOrderRepository.save(paymentOrder)

        val reqBody = this.objectMapper.writeValueAsString(requestDto)
        val tokens = tokenGenerator.generate(account.email, setOf(Role.USER))

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("$V1_API_TOSS_PAYMENTS/key-in/{paymentOrderId}", 99L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
                .header("Authorization", "Bearer ${tokens.accessToken}")
        ).andExpect(MockMvcResultMatchers.status().isNotFound)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    keyInRequestFieldsSnippet(),
                    errorResponseFieldsSnippet()
                )
            )
    }

    @Test
    @DisplayName("주문 결제 정보의 소유자가 아닌 다른 사용자 계정으로 결제 승인을 요청할 경우 해당 결제 요청은 실패한다.")
    fun paymentWrongUserError() {
        val account = testEntityForRegister("keyInWrongUserTest@gmail.com")
        val wrongAccount = testEntityForRegister("wrongUser123@gmail.com")
        accountRepository.save(account)
        accountRepository.save(wrongAccount)

        val requestDto = MockPayments.invalidCardNumberTestTossPaymentsRequest

        val paymentOrder = PaymentOrder.newInstance(
            account.id!!,
            requestDto.orderName,
            requestDto.amount
        )
        paymentOrderRepository.save(paymentOrder)

        val reqBody = this.objectMapper.writeValueAsString(requestDto)
        val wrongToken = tokenGenerator.generate(wrongAccount.email, setOf(Role.USER))

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("$V1_API_TOSS_PAYMENTS/key-in/{paymentOrderId}", paymentOrder.id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
                .header("Authorization", "Bearer ${wrongToken.accessToken}")
        ).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    keyInRequestFieldsSnippet(),
                    errorResponseFieldsSnippet()
                )
            )
    }

    @Test
    @DisplayName("이미 결제 진행이 불가능한 주문 결제를 승인 요청할 경우 해당 요청은 실패한다.")
    fun paymentAlreadyDonePaymentOrderError() {
        val account = testEntityForRegister("keyInInvalidStatusTest@gmail.com")
        accountRepository.save(account)

        val requestDto = MockPayments.invalidCardNumberTestTossPaymentsRequest

        val paymentOrder = PaymentOrder.newInstance(
            account.id!!,
            requestDto.orderName,
            requestDto.amount,
            PaymentsStatus.CANCELED
        )
        paymentOrderRepository.save(paymentOrder)

        val reqBody = this.objectMapper.writeValueAsString(requestDto)
        val wrongToken = tokenGenerator.generate(account.email, setOf(Role.USER))

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("$V1_API_TOSS_PAYMENTS/key-in/{paymentOrderId}", paymentOrder.id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
                .header("Authorization", "Bearer ${wrongToken.accessToken}")
        ).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    keyInRequestFieldsSnippet(),
                    errorResponseFieldsSnippet()
                )
            )
    }

    @Test
    @DisplayName("변조된 결제요청 정보로 결제 승인 요청할 경우 해당 요청은 실패한다.")
    fun paymentInvalidPaymentOrderError() {
        val account = testEntityForRegister("keyInInvalidPaymentOrderTest@gmail.com")
        accountRepository.save(account)

        val wrongRequestDto = MockPayments.invalidCardNumberTestTossPaymentsRequest

        val paymentOrder = PaymentOrder.newInstance(
            account.id!!,
            "originOrderName",
            wrongRequestDto.amount
        )
        paymentOrderRepository.save(paymentOrder)

        val reqBody = this.objectMapper.writeValueAsString(wrongRequestDto)
        val wrongToken = tokenGenerator.generate(account.email, setOf(Role.USER))

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("$V1_API_TOSS_PAYMENTS/key-in/{paymentOrderId}", paymentOrder.id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
                .header("Authorization", "Bearer ${wrongToken.accessToken}")
        ).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    keyInRequestFieldsSnippet(),
                    errorResponseFieldsSnippet()
                )
            )
    }



    @Test
    @DisplayName("사용자 계정별 카드결제 이력 조회 api")
    fun readHistoriesTest() {
        val account = testEntityForRegister("readHistoriesTest@gmail.com")
        val entity = accountRepository.save(account)

        val tokens = tokenGenerator.generate(account.email, setOf(Role.USER))

        paymentHistoryRepository.save(
            PaymentHistory.newInstanceFrom(
            entity.id!!,
            LocalDateTime.now(),
            "ord_202306172137299642490491",
            "테스트결제",
            10000,
            "testPaymentKey",
            "DONE"
        ))

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .get(V1_API_TOSS_PAYMENTS)
                .param("pageNum", "0")
                .param("pageSize", "6")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer ${tokens.accessToken}")
        ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                    PayloadDocumentation.responseFields(
                        PayloadDocumentation.fieldWithPath("isSuccess")
                            .type(JsonFieldType.BOOLEAN)
                            .description("api 성공여부"),
                        PayloadDocumentation.fieldWithPath("body[].orderId")
                            .type(JsonFieldType.STRING)
                            .description("결제 주문 번호"),
                        PayloadDocumentation.fieldWithPath("body[].orderName")
                            .type(JsonFieldType.STRING)
                            .description("결제 주문 명"),
                        PayloadDocumentation.fieldWithPath("body[].amount")
                            .type(JsonFieldType.NUMBER)
                            .description("결제 금액"),
                        PayloadDocumentation.fieldWithPath("body[].status")
                            .type(JsonFieldType.STRING)
                            .description("결제 상태"),
                        PayloadDocumentation.fieldWithPath("body[].approvedAt")
                            .type(JsonFieldType.STRING)
                            .description("결제 승인 일자")
                    )
                )
            )
    }

    @Test
    @DisplayName("주문 결제 발행 api 테스트")
    fun testGeneratePaymentOrder() {
        val account = testEntityForRegister("generatePaymentOrder@gmail.com")
        val entity = accountRepository.save(account)

        val tokens = tokenGenerator.generate(account.email, setOf(Role.USER))
        val reqBody = objectMapper.writeValueAsString(PaymentOrderRequest(entity.id!!, "테스트 주문상품", 10))

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("$V1_API_TOSS_PAYMENTS/payment-order")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
                .header("Authorization", "Bearer ${tokens.accessToken}")
        ).andExpect(MockMvcResultMatchers.status().isSeeOther)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("accountId")
                            .type(JsonFieldType.NUMBER)
                            .description("결제 주문을 이용하고자 하는 사용자의 id 번호"),
                        PayloadDocumentation.fieldWithPath("orderName")
                            .type(JsonFieldType.STRING)
                            .description("결제 주문을 이용하고자 하는 사용자의 주문상품명"),
                        PayloadDocumentation.fieldWithPath("amount")
                            .type(JsonFieldType.NUMBER)
                            .description("결제 주문을 이용하고자 하는 사용자의 주문상품의 수량")
                    )
                )
            )
    }

    // 임시로 비활성화
    @Test
    @Disabled
    @DisplayName("변조된 사용자 계정 id로 주문 결제 발행 api 테스트")
    fun testWithInvalidAccountGeneratePaymentOrder() {
        val account = testEntityForRegister("originalGeneratedPaymentOrder@gmail.com")
        val originalUser = accountRepository.save(account)
        val invalidAccountId = 9999L
        val tokens = tokenGenerator.generate(account.email, setOf(Role.USER))
        val invalidPaymentOrderRequest = PaymentOrderRequest(invalidAccountId, "잘못된 테스트 주문상품", 10)
        val reqBody = objectMapper.writeValueAsString(invalidPaymentOrderRequest)

        this.mockMvc.perform(
            RestDocumentationRequestBuilders
                .post("$V1_API_TOSS_PAYMENTS/payment-order")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqBody)
                .header("Authorization", "Bearer ${tokens.accessToken}")
        ).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andDo(
                MockMvcRestDocumentation.document(
                    "{class-name}/{method-name}",
                    getDocumentRequest(),
                    PayloadDocumentation.requestFields(
                        PayloadDocumentation.fieldWithPath("accountId")
                            .type(JsonFieldType.NUMBER)
                            .description("결제 주문을 이용하고자 하는 사용자의 id 번호"),
                        PayloadDocumentation.fieldWithPath("orderName")
                            .type(JsonFieldType.STRING)
                            .description("결제 주문을 이용하고자 하는 사용자의 주문상품명"),
                        PayloadDocumentation.fieldWithPath("amount")
                            .type(JsonFieldType.NUMBER)
                            .description("결제 주문을 이용하고자 하는 사용자의 주문상품의 수량")
                    ),
                    errorResponseFieldsSnippet()
                )
            )
    }

    private fun keyInRequestFieldsSnippet(): RequestFieldsSnippet? =
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
}