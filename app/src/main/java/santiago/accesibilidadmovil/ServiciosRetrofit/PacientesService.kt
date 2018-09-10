package santiago.accesibilidadmovil.ServiciosRetrofit

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import santiago.accesibilidadmovil.dto.Credenciales
import santiago.accesibilidadmovil.dto.Paciente
import santiago.accesibilidadmovil.dto.RespuestaServidor

interface PacientesService {

    @POST("pacientes/login")
    fun login(@Body cred: Credenciales): Observable<RespuestaServidor<Paciente>>

    @GET("pacientes/getall")
    fun getAll(): Observable<RespuestaServidor<List<Paciente>>>
}