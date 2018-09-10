package santiago.accesibilidadmovil.dto

data class Paciente(
        val nom_usuario: String,
        val contrasena: String,
        val documento: String,
        val nombre: String,
        val telefono: String,
        val direccion: String,
        val foto: String
) {
    override fun toString(): String {
        return super.toString()
    }
}