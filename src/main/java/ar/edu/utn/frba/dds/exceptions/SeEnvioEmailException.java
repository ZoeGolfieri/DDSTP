package ar.edu.utn.frba.dds.exceptions;

public class SeEnvioEmailException extends RuntimeException {
  public SeEnvioEmailException(String mensaje) {
    super(mensaje);
  }
}
