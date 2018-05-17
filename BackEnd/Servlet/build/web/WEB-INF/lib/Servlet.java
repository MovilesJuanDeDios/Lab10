/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import AccesoDatos.ServicioProducto;
import AccesoDatos.ServicioUsuario;
import LogicaNegocio.Product;
import LogicaNegocio.Usuario;

/**
 *
 * @author slon
 */
public class Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            
            //String para guardar el JSON generaro por al libreria GSON
            String json;
            String json2;
           
            //Se crea el objeto
            Product p = new Product();
            Usuario u = new Usuario();
            
            ServicioProducto sp = new ServicioProducto();
            ServicioUsuario su = new ServicioUsuario();
            
            Thread.sleep(500);
            
            String accion = request.getParameter("accion");
            List<Double> listaCalculo = new ArrayList();
            switch (accion) {
                case "agregarUsuario":
                          u.setNombre(request.getParameter("nombre"));
                          u.setEmail(request.getParameter("email"));
                          u.setUsername(request.getParameter("username"));
                          u.setClave(request.getParameter("password")); 
                          u.setRol(Integer.parseInt(request.getParameter("rol"))); 
                          
                          su.insertarUsuario(u);

                          out.print("C~El objeto fue ingresado correctamente");
                          break;

                case "setUsuario":
                        u.setNombre(request.getParameter("nombre"));
                        u.setEmail(request.getParameter("email"));
                        u.setUsername(request.getParameter("username"));
                        u.setClave(request.getParameter("password")); 
                        u.setRol(Integer.parseInt(request.getParameter("rol"))); 

                        su.actualizarUsuario(u);

                        out.print("C~El objeto fue actualizado correctamente");
                        break;

                case "consultarUsuario":

                    List<Product> list = new ArrayList(su.listarUsuario());
                    json = new Gson().toJson(list);    
                    out.print(json);
                    break;

                case "deleteUsuario":
                    String user = request.getParameter("nombre");
                    su.eliminarUsuario(user);
                    out.print("C~El objeto fue eliminado correctamente");
                    break;      
                    
                /* --------------------------------------------------------------------------------------- */
                    
                case "agregarProducto":
                          /*p.setCodigo(request.getParameter("codigo"));
                          p.setNombreProducto(request.getParameter("nombre"));
                          p.setPrecio(Double.parseDouble(request.getParameter("precio")));
                          p.setImportado(Integer.parseInt(request.getParameter("importado")));
                          p.setTipo(request.getParameter("tipo")); */
                          
                          sp.insertarProducto(p);

                          out.print("C~El objeto fue ingresado correctamente");
                          break;

                case "setProducto":
                        /*p.setCodigo(request.getParameter("codigo"));
                        p.setNombreProducto(request.getParameter("nombre"));
                        p.setPrecio(Double.parseDouble(request.getParameter("precio")));
                        p.setImportado(Integer.parseInt(request.getParameter("importado")));
                        p.setTipo(request.getParameter("tipo")); */

                        sp.actualizarProducto(p);

                        out.print("C~El objeto fue actualizado correctamente");
                        break;

                case "consultarProductos":

                    List<Product> listPro = new ArrayList(sp.listarProducto());
                    json = new Gson().toJson(listPro);   
                    out.print(json);
                    break;

                case "deleteProducto":
                    int prod = Integer.parseInt(request.getParameter("nombre"));
                    sp.eliminarProducto(prod);
                    out.print("C~El objeto fue eliminado correctamente");
                    break;

                default:
                    out.print("E~No se indicó la acción que se desea realizare");
                    break;
                  }

        } catch (NumberFormatException e) {
            out.print("E~" + e.getMessage());
        } catch (Exception e) {
            out.print("E~" + e.getMessage() );
        } 
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
