package com.sharkmind.practicasotter.samples.anfibios.ui

import androidx.compose.runtime.Composable

@Composable
fun Anfibios() {

    /*
      Para probar 'Anfibios' en el emulador se necesita realizar los siguientes pasos:
      - En AndroidManifest.xml insertar el permiso de Internet:
          <uses-permission android:name="android.permission.INTERNET" />
      - También agregar 'AnfibioApp.kt' en <application:
          android:name=".samples.anfibios.AnfibioApp"
      - Usar 'AnfibioApp.kt' como punto de entrada a la aplicación.
      - Añadir la propiedad android:usesCleartextTraffic="true" en la etiqueta <application>.
        Esto es necesario porque la URL de la API que estás usando (http://android-kotlin-
        fun-mars-server.appspot.com/) no es segura (utiliza http en lugar de https), y las
        versiones recientes de Android bloquean este tipo de tráfico por defecto.
    */

    AnfibioHome()
}