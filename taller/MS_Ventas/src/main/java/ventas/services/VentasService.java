package ventas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ventas.model.VentasModel;
import ventas.repository.IVentasRepository;


@Service
public class VentasService {
    @Autowired
    private IVentasRepository ventaRepository;

    public List<VentasModel> getAllVentas() {
        return ventaRepository.findAll();
    }

    public VentasModel getVentaById(Integer id) {
        return ventaRepository.findById(id).orElse(null);
    }

    public VentasModel saveVenta(VentasModel venta) {
        return ventaRepository.save(venta);
    }

    public void deleteVenta(Integer id) {
        ventaRepository.deleteById(id);
    }
    public VentasModel updateVenta(Integer id, VentasModel venta) {
        // Verifica si la venta con el ID dado existe
        VentasModel existingVenta = ventaRepository.findById(id).orElse(null);
        
        if (existingVenta != null) {
            // Actualiza los campos relevantes de la venta existente
            existingVenta.setTotal(venta.getTotal());
            existingVenta.setFechaVenta(venta.getFechaVenta());
            existingVenta.setIdUsuario(venta.getIdUsuario());
            existingVenta.setIdProducto(venta.getIdProducto());
    
            // Guarda la venta actualizada en la base de datos
            return ventaRepository.save(existingVenta);
        } else {
            // Manejar el caso en el que la venta no existe
            // Puedes lanzar una excepción, devolver null, etc.
            return null;
        }
    }
}
