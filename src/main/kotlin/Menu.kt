import java.util.Scanner

open class Menu {
    private val scanner = Scanner(System.`in`)

    // Общий метод для печати меню списка архивов и заметок
    fun printMenu(archiveOrNote: String, list: MutableList<String>) {
        println("\nМеню\n0. Создать $archiveOrNote")
        var n = 1
        for (element in list) {
            println("$n. $element")
            n++
        }
        println("${list.size + 1}. Выход\n\nДля выбора введите номер пункта:")
    }

    // Метод для печати меню заметки
    fun printMenuNote (chosenNote: String) {
        println("\nМеню\n0. Изменить текст заметки под названием \"$chosenNote\"\n1. Открыть заметку под названием \"$chosenNote\"\n2. Выход\n\nДля выбора введите номер пункта:")
    }

    // Общий метод для выбора пункта в списке
    fun chooseNumber(archiveOrNote: String, list: MutableList<String>, chosenNote: String): Int {
        while (true) {
            if (scanner.hasNextInt()) { // Проверка типа введенного значения
                var chosenNumber = scanner.nextInt()
                var buffer = scanner.nextLine()
                if (chosenNumber >= 0 && chosenNumber <= list.size + 1) { // Проверка значения введенного числа
                    return chosenNumber
                } else {
                    println("\nОшибка: Вы ввели число, которого нет в списке. Попробуйте еще раз.")
                    when(archiveOrNote) {
                        "выбрать заметку" -> printMenuNote(chosenNote)
                        else -> printMenu(archiveOrNote, list)
                    }
                }
            } else {
                println("\nОшибка: Для выбора пункта введите число, попробуйте еще раз.")
                var buffer = scanner.nextLine()
                when(archiveOrNote) {
                    "выбрать заметку" -> printMenuNote(chosenNote)
                    else -> printMenu(archiveOrNote, list)
                }
            }
        }
    }

    // Общий метод для проверки дубликатов в списках архивов и заметок
    fun checkDuplicate(archiveOrNote: String, list: MutableList<String>, chosenNumber: Int) : String {
        var newElement = ""
        if (chosenNumber == 0) {
            while (true) {
                println("\nКак назовем $archiveOrNote? (только уникальное имя)")
                newElement = scanner.nextLine()

                if (list.contains(newElement)) { // Проверка на дубликат в списке
                    println("\nОшибка: Введите уникальное имя")
                    continue
                } else break
            }
        }
        return newElement
    }
}

