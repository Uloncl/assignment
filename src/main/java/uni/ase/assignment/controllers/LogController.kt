package uni.ase.assignment.controllers

import org.fxmisc.richtext.CodeArea

class LogController (val log: CodeArea) {
    fun error(text: String) {
        log.appendText("ERROR: $text\n")
    }
    fun out(text: String) {
        log.appendText("$text\n")
    }
}