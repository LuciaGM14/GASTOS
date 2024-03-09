package es.studium.MVC;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/InformeServlet")
public class InformeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public InformeServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el mes y el año seleccionados por el usuario
        String mesParam = request.getParameter("mes");
        String anioParam = request.getParameter("anio");

        if (mesParam != null && anioParam != null) {
            int mes = Integer.parseInt(mesParam);
            int anio = Integer.parseInt(anioParam);

            // Obtener el ID del usuario de la sesión actual
            HttpSession session = request.getSession();
            Integer idUsuarioFK = (Integer) session.getAttribute("idUsuario");

            if (idUsuarioFK != null) {
                // Obtener las compras del mes y año seleccionados
                Modelo modelo = new Modelo();
                ArrayList<Compra> compras = modelo.DatosCompraPorMes(idUsuarioFK.intValue(), mes);

                // Calcular el total de las compras
                double total = 0;
                for (Compra compra : compras) {
                    total += compra.getImporteCompra();
                }

                // Establecer atributos en la solicitud para mostrar en el JSP
                request.setAttribute("compras", compras);
                request.setAttribute("total", total);

                // Redireccionar al JSP para mostrar el informe
                request.getRequestDispatcher("/Informe.jsp").forward(request, response);
            } else {
                // Manejar el caso en que el ID del usuario sea nulo
                // Puedes redirigir a una página de error o mostrar un mensaje al usuario
                System.err.println("El ID del usuario es nulo.");
                // Aquí redirigiremos al usuario a una página de error
                response.sendRedirect(request.getContextPath() + "/error.jsp");
            }
        } else {
            // Manejar el caso en que los parámetros de mes y/o año sean nulos
            // Puedes redirigir a una página de error o mostrar un mensaje al usuario
            System.err.println("Los parámetros 'mes' y/o 'anio' son nulos.");
            // Aquí redirigiremos al usuario a una página de error
            response.sendRedirect(request.getContextPath() + "/Error.jsp");
        }
    }
}