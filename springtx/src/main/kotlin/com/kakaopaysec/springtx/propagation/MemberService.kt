package com.kakaopaysec.springtx.propagation

import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = KotlinLogging.logger {}

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val logRepository: LogRepository
) {

    @Transactional
    fun joinV1(username: String) {
        val member = Member(username)
        val logMessage = Log(username)

        logger.info { "== memberRepository 호출 시작 ==" }
        memberRepository.save(member)
        logger.info { "== memberRepository 호출 종료 ==" }

        logger.info { "== logRepository 호출 시작 ==" }
        logRepository.save(logMessage)
        logger.info { "== logRepository 호출 종료 ==" }
    }

    @Transactional
    fun joinV2(username: String) {
        val member = Member(username)
        val logMessage = Log(username)

        logger.info { "== memberRepository 호출 시작 ==" }
        memberRepository.save(member)
        logger.info { "== memberRepository 호출 종료 ==" }

        logger.info { "== logRepository 호출 시작 ==" }

        try {
            logRepository.save(logMessage)
        } catch (e: RuntimeException) {
            logger.info { "log 저장에 실패했습니다. logMessage = ${logMessage.message}" }
            logger.info { "로그 정상흐름 반환" }
        }

        logger.info { "== logRepository 호출 종료 ==" }
    }
}
