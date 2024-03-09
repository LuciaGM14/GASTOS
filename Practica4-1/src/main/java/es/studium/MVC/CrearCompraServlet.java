

    package es.studium.MVC;

    import java.io.IOException;
    import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;
    import javax.sql.DataSource;

    @WebServlet("/CrearCompraServlet")
    public class CrearCompraServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        @Override
        public void init() throws ServletException {
            try {
                InitialContext initContext = new InitialContext();
                @SuppressWarnings("unused")
                DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/aplicacionTienda");
            } catch (Exception e) {
                throw new ServletException("Error al inicializar el pool de conexiones", e);
            }
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            // Obtener los parámetros de la solicitud
            String idCompraStr = request.getParameter("idCompra");
            String nuevaFechaCompra = request.getParameter("nuevaFecha");
            String nuevoImporteCompraStr = request.getParameter("nuevoImporte");
            String nuevoIdTiendaFKStr = request.getParameter("nuevoIdTienda");

            try {
                // Convertir los parámetros a tipos adecuados
                int idCompra = Integer.parseInt(idCompraStr);
                double nuevoImporteCompra = Double.parseDouble(nuevoImporteCompraStr);
                int nuevoIdTiendaFK = Integer.parseInt(nuevoIdTiendaFKStr);

                // Obtener la sesión
                HttpSession session = request.getSession();
                int idUsuarioFK = (int) session.getAttribute("usuarioLogueado");

                // Crear una nueva instancia del modelo
                Modelo modelo = new Modelo();

                // Llamar al método para modificar la compra en la base de datos
                modelo.modificarCompra(idCompra, nuevaFechaCompra, nuevoImporteCompra, nuevoIdTiendaFK);

                // Actualizar la lista de compras en la sesión
                ArrayList<Compra> compras = modelo.DatosCompra(idUsuarioFK);
                session.setAttribute("listadoCompra", compras);

                // Redirigir al usuario de vuelta a la página principal
                response.sendRedirect("Principal.jsp");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                // Manejar cualquier error que ocurra al modificar la compra
                // Por ejemplo, podrías redirigir a una página de error o mostrar un mensaje al usuario
                response.getWriter().println("Error al modificar la compra.");
            }
        }
    }
