package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getter, Setter, toString, equals, hashCode hepsini ekler
@NoArgsConstructor // Parametresiz constructor
@AllArgsConstructor // Parametreli constructor
public class Hasta {
    private String id;
    private String ad;
    private String soyad;
    private int yas;
    private String tcKimlikNo;
    private String telefon;
    private String adres;
    private String email;


    @Override
    public String toString() {
        return " ID: " + id + " | " + ad + " " + soyad + " | Yaş: " + yas + " | T.C. Kimlik No: " + tcKimlikNo + " | Telefon: " + telefon + " | Adres: " + adres + " | E posta adresi:" + email;
    }
}
