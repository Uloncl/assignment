package uni.ase.assignment.services

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.concurrent.Service
import javafx.concurrent.Task

class LogControllerService () : Service<String>() {
    var newText : StringProperty = SimpleStringProperty("")
    fun setNewText(text : String) {
        println("newtext: $text")
        newText = SimpleStringProperty(text)
    }
    fun getNewText() : String {
        return newText.get()
    }
    fun getNewTextProperty() : StringProperty {
        return newText
    }
    override fun createTask(): Task<String> {
        return object : Task<String>() {
            var newText : String = getNewText()
            @Throws(Exception::class)
            override fun call(): String {
                this.updateValue("${this.valueProperty()}$newText")
                println("hi")
                return this.value
            }
        }
    }
}
