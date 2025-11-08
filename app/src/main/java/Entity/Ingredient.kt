package Entity

class Ingredient( //constructor con parametros
    var name: String = "",
    var amount: Double = 0.0,
    var unit: String = "", // "gramos", "tazas", "cucharadas", "unidades"
    var state: String = "", // "picado", "rallado", "cocido", "crudo"
    var isOptional: Boolean = false,
    var notes: String = ""
) {
    // Constructor vacío


   //obtener una descripcion del ingrediente
    fun getDescription(): String {
        val optionalText = if (isOptional) " (opcional)" else ""
        return "$amount $unit de $name$optionalText"
    }
}