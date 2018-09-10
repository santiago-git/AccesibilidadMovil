package santiago.accesibilidadmovil.vistas.servicios

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import santiago.accesibilidadmovil.dto.Paciente

class SessionService {
    fun guardarSesion(paciente: Paciente, context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val prefsEditor = prefs.edit()
        val json = Gson().toJson(paciente) //convierte a .json el objeto
        prefsEditor.putString("pacienteInfo", json)
        prefsEditor.commit()
        println(json)
    }

    fun obtenerSesion(context: Context): Paciente? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val json = prefs.getString("pacienteInfo", "")

        val p = Gson().fromJson<Paciente>(json, Paciente::class.java)

        return p

    }

    fun eliminarSesion(context: Context){
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().remove("pacienteInfo").commit() //elimina la clave
    }

}