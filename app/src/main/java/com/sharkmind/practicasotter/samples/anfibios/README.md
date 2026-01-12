# Documentación del Proyecto: Anfibios

## 1. Resumen General
Anfibios es una aplicación de Android moderna construida 100% con Kotlin y Jetpack Compose. Su propósito es demostrar las mejores prácticas de desarrollo de Android, incluyendo una arquitectura limpia, inyección de dependencias y el consumo de una API REST para mostrar una lista de anfibios con sus imágenes y descripciones.

La aplicación obtiene los datos de un endpoint público, maneja los estados de la UI (Cargando, Éxito, Error) y presenta la información en una lista vertical con tarjetas interactivas.

## 2. Capturas

<img src="/img/anfibios.png" alt="Captura_01" height="400">

## 3. Arquitectura
* El proyecto sigue una arquitectura MVVM (Model-View-ViewModel), con principios de Arquitectura Limpia para separar las responsabilidades de cada capa.

* UI Layer (View): Compuesta por funciones de Jetpack Compose (AnfibioHome.kt) que observan un estado (UiState) y reaccionan para pintar la interfaz.

* ViewModel (AnfibioVM.kt): Actúa como intermediario entre la UI y la capa de datos. Solicita los datos al repositorio, los convierte en un estado de UI (AnfibioUiState) y lo expone para que la UI lo consuma.

* Data Layer (AnfibioRepository.kt): Abstrae el origen de los datos. Proporciona una API limpia para que el ViewModel acceda a los datos.

* Network Layer (AnfibioService.kt): Se encarga de la comunicación con la API externa utilizando Retrofit.

## 4. Tecnologías y Librerías Clave
* Jetpack Compose: Interfaz de usuario declarativa.

* Hilt: Inyección de dependencias.

* Retrofit: Cliente HTTP para API REST.

* Kotlinx Serialization: Parseo de JSON a objetos Kotlin.

* Coil 3: Carga asíncrona de imágenes.

* Coroutines: Gestión de tareas asíncronas y concurrencia.

* Lifecycle ViewModel: Persistencia de estado ante cambios de configuración.

## 5. Desglose de Componentes

### 4.1. Capa de Red (Network)

`AnfibioData.kt`

Define el modelo de datos que se mapea desde el JSON.

```
@Serializable
data class AnfibioData(
    val name: String,
    val type: String,
    val description: String,
    @SerialName("img_src")
    val imgSrc: String
)
```

`AnfibioService.kt`

Definición de endpoints.

```
interface AnfibioService {
    @GET("amphibians")
    suspend fun getAnfibio(): List<AnfibioData>
}
```

`NetworkModule.kt`

Módulo de Hilt para proveer dependencias singleton.

```
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideAnfibioService(retrofit: Retrofit): AnfibioService =
        retrofit.create(AnfibioService::class.java)
}
```

### 4.2. Capa de Datos (Data)

`AnfibioRepository.kt`

```
interface AnfibioRepository {
    suspend fun getAnfibios(): List<AnfibioData>
}

class NetworkAnfibioRepository(
    private val anfibioApiService: AnfibioService
): AnfibioRepository {
    override suspend fun getAnfibios(): List<AnfibioData> = anfibioApiService.getAnfibio()
}
```

### 4.3. Capa de UI (ViewModel y View)

`AnfibioVM.kt`

```
sealed interface AnfibioUiState {
    data class Success(val cards: List<AnfibioData>): AnfibioUiState
    object Error: AnfibioUiState
    object Loading: AnfibioUiState
}

@HiltViewModel
class AnfibioVM @Inject constructor(
    private val repository: AnfibioRepository
): ViewModel() {
    var anfibioUiState: AnfibioUiState by mutableStateOf(AnfibioUiState.Loading)
        private set

    init { setAnfibioInfo() }

    private fun setAnfibioInfo() {
        viewModelScope.launch {
            anfibioUiState = try {
                AnfibioUiState.Success(repository.getAnfibios())
            } catch (e: IOException) {
                AnfibioUiState.Error
            }
        }
    }
}
```

`AnfibioHome.kt`

```
@Composable
fun AnfibioHomeScreen(appState: AnfibioUiState) {
    when (appState) {
        is AnfibioUiState.Loading -> AnfibioLoading()
        is AnfibioUiState.Error -> AnfibioError()
        is AnfibioUiState.Success -> AnfibioList(listaAnfibios = appState.cards)
    }
}
```

## 5. Configuración del Proyecto

`AnfibioApp.kt`

Punto de entrada para Hilt y configuración de Coil 3.

```
@HiltAndroidApp
class AnfibioApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SingletonImageLoader.setSafe {
            ImageLoader.Builder(this)
                .crossfade(true)
                .build()
        }
    }
}
```

`AndroidManifest.xml`

```
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:name=".samples.anfibios.AnfibioApp"
        android:usesCleartextTraffic="true"
        ...>
        </application>
</manifest>
```

> [!NOTE]
> usesCleartextTraffic="true" es obligatorio si la API de pruebas utiliza el protocolo HTTP en lugar de HTTPS.
