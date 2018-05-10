package AccesoDatos;

import LogicaNegocio.Product;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import oracle.jdbc.internal.OracleTypes;

/**
 *
 * @author casca
 */
public class ServicioProducto extends Servicio {

    private static final String INSERTAPRODUCTO = "{call insertarProduct(?,?,?,?,?,?)}";
    private static final String ACTUALIZARPRODUCTO = "{call actualizarProduct(?,?,?,?,?,?)}";
    private static final String BUSCARPRODUCTO = "{?=call buscarProduct(?)}";
    private static final String LISTARPRODUCTO = "{?=call listarProduct()}";
    private static final String ELIMINARPRODUCTO = "{call eliminarProducto(?)}";
    private static final String TOTALPAGAR = "{?=call totalPagar(?,?)}";

    public ServicioProducto() {

    }

    public void insertarProducto(Product producto) throws GlobalException, NoDataException {
        connect();
        CallableStatement pstmt = null;

        try {
            pstmt = cn.prepareCall(INSERTAPRODUCTO);
            pstmt.setInt(1, producto.getId());
            pstmt.setString(2, producto.getTitle());
            pstmt.setString(3, producto.getShortdesc());
            pstmt.setInt(4, producto.getCantidad());
            pstmt.setInt(5, producto.getPrice());
            pstmt.setInt(6, producto.getImage());

            boolean resultado = pstmt.execute();

            if (resultado == true) {
                throw new NoDataException("No se realizo la inserción");
            } else {
                System.out.println("\nInserción Satisfactoria!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Llave duplicada");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public void actualizarProducto(Product producto) throws GlobalException, NoDataException {
        connect();
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(ACTUALIZARPRODUCTO);
            pstmt.setInt(1, producto.getId());
            pstmt.setString(2, producto.getTitle());
            pstmt.setString(3, producto.getShortdesc());
            pstmt.setInt(4, producto.getCantidad());
            pstmt.setInt(5, producto.getPrice());
            pstmt.setInt(6, producto.getImage());
            
            boolean resultado = pstmt.execute();

            if (resultado == true) {
                throw new NoDataException("No se realizo la actualización");
            } else {
                System.out.println("\nModificación Satisfactoria!");
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }
    
     public Product buscarProducto(String title) throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        Product producto = null;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(BUSCARPRODUCTO);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, title);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                producto = new Product(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("shortdesc"),
                        rs.getInt("cantidad"),
                        rs.getInt("price"),
                        rs.getInt("image"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();

            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        if (producto == null) {
            throw new NoDataException("No hay datos");
        }
        System.out.print(producto.toString());
        return producto;
    }
    
    public Collection listarProducto() throws GlobalException, NoDataException {
        connect();

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Product producto = null;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(LISTARPRODUCTO);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                producto = new Product(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("shortdesc"),
                        rs.getInt("cantidad"),
                        rs.getInt("price"),
                        rs.getInt("image"));
                
                coleccion.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        if (coleccion == null || coleccion.size() == 0) {
            throw new NoDataException("No hay datos");
        }
       
        return coleccion;
    }

    public void eliminarProducto(int id) throws GlobalException, NoDataException {
        connect();
        PreparedStatement pstmt = null;
        try {
            pstmt = cn.prepareStatement(ELIMINARPRODUCTO);
            pstmt.setInt(1, id);

            boolean resultado = pstmt.execute();

            if (resultado == true) {
                throw new NoDataException("No se realizo el borrado");
            } else {
                System.out.println("\nEliminación Satisfactoria!");
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public double totalPagar(Product producto) throws GlobalException, NoDataException {
        connect();
        double total;
        CallableStatement pstmt = null;
        try {
            pstmt = cn.prepareCall(TOTALPAGAR);
            pstmt.registerOutParameter(1, OracleTypes.DOUBLE);
            pstmt.setDouble(2, producto.getPrice());
            pstmt.setDouble(3, producto.getCantidad());
            pstmt.execute();
            total = pstmt.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                disconnect();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }

        
        return total;
    }
}
