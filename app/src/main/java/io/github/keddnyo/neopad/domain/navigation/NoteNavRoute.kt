package io.github.keddnyo.neopad.domain.navigation

sealed class NoteNavRoute(val route: String) {
    object MainScreen : NoteNavRoute(route = "main_screen")
    object NoteDetail : NoteNavRoute(route = "note_detail")
}