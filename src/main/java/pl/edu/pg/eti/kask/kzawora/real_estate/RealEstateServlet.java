package pl.edu.pg.eti.kask.kzawora.real_estate;

import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Simple realEstate servlet used to show servlet based security.
 *
 * @author psysiu
 */
@WebServlet(urlPatterns = "/raw/realEstates")
@ServletSecurity(
        value = @HttpConstraint(
                rolesAllowed = {User.Roles.USER},
                transportGuarantee = ServletSecurity.TransportGuarantee.CONFIDENTIAL
        )
)
public class RealEstateServlet extends HttpServlet {

    @Inject
    private RealEstateService service;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        service.findAllRealEstates().forEach(realEstate -> writer.write(String.format("%s\n", realEstate.getAddress().getAddress())));
    }
}
