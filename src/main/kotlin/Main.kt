import java.util.Scanner
import kotlin.system.exitProcess

fun main() {
    val archivesMap: MutableMap<String, MutableMap<String, String>> = mutableMapOf() //Мап архив-список заметок-текст заметки
    var notesMap: MutableMap<String, String> // Мап заметка-текст заметки
    var chosenNote = "" // Выбранная заметка
    var chosenArchive: String
    var menuNoteList: MutableSet<String> //Список для вывода меню заметки

    val menu = Menu()

    val scanner = Scanner(System.`in`)

    while (true){
        while(true) {
            val (newArchive, chosenNumber) = menu.actionsMenuArchivesNotes(menu, "архив", archivesMap.keys, chosenNote)
            when(chosenNumber) {
                0 -> { // Добавляем новый архив в список архивов
                    archivesMap[newArchive] = mutableMapOf()
                }
                (archivesMap.keys.size + 1) -> exitProcess(0) // Выход из приложения
                else -> {
                    chosenArchive = (archivesMap.keys).elementAt(chosenNumber-1) // Определяем выбранный архив
                    break
                }
            }
        }
        while (true) {
            notesMap = if(archivesMap[chosenArchive] == null) {
                mutableMapOf() // Если в архиве нет заметок, то присваиваем пустой мап заметка-текст
            } else {
                archivesMap[chosenArchive]!! // Если в архиве есть заметки, то присваиваем заполненную мап заметка-текст
            }
            var (newNote, chosenNumber) = menu.actionsMenuArchivesNotes(menu, "заметку", archivesMap[chosenArchive]!!.keys, chosenNote)
            when(chosenNumber) {
                0-> { // Добавляем новую заметку в архив
                    archivesMap[chosenArchive]?.set(newNote, "*Пустая заметка*")
                }
                (archivesMap[chosenArchive]!!.keys.size + 1) -> break // Выход в меню списка архивов
                else -> {
                    chosenNote = archivesMap[chosenArchive]!!.keys.elementAt(chosenNumber-1) // Определяем выбранную заметку
                    while (true) {
                        menuNoteList = mutableSetOf(chosenNote) // Создаем список из заметки
                        menu.printMenuNote(chosenNote) // Вывод меню заметки
                        chosenNumber = menu.chooseNumber("выбрать заметку", menuNoteList, chosenNote) // Выбор пункта меню
                        when(chosenNumber) {
                            0 -> { //Присваиваем заметке текст
                                println("\nВведите текст заметки")
                                val notesText = scanner.nextLine()

                                notesMap[chosenNote] = notesText // Связываем имя заметки с текстом
                                archivesMap[chosenArchive] = notesMap // Связываем выбранный архив с мап заметка-текст
                            }
                            1 -> println("\nВывожу текст заметки:\n"+ notesMap[chosenNote]) //Выводим текст заметки на экран
                            (menuNoteList.size + 1) -> break // Возвращаемся в меню списка заметок
                        }
                    }
                }
            }
        }
    }
 }

