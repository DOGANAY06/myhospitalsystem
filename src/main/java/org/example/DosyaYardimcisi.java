package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/*
  Dosya formatı (satır başına):
  id;ad;soyad;yas;tcKimlikNo;telefon;adres;email

  Dosyadan okurken HashMap'in anahtarı tcKimlikNo.
*/
public class DosyaYardimcisi {
    private static final String DOSYA_ADI = "hastalar.txt";

    // Map'i dosyaya yaz
    public static void verileriKaydet(Map<String, Hasta> hastalar) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DOSYA_ADI))) {
            for (Map.Entry<String, Hasta> entry : hastalar.entrySet()) {
                Hasta h = entry.getValue();
                // ; ile ayırıyoruz
                String line = String.join(";",
                        safe(h.getId()),
                        safe(h.getAd()),
                        safe(h.getSoyad()),
                        String.valueOf(h.getYas()),
                        safe(h.getTcKimlikNo()),
                        safe(h.getTelefon()),
                        safe(h.getAdres()),
                        safe(h.getEmail())
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Veriler kaydedilirken hata oluştu: " + e.getMessage());
        }
    }

    // Dosyadan yükle ve HashMap olarak döndür (key = tcKimlikNo)
    public static HashMap<String, Hasta> verileriYukle() {
        HashMap<String, Hasta> hastalar = new HashMap<>();
        File dosya = new File(DOSYA_ADI);
        if (!dosya.exists()) {
            return hastalar; // yoksa boş döndür
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(dosya))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                String[] p = satir.split(";", -1); // -1: trailing empty'leri koru
                if (p.length >= 8) {
                    String id = p[0];
                    String ad = p[1];
                    String soyad = p[2];
                    int yas = 0;
                    try { yas = Integer.parseInt(p[3]); } catch (NumberFormatException ignored) {}
                    String tc = p[4];
                    String telefon = p[5];
                    String adres = p[6];
                    String email = p[7];

                    Hasta h = new Hasta(id, ad, soyad, yas, tc, telefon, adres, email);
                    if (tc != null && !tc.isEmpty()) {
                        hastalar.put(tc, h); // anahtar: tcKimlikNo
                    }
                } // aksi halde satırı atla
            }
        } catch (IOException e) {
            System.err.println("Veriler yüklenirken hata oluştu: " + e.getMessage());
        }

        return hastalar;
    }

    // null kontrolü için yardımcı
    private static String safe(String s) {
        return s == null ? "" : s;
    }
}


