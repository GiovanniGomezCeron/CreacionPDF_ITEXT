package DAO;

import Factory.ConexionBD;
import Factory.FactoryConexionBD;
import Model.Categoria;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Image;

public class CategoriaDAOImplementar implements CategoriaDAO {

    ConexionBD conn;  //Crear el objeto tipo conexión.

    public CategoriaDAOImplementar() {
        //Definir a la base de datos que se conectará por defecto.
        //this.conn = FactoryConexionBD.open(FactoryConexionBD.MySQL);
    }

    @Override
    public List<Categoria> Listar() {
        this.conn = FactoryConexionBD.open(FactoryConexionBD.MySQL);
        List<Categoria> lista = new ArrayList<Categoria>();

        try {

            StringBuilder miSQL = new StringBuilder();
            miSQL.append("SELECT * FROM tb_categoria;");
            ResultSet resultadoSQL = this.conn.consultaSQL(miSQL.toString());
            while (resultadoSQL.next()) {
                Categoria categoria = new Categoria();
                //Asignar cada campo consultado al atributo en la clase.
                categoria.setId_categoria(resultadoSQL.getInt("id_categoria"));
                categoria.setNom_categoria(resultadoSQL.getString("nom_categoria"));
                categoria.setEstado_categoria(resultadoSQL.getInt("estado_categoria"));
                lista.add(categoria); //Agregar al array cada registro encontrado.
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            this.conn.cerrarConexion();
        }

        return lista;
    }

    @Override
    public List<Categoria> Listar2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Categoria editarCat(int id_cat_edit
    ) {
        this.conn = FactoryConexionBD.open(FactoryConexionBD.MySQL);
        Categoria categoria = new Categoria();
        StringBuilder miSQL = new StringBuilder();
        //Agregar la consulta SQL.
        miSQL.append("SELECT * FROM tb_categoria WHERE id_categoria = ").append(id_cat_edit);
        //Realizar la consulta.
        try {
            ResultSet resultadoSQL = this.conn.consultaSQL(miSQL.toString());
            while (resultadoSQL.next()) {
                categoria.setId_categoria(resultadoSQL.getInt("id_categoria"));
                categoria.setNom_categoria(resultadoSQL.getString("nom_categoria"));
                categoria.setEstado_categoria(resultadoSQL.getInt("estado_categoria"));
            }
        } catch (Exception e) {
        } finally {
            this.conn.cerrarConexion();
        }

        return categoria;
    }

    @Override
    public boolean guardarCat(Categoria categoria
    ) {
        this.conn = FactoryConexionBD.open(FactoryConexionBD.MySQL);
        boolean guardar = false;
        try {
            if (categoria.getId_categoria() == 0) {
                StringBuilder miSQL = new StringBuilder();
                //Agregar consulta SQL; el id_categoria es autoincrementable.
                miSQL.append("INSERT INTO tb_categoria(nom_categoria, estado_categoria) VALUES ('");
                miSQL.append(categoria.getNom_categoria() + "', ").append(categoria.getEstado_categoria());
                miSQL.append(");");
                //Invocar método para ejecutar la consulta.
                this.conn.ejecutarSQL(miSQL.toString());
                System.out.println("Registro Guardado...");
            } else if (categoria.getId_categoria() > 0) {                            //Comprobación para actualizar...
                System.out.println("Entramos...");
                StringBuilder miSQL = new StringBuilder();
                miSQL.append("UPDATE tb_categoria SET id_categoria = ").append(categoria.getId_categoria());
                miSQL.append(", nom_categoria =  '").append(categoria.getNom_categoria());
                miSQL.append("', estado_categoria =  ").append(categoria.getEstado_categoria());
                miSQL.append(" WHERE id_categoria = ").append(categoria.getId_categoria()).append(";");
                //Invocar método para ejecutar la consulta.
                this.conn.ejecutarSQL(miSQL.toString());
                System.out.println("Registro modificado correctamente!");
            }

            //return guardar;
        } catch (Exception e) {

        } finally {
            this.conn.cerrarConexion();
        }
        return guardar;
    }

    @Override
    public boolean borrarCat(int id_cat_borrar
    ) {
        this.conn = FactoryConexionBD.open(FactoryConexionBD.MySQL);
        boolean borrar = false;           //Bandera de resultados
        try {
            StringBuilder miSQL = new StringBuilder();
            miSQL.append("DELETE FROM tb_categoria WHERE id_categoria = ").append(id_cat_borrar);
            this.conn.ejecutarSQL(miSQL.toString());
            borrar = true;
        } catch (Exception e) {

        } finally {
            this.conn.cerrarConexion();  //Cerrar la conexión.
        }
        return borrar;
    }

    public void generar() {
        this.conn = FactoryConexionBD.open(FactoryConexionBD.MySQL);
        String nom;
        int id, ca;
        try {
            Document doc = new Document();
            String ruta = System.getProperty("user.dir");
            String d = System.getProperty("user.home");
            PdfWriter.getInstance(doc, new FileOutputStream(d + "/Desktop/Inventario-Categorias.pdf"));
            PdfPTable table = new PdfPTable(3);

            //parrafos
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Chunk.ALIGN_CENTER);
            parrafo.setFont(FontFactory.getFont("Tahoma", 19, Font.BOLDITALIC, BaseColor.ORANGE));
            parrafo.add("Registro de tabla Categoria\n\n");
            table.addCell("Id");
            table.addCell("Categoria");
            table.addCell("Estado");
            doc.open();
            doc.add(parrafo);

            StringBuilder miSQL = new StringBuilder();
            miSQL.append("SELECT * FROM tb_categoria;");

            ResultSet resultadoSQL = this.conn.consultaSQL(miSQL.toString());
            while (resultadoSQL.next()) {
                Categoria categoria = new Categoria();
                //Asignar cada campo consultado al atributo en la clase.
                table.addCell(String.valueOf(resultadoSQL.getInt("id_categoria")));
                String estado = "Activo";

                table.addCell(resultadoSQL.getString("nom_categoria"));
                if (resultadoSQL.getInt("estado_categoria") == 0) {
                    estado = "Inactivo";
                }
                table.addCell(estado);

            }
            doc.add(table);
            doc.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            this.conn.cerrarConexion();
        }
    }

}
