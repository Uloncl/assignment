package uni.ase.assignment.controllers

import org.fxmisc.richtext.CodeArea
import uni.ase.assignment.controllers.CanvasController
import uni.ase.assignment.controllers.LogController

class CodeParser (val ca: CodeArea, val cc: CanvasController, val log: LogController) {
    fun run() {
        log.out("running code");
    }
}