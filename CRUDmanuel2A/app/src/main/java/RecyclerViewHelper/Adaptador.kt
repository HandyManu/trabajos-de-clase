package RecyclerViewHelper

import Manuel.Ortega.crudmanuel2_a.R
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassProductos
import java.util.UUID

class Adaptador(private var Datos: List<dataClassProductos>) : RecyclerView.Adapter<ViewHolder>() {

    fun actualizarLista(nuevaLista:List<dataClassProductos>){
        Datos=nuevaLista
        notifyDataSetChanged()
    }

    //funcion parar actualizar el reciler view cuando actualizo los datos

    fun actualizarListaDespuesDeActualizarDatos(uuid: String,nuevoNombre:String){
        val index=Datos.indexOfFirst { it.uuid==uuid }
        Datos[index].NombreProducto=nuevoNombre
        notifyItemChanged(index)

    }
    fun eliminarRegistro(nombreProducto:String,position: Int){

        //quitar el elementpo de la lista
        val listaDatos = Datos .toMutableList()
        listaDatos.removeAt(position)

        //quitar de la base de datos
        GlobalScope.launch(Dispatchers.IO) {

            //crear un objeto e la clase conexion
            val objConexion=ClaseConexion().cadenaConexion()

            val deleteProducto = objConexion?.prepareStatement("delete tbproductos where NombreProducto=?")!!
            deleteProducto.setString( 1,nombreProducto)
            deleteProducto.executeUpdate()

            val commit = objConexion.prepareStatement( "commit")!!
            commit.executeUpdate()
        }
        Datos=listaDatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()

    }

    fun actualizarProducto(nombreProducto: String , uuid:String){
        //crear na co rrutinan
        GlobalScope.launch(Dispatchers.IO){
            //creo un objeto de la clase conexion

            val objConexion = ClaseConexion().cadenaConexion()

            //variable que contenga prepared sttement
            val updateProducto = objConexion?.prepareStatement("update tbproductos set NombreProducto = ? where uuid = ?")!!

            updateProducto.setString(1,nombreProducto)
            updateProducto.setString(2,uuid)
            updateProducto.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                actualizarListaDespuesDeActualizarDatos(uuid,nombreProducto)
            }

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = Datos[position]
        holder.textView.text = producto.NombreProducto

        val item =Datos[position]


        holder.imgBorrar.setOnClickListener {
            //craeamos una alaerta

            //invocamos  el contexto
            val context = holder.itemView.context

            //CREO LA ALERTA

            val builder = AlertDialog.Builder(context)

            //le ponemos titulo a la alerta

            builder.setTitle("¿estas seguro?")

            //ponerle mendsaje a la alerta

            builder.setMessage("Deseas en verdad eliminar el registro")

            //agrgamos los botones

            builder.setPositiveButton("si"){dialog,wich ->
                eliminarRegistro(item.NombreProducto,position)
            }

            builder.setNegativeButton("no"){dialog,wich ->

            }

            //cramos la alerta
            val alertDialog=builder.create()

            //mostramos la alerta

            alertDialog.show()

        }

        holder.imgEditar.setOnClickListener {
            val context=holder.itemView.context

            //creo la alerta
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar nombre")

            //agregar un cuadro de texto para que el usuario pueda escribir un nuevo nombre

            val cuadritoNuevoNombre = EditText(context)
            cuadritoNuevoNombre.setHint(item.NombreProducto)
            builder.setView(cuadritoNuevoNombre)

            builder.setPositiveButton("Actualizar"){
                dialog,which->actualizarProducto(cuadritoNuevoNombre.text.toString(),item.uuid)
            }

            builder.setNegativeButton("cancelar"){
                dialog,which->dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

    }
}
