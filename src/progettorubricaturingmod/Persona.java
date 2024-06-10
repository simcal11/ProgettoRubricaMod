/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progettorubricaturingmod;

/**
 *
 * @author simdr
 */
public class Persona {

    private String nome;
    private String cognome;
    private String indirizzo;
    private String telefono;
    private int eta;

    public Persona(String nome, String cognome, String indirizzo, String telefono, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.eta = eta;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getEta() {
        return eta;
    }

    public String[] getArrayValori() {
        String[] array = {getNome(), getCognome(), String.valueOf(getTelefono())};
        return array;
    }

   
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true  
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Persona or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Persona)) {
            return false;
        }

        Persona p = (Persona) o;

        return this.getNome().equals(p.getNome()) && this.getCognome().equals(p.getCognome()) && this.getTelefono().equals(p.getTelefono()) && this.getIndirizzo().equals(p.getIndirizzo()) && this.getEta() == p.getEta();

    }
}
