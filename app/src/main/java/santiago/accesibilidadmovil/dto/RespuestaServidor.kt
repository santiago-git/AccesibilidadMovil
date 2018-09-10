package santiago.accesibilidadmovil.dto

data class RespuestaServidor<T>(
        val satisfactorio: Boolean,
        val resultado: T,
        val mensaje: String
)