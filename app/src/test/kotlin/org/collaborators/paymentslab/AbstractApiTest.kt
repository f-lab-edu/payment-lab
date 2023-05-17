package org.collaborators.paymentslab

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborator.paymentlab.common.URI_HOST
import org.collaborator.paymentlab.common.URI_PORT
import org.collaborator.paymentlab.common.URI_SCHEME
import org.collaborators.paymentslab.account.domain.Account
import org.collaborators.paymentslab.account.domain.PasswordEncrypt
import org.collaborators.paymentslab.account.domain.TokenGenerator
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = URI_SCHEME, uriHost = URI_HOST, uriPort = URI_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension::class)
@ActiveProfiles("test")
abstract class AbstractApiTest {
    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    protected lateinit var tokenGenerator: TokenGenerator

    @Autowired lateinit var encrypt: PasswordEncrypt

    @Value("\${uri.scheme}")
    protected lateinit var scheme: String

    @Value("\${uri.host}")
    protected lateinit var host: String

    @Value("\${uri.port}")
    protected lateinit var port: String

    protected fun testEntityForRegister(email: String): Account {
        val account = Account.register(
            email,
            encrypt.encode("qqqwww123"), "testName"
        )
        account.completeRegister()
        return account
    }

    protected fun getDocumentRequest(): OperationRequestPreprocessor {
        return Preprocessors.preprocessRequest(
            Preprocessors.modifyUris()
                .scheme(scheme)
                .host(host)
                .port(port.toInt()),
            Preprocessors.prettyPrint()
        )
    }
}