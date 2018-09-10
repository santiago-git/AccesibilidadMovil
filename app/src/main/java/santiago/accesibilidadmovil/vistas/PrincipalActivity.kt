package santiago.accesibilidadmovil.vistas

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_principal.*
import santiago.accesibilidadmovil.R
import santiago.accesibilidadmovil.dto.Solicitud
import santiago.accesibilidadmovil.vistas.servicios.MainService
import santiago.accesibilidadmovil.vistas.servicios.SessionService
import android.location.LocationManager
import android.content.DialogInterface
import android.location.Location
import android.location.LocationListener
import android.provider.Settings
import android.support.v7.app.AlertDialog
import santiago.accesibilidadmovil.dto.Ubicacion

class PrincipalActivity : AppCompatActivity() {

    private val _mainServ: MainService = MainService()
    lateinit var locationManager: LocationManager
    private var ubicacion: Ubicacion? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        setSupportActionBar(toolbar)
        ontenerUbicacion()

        fab.setOnClickListener { view ->
            crearSolicitud()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.cerrar_sesion -> {
                SessionService().eliminarSesion(applicationContext)
                val i = Intent(baseContext, LoginActivity::class.java)
                startActivity(i)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun obtenerSolicitides() {
        _mainServ.obtenerPacientes()
                .subscribe(
                        { res ->
                            Log.w("Respuesta", res.satisfactorio.toString());
                        },
                        { err ->
                            Log.w("Respuesta", err.toString());
                        }
                )
    }

    private fun crearSolicitud() {
        val sesionPaciente = SessionService().obtenerSesion(applicationContext)
        var solicitud: Solicitud = Solicitud(
                id = null,
                categoria_id = 1,
                clasificacion_id = 1,
                paciente_id = sesionPaciente!!.nom_usuario,
                descripcion = "Envio desde KOTLIN2",
                coordLat = ubicacion?.latitud,
                coordLong = ubicacion?.longitud
        )

        _mainServ.crarSolicitud(solicitud)
                .subscribe(
                        { res ->
                            if (res.satisfactorio) {
                                snackBarShow("Se ha enviado la solicitud con éxito")
                            } else {
                                snackBarShow("No se ha podido enviar la solicitud")
                            }
                        },
                        { err ->
                            snackBarShow(err.localizedMessage)
                        }
                )
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled())
            showAlert()
        return isLocationEnabled()
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " + "usa esta app")
                .setPositiveButton("Configuración de ubicación", DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                    val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(myIntent)
                })
                .setNegativeButton("Cancelar", DialogInterface.OnClickListener { paramDialogInterface, paramInt -> })
        dialog.show()
    }

    private fun isLocationEnabled(): Boolean {
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    fun ontenerUbicacion(){
        locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(checkLocation()) {
            /*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0F, object : LocationListener {
                override fun onLocationChanged(location: Location?) {
                    if (location != null) {
                        Log.d("CodeAndroidLocation", " GPS Latitude : " + location!!.latitude)
                        Log.d("CodeAndroidLocation", " GPS Longitude : " + location!!.longitude)
                        snackBarShow(location!!.latitude.toString())
                    }
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                }

                override fun onProviderEnabled(p0: String?) {
                }

                override fun onProviderDisabled(p0: String?) {
                }

            })*/
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, object: LocationListener {
                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                }

                override fun onProviderEnabled(p0: String?) {
                }

                override fun onProviderDisabled(p0: String?) {
                }

                override fun onLocationChanged(location: Location?) {
                    if (location != null) {
                        ubicacion = Ubicacion(
                                latitud = location!!.latitude,
                                longitud = location!!.longitude
                        )

                    }
                }
            }, null)


        }

    }

    fun snackBarShow(str: String){
        Snackbar.make(findViewById(android.R.id.content),str, Snackbar.LENGTH_LONG)
                .setAction("Acción", null).show()
    }


}