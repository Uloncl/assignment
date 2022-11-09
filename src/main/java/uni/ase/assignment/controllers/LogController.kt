package uni.ase.assignment.controllers

import javafx.scene.control.TextArea


class LogController (val log: TextArea) {
    fun error(text: String) {
        log.appendText("ERROR: $text\n")
    }
    fun out(text: String) {
        log.appendText("$text\n")
    }
    fun clear() {
        log.clear()
    }
}