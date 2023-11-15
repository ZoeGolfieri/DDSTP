package ar.edu.utn.frba.dds.domain.usuario;

import ar.edu.utn.frba.dds.domain.servicio.Servicio;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Empresas")
@NoArgsConstructor
public class Empresa {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_empresa")
  private Integer id_empresa;
  @Column(name = "nombreEmpresa", columnDefinition = "VARCHAR(20)")
  String nombreEmpresa;
  @Enumerated
  @Column(name = "tipoEmpresa")
  TipoEmpresa tipo;
  @ElementCollection
  List<String> problematicas;
  @ManyToMany
  @JoinTable(
      name = "empresa_servicio", // Nombre de la tabla intermedia
      joinColumns = @JoinColumn(name = "id_empresa"), // Columna que hace referencia a esta entidad
      inverseJoinColumns = @JoinColumn(name = "id_servicio") // Columna que hace referencia a la otra entidad
  )
  List<Servicio> serviciosAsociados;

  public Empresa(String nombreEmpresa, TipoEmpresa tipo) {
    this.nombreEmpresa = nombreEmpresa;
    this.tipo = tipo;
  }
}



