package com.kakaopaysec.aop.exam.service

import com.kakaopaysec.aop.exam.annotation.Trace
import com.kakaopaysec.aop.exam.repository.ExamRepository
import org.springframework.stereotype.Service

@Service
class ExamService(
    private val examRepository: ExamRepository
) {

    @Trace
    fun request(itemId: String) {
        examRepository.save(itemId)
    }
}
