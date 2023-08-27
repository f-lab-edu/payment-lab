package org.collaborators.paymentslab.log.infrastructure

import org.collaborators.paymentslab.log.domain.EventResultRecorder
import org.collaborators.paymentslab.log.domain.PaymentResultEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value

class PaymentEventResultSyncRecorder: EventResultRecorder<PaymentResultEvent> {

    private val logger = LoggerFactory.getLogger("payment")

    @Value("\${event.record.dir}")
    private lateinit var recordDir: String

    override fun record(event: PaymentResultEvent) {
        // TODO 일자별로 로그파일이 저장되는 로직(동시성 문제 있는지 확인하고) 구현

        // 파일 생성에 필요한 리소스 획득
        // recordDir에 해당하는 디렉토리가 있는지 확인하고 없으면 생성
        // 이벤트가 발행된 날짜(occurredOn)을 확인하여 yyyy-mm-dd에 해당하는 텍스트 파일이 없으면 해당 파일 생성
        // 해당 파일에 내용 입력
        // 입력 완료된 파일 저장
        // 획득한 리소스 반납

        logger.info("payment occurred on {} recorded", event.occurredOn())
    }
}