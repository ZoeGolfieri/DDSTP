package com.georef;

import ar.edu.utn.frba.dds.domain.localizacion.Provincia;
import ar.edu.utn.frba.dds.domain.localizacion.division.Division;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoRefApi {
  @GET("provincias")
  Call<Provincia> provincia(@Query("nombre") String nombre);

  @GET("municipios")
  Call<Division> municipioDeProvincia(@Query("provincia") String nombreProvincia, @Query("nombre")String nombreMunicipio);

  @GET("departamentos")
  Call<Division> departamentoDeProvincia(@Query("provincia") String nombreProvincia, @Query("nombre") String nombreDepartamento);

}
