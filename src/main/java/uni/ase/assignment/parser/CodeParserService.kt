package uni.ase.assignment.parser

import javafx.collections.ObservableList
import javafx.collections.ObservableMap
import javafx.concurrent.Service
import javafx.concurrent.Task
import uni.ase.assignment.controllers.CanvasController
import uni.ase.assignment.controllers.LogController

class CodeParserService(
    val codeParser: CodeParser
) : Service<ObservableMap<String, String>>() {
    override fun createTask(): Task<ObservableMap<String, String>> {
        return CodeParserTask(codeParser)
    }
}
