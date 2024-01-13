package ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ventas.model.VentasModel;
import ventas.services.VentasService;

@RestController
@RequestMapping("/api/ventas")
public class VentasController {
@Autowired
    private VentasService ventaService;

    @GetMapping
    public List<VentasModel> getAllVentas() {
        return ventaService.getAllVentas();
    }

    @GetMapping("/{id}")
    public VentasModel getVentaById(@PathVariable Integer id) {
        return ventaService.getVentaById(id);
    }

    @PostMapping
    public VentasModel saveVenta(@RequestBody VentasModel venta) {
        return ventaService.saveVenta(venta);
    }

    @DeleteMapping("/{id}")
    public void deleteVenta(@PathVariable Integer id) {
        ventaService.deleteVenta(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<VentasModel> updateVenta(@PathVariable Integer id, @RequestBody VentasModel venta) {
        VentasModel updatedVenta = ventaService.updateVenta(id, venta);

        if (updatedVenta != null) {
            return new ResponseEntity<>(updatedVenta, HttpStatus.OK);
        } else {
            // Manejar el caso en el que la venta no se pudo actualizar
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
