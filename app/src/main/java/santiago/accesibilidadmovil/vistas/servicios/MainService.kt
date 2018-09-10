package santiago.accesibilidadmovil.vistas.servicios

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import santiago.accesibilidadmovil.ServiciosRetrofit.InstanciaRetrofit
import santiago.accesibilidadmovil.dto.Credenciales
import santiago.accesibilidadmovil.dto.Paciente
import santiago.accesibilidadmovil.dto.RespuestaServidor
import santiago.accesibilidadmovil.dto.Solicitud

class MainService {

    /*
    Aqui se realizan todas la peticiones al servidor
     */

    private val instRetrofit: InstanciaRetrofit = InstanciaRetrofit()

    fun crarSolicitud(solicitud: Solicitud): Observable<RespuestaServidor<Boolean>> {
        return instRetrofit.SolicitudService().crearSolicitud(solicitud)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(
                        {
                            if (!it.satisfactorio)
                                throw (Exception(it.mensaje))
                        }
                )
    }

    fun obtenerPacientes(): Observable<RespuestaServidor<List<Paciente>>> {
        return instRetrofit.PacienteService().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun login(cred: Credenciales): Observable<RespuestaServidor<Paciente>> {
        return instRetrofit.PacienteService().login(cred)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(
                        {
                            if (!it.satisfactorio)
                                throw (Exception(it.mensaje))
                        }
                )
    }

}