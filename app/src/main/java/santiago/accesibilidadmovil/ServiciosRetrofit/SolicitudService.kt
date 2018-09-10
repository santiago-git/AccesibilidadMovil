package santiago.accesibilidadmovil.ServiciosRetrofit

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import santiago.accesibilidadmovil.dto.RespuestaServidor
import santiago.accesibilidadmovil.dto.Solicitud

interface SolicitudService {

    @GET("solicitudes/getall")
    fun getAll(): Observable<RespuestaServidor<List<Solicitud>>>

    @POST("solicitudes/crear")
    fun crearSolicitud(@Body solicitud: Solicitud): Observable<RespuestaServidor<Boolean>>
}