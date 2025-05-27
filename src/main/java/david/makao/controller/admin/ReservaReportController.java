package david.makao.controller.admin;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.DataSource;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ReservaReportController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/admin/reservas/pdf")
    public void exportReservasToPdf(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=reservas.pdf");

        // Cargar y compilar el archivo .jrxml
        JasperReport report = JasperCompileManager.compileReport(
                resourceLoader.getResource("classpath:reports/reservasReporte.jrxml").getInputStream()
        );

        // Parámetros para el reporte (opcional)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Reporte de Reservas");

        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);

            OutputStream out = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al generar el reporte: " + e.getMessage());
        }
    }
}
