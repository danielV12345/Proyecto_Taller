package productos.model;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "productos")
public class ProductoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_categoria",referencedColumnName = "id_categoria")  
    private CategoriaModel categoria;

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public CategoriaModel getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaModel categoria) {
        this.categoria = categoria;
    }
    
}

