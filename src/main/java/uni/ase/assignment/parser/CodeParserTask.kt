package uni.ase.assignment.parser

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.ObservableMap
import javafx.concurrent.Task
import uni.ase.assignment.controllers.CanvasController
import uni.ase.assignment.controllers.LogController

class CodeParserTask(
    val codeParser: CodeParser
) : Task<ObservableMap<String, String>>() {
    override fun call() : ObservableMap<String, String> {
        this.updateTitle("running code parser")
        var map : ObservableMap<String, String> = FXCollections.observableMap(mapOf())
        codeParser.run(this)
        return FXCollections.observableMap(mapOf())
    }
}
