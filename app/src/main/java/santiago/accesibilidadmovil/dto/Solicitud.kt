package santiago.accesibilidadmovil.dto

data class Solicitud(
        val id: Int?,
        val paciente_id: String,
        val categoria_id: Int,
        val clasificacion_id: Int,
        val descripcion: String,
        val coordLat: Double?,
        val coordLong: Double?
)