package org.collaborators.paymentslab.account.presentation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.util.StringUtils

class PasswordValidator: ConstraintValidator<Password, String> {
    private var min = 0
    private var max = 0

    override fun initialize(constraintAnnotation: Password) {
        this.min = constraintAnnotation.min
        this.max = constraintAnnotation.max
    }

    override fun isValid(password: String, context: ConstraintValidatorContext): Boolean {
        if (!StringUtils.hasText(password))
            return sendViolation(context, "비밀번호를 입력해주세요.")
        if (password.length < min || password.length > max)
            return sendViolation(context, "비밀번호 입력 길이 규칙을 지켜주세요. 비밀번호 길이는 최소 ${this.min}에서 ${this.max}" +
                    "까지만 입력해주세요.")
        return isValid(password)
    }

    private fun isValid(password: String): Boolean {
        val index = HashSet<Char>()
        val chars = password.toCharArray()
        for (ch in chars) {
            index.add(ch)
        }
        if (index.size >= 3)
            return password.length >= 8
        return password.length >= 10
    }

    private fun sendViolation(context: ConstraintValidatorContext, message: String): Boolean {
        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(message)
            .addConstraintViolation()
        return false
    }
}