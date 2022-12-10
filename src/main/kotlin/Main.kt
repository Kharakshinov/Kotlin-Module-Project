import kotlin.system.exitProcess
import java.util.Scanner

fun main() {
    val archivesList: MutableList<String> = mutableListOf() // Список архивов
    val archivesMap1: MutableMap<String, MutableList<String>?> = mutableMapOf() //Мап архив-список заметок
    val archivesMap2: MutableMap<String, MutableMap<String, String>> = mutableMapOf() //Мап архив-список заметок-текст заметки
    var notesList: MutableList<String> // Список заметок
    var notesMap: MutableMap<String, String> // Мап заметка-текст заметки
    var chosenArchive: String // Выбранный архив
    var chosenNote = "" // Выбранная заметка
    var chosenNumber: Int // Выбранный пункт меню
    var menuNoteList: MutableList<String> //Список для вывода меню заметки

    val menuArchives = MenuArchives() //Меню списка архивов
    val menuNotes = MenuNotes() //Меню списка заметок
    val menuNote = MenuNote() //Меню заметки

    val scanner = Scanner(System.`in`)

    while (true){
       while(true) {
           menuArchives.printMenu("архив", archivesList) // Вывод меню списка архивов
           chosenNumber = menuArchives.chooseNumber("архив", archivesList, chosenNote) // Выбор пункта
           var newArchive = menuArchives.checkDuplicate("архив", archivesList, chosenNumber) // Проверка на дубликаты в списке архивов
           when(chosenNumber) {
               0 -> { // Добавляем новый архив в список архивов
                   archivesList.add(newArchive)
                   archivesMap1[newArchive] = mutableListOf()
               }
               (archivesList.size + 1) -> exitProcess(0) // Выход из приложения
               else -> {
                   chosenArchive = archivesList[chosenNumber-1] // Определяем выбранный архив
                   break // Спускаемся в меню заметок
               }
           }
       }
        while (true) {
            notesList = archivesMap1[chosenArchive]!! // Определяем список заметок исходя из выбранного архива
            notesMap = if(archivesMap2[chosenArchive] == null) {
                mutableMapOf() // Если в архиве нет заметок, то присваиваем пустой мап заметка-текст
            } else {
                archivesMap2[chosenArchive]!! // Если в архиве есть заметки, то присваиваем заполненную мап заметка-текст
            }
            menuNotes.printMenu("заметку", notesList) // Вывод меню списка заметок
            chosenNumber = menuNotes.chooseNumber("заметку", notesList, chosenNote) // Выбор пункта меню
            var newNote = menuNotes.checkDuplicate("заметку", notesList, chosenNumber) // Проверка заметок на дубликаты
            when(chosenNumber) {
                0-> { // Добавляем новую заметку в архив
                    var chosenNotesList = archivesMap1[chosenArchive]
                    chosenNotesList?.add(newNote)
                    archivesMap1[chosenArchive] = chosenNotesList
                }
                (notesList.size + 1) -> break // Выход в меню списка архивов
                else -> {
                    chosenNote = notesList[chosenNumber-1] // Определяем выбранную заметку
                    while (true) {
                        menuNoteList = mutableListOf(chosenNote) // Создаем список из заметки
                        menuNote.printMenuNote(chosenNote) // Вывод меню заметки
                        chosenNumber = menuNote.chooseNumber("выбрать заметку", menuNoteList, chosenNote) // Выбор пункта меню
                        when(chosenNumber) {
                            0 -> { //Присваиваем заметке текст
                                println("\nВведите текст заметки")
                                var notesText = scanner.nextLine()

                                notesMap[chosenNote] = notesText // Связываем имя заметки с текстом
                                archivesMap2[chosenArchive] = notesMap // Связываем выбранный архив с мап заметка-текст
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