package santiago.accesibilidadmovil.ServiciosRetrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class InstanciaRetrofit {

    //private val urlServidor = "http://192.168.0.26:3000/"
    private val urlServidor = "http://192.168.0.26:3000/"

    private var retrofit: Retrofit? = null;

    private fun instanciaRetrofit(): Retrofit {
        if (retrofit === null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(urlServidor)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
        return retrofit!!
    }

    fun PacienteService(): PacientesService {
        return instanciaRetrofit().create<PacientesService>(PacientesService::class.java)
    }

    fun SolicitudService(): SolicitudService {
        return instanciaRetrofit().create<SolicitudService>(SolicitudService::class.java)
    }

}