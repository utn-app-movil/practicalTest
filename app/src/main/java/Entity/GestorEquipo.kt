package Entity

class GestorEquipo {
    private val listaEquipos = mutableListOf<Equipo>()
    private var nextId = 1

    fun agregarEquipo(nombre: String, descripcion: String, precio: Double, disponible: Boolean) {
        val nuevoEquipo = Equipo(nextId++, nombre, descripcion, precio, disponible)
        listaEquipos.add(nuevoEquipo)
    }

    fun obtenerEquipos(): List<Equipo> {
        return listaEquipos
    }

    fun actualizarEquipo(id: Int, nombre: String, descripcion: String, precio: Double, disponible: Boolean): Boolean {
        val equipo = listaEquipos.find { it.id == id }
        equipo?.let {
            it.nombre = nombre
            it.descripcion = descripcion
            it.precio = precio
            it.disponible = disponible
            return true
        }
        return false
    }

    fun eliminarEquipo(id: Int): Boolean {
        return listaEquipos.removeIf { it.id == id }
    }

}