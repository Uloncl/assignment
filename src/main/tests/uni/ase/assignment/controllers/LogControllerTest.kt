package uni.ase.assignment.controllers

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.platform.commons.util.StringUtils
import kotlin.test.assertSame

internal class LogControllerTest (val log: LogController) {

    @Test
    fun testError() : Boolean {
        log.error("this is an error")
        return log.log.text.contains(Regex("ERROR: this is an error"))
    }

    @Test
    fun testOut() : Boolean {
        log.out("this is a log message")
        return log.log.text.contains(Regex("ERROR: this is an error\nthis is a log message"))
    }

    @Test
    fun testClear() : Boolean {
        log.clear()
        return StringUtils.isBlank(log.log.text)
    }
}