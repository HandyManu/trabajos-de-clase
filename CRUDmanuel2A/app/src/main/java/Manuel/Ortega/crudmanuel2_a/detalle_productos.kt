package Manuel.Ortega.crudmanuel2_a

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detalle_productos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_productos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //mando a llamr a todos los elementos de la pantalla

        val txtUUIDDetlle = findViewById<TextView>(R.id.txtUUIDDetalle)
        val txtNombreDetalle = findViewById<TextView>(R.id.txtNombreDetalle)
        val txtPrecioDetalle = findViewById<TextView>(R.id.txtPrecioDetalle)
        val txtCantidadDetalle = findViewById<TextView>(R.id.txtCantidadDetalle)
        val imgAtras=findViewById<ImageView>(R.id.imgAtras)

        //recibir los valores

        val UUIDProducto = intent.getStringExtra("uuid")
        val nombreProducto = intent.getStringExtra("nombre")
        val precioProducto = intent .getIntExtra("precio",0)
        val cantidadProducto = intent .getIntExtra("cantidad",0)

        // poner los valores recividos en el textview

        txtUUIDDetlle.text=UUIDProducto
        txtNombreDetalle.text=nombreProducto
        txtPrecioDetalle.text=precioProducto.toString()
        txtCantidadDetalle.text = cantidadProducto.toString()

        imgAtras.setOnClickListener {
            val pantallaAtras = Intent(this,MainActivity::class.java)
            startActivity(pantallaAtras)
        }

    }
}