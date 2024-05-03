package Manuel.Ortega.crudmanuel2_a

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.dataClassProductos

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //mandar a llamar a todos los elementos de la pantalla

        val txtProducto=findViewById<EditText>(R.id.txt_producto)
        val txtPrecio = findViewById<EditText>(R.id.txt_Precio)
        val txtCantidad = findViewById<EditText>(R.id.txt_Cantidad)
        val btnAgregar = findViewById<Button>(R.id.btn_Agregar)

        // programar el boton
        btnAgregar.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO){

                //guardar datos

                //crear un objeto de la clase conexion
                val claseConexion=ClaseConexion().cadenaConexion()

                //crar una variable que contenga un preparedstatement

                val addProducto=claseConexion?.prepareStatement("insert into tbProductos(nombreProducto,precio,cantidad)values(?,?,?)")!!

                addProducto.setString(1,txtProducto.text.toString())
                addProducto.setInt(2,txtPrecio.text.toString().toInt())
                addProducto.setInt(3,txtCantidad.text.toString().toInt())
                addProducto.executeUpdate()
            }
        }

        ////////////////////////////////mostrar datos ////////////////////////

        val rcvproductos=findViewById<RecyclerView>(R.id.rcv_Productos)

        //asignar un layout al reciledview

        rcvproductos.layoutManager=LinearLayoutManager(this)

        //funcion para obtener datos
        fun obtenerDatos():List<dataClassProductos>{
            val objConexion=ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
        }

    }
}