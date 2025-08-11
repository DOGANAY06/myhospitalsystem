package org.example;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

// CRUD işlemlerini yöneten sınıf
public class HastaYonetimSistemi {
    private HashMap<String, Hasta> hastalar;
    private final Scanner scanner;

    public HastaYonetimSistemi() {
        // Dosyadan yükle; DosyaYardimcisi.verileriYukle()
        this.hastalar = DosyaYardimcisi.verileriYukle();
        this.scanner = new Scanner(System.in);
    }

    // ---------- T.C. Kimlik Numarası Doğrulama (resmi algoritma) ----------
    private boolean tcGecerli(String tc) {
        if (tc == null || !tc.matches("\\d{11}")) return false;
        if (tc.charAt(0) == '0') return false;

        int[] d = new int[11];
        for (int i = 0; i < 11; i++) d[i] = tc.charAt(i) - '0';

        int toplamTek = d[0] + d[2] + d[4] + d[6] + d[8];
        int toplamCift = d[1] + d[3] + d[5] + d[7];

        int onuncu = ((toplamTek * 7) - toplamCift) % 10;
        int onbirinci = (toplamTek + toplamCift + d[9]) % 10;

        return d[9] == onuncu && d[10] == onbirinci;
    }

    // ---------- Yardımcı: integer okuma (hata yakalama ile) ----------
    private int readInt(String prompt, int defaultValue) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) return defaultValue;
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Geçersiz sayı, tekrar deneyin.");
            }
        }
    }

    // ---------- Yeni hasta ekleme (TC doğrulama + benzersizlik) ----------
    public void hastaEkle() {
        System.out.print("Ad: ");
        String ad = scanner.nextLine().trim();

        System.out.print("Soyad: ");
        String soyad = scanner.nextLine().trim();

        int yas = readInt("Yaş: ", 0);

        // TC doğrulama ve benzersizlik kontrolü
        String tcKimlikNo;
        while (true) {
            System.out.print("T.C. Kimlik No (11 haneli): ");
            tcKimlikNo = scanner.nextLine().trim();
            if (!tcGecerli(tcKimlikNo)) {
                System.out.println("Hata: Geçersiz T.C. Kimlik No. Lütfen 11 haneli ve kurala uygun bir numara girin.");
                continue;
            }
            // Burada TC kimlik no benzersizlik kontrolü yapıyoruz
            String finalTcKimlikNo = tcKimlikNo;
            boolean tcVarMi = hastalar.values().stream()
                    .anyMatch(h -> h.getTcKimlikNo().equals(finalTcKimlikNo));
            if (tcVarMi) {
                System.out.println("❌ Bu T.C. Kimlik No ile zaten kayıtlı hasta var!");
                return;
            }
            break;
        }

        System.out.print("Telefon: ");
        String telefon = scanner.nextLine().trim();

        System.out.print("Adres: ");
        String adres = scanner.nextLine().trim();

        System.out.print("E-posta: ");
        String email = scanner.nextLine().trim();

        String id = UUID.randomUUID().toString(); // benzersiz id

        Hasta yeni = new Hasta(id, ad, soyad, yas, tcKimlikNo, telefon, adres, email);
        hastalar.put(id, yeni);

        System.out.println("✅ Hasta başarıyla eklendi: " + yeni);
        DosyaYardimcisi.verileriKaydet(hastalar);
    }

    // ---------- Tüm hastaları listele ----------
    public void hastalariListele() {
        if (hastalar.isEmpty()) {
            System.out.println("Kayıtlı hasta bulunmamaktadır.");
            return;
        }
        System.out.println("Tüm Hastalar:");
        for (Map.Entry<String, Hasta> e : hastalar.entrySet()) {
            System.out.println(e.getValue());
        }
    }

    // ---------- Hasta güncelle ----------e
    public void hastaGuncelle() {
        System.out.print("Güncellenecek hasta ID'si: ");
        String id = scanner.nextLine();
        Hasta h = hastalar.get(id);
        if (h == null) {
            System.out.println("Bu ID ile kayıt bulunamadı.");
            return;
        }

        System.out.print("Yeni Ad (" + h.getAd() + "): ");
        String ad = scanner.nextLine().trim();
        if (!ad.isEmpty()) h.setAd(ad);

        System.out.print("Yeni Soyad (" + h.getSoyad() + "): ");
        String soyad = scanner.nextLine().trim();
        if (!soyad.isEmpty()) h.setSoyad(soyad);

        int yas = readInt("Yeni Yaş (" + h.getYas() + "): ", h.getYas());
        h.setYas(yas);

        System.out.print("Yeni Telefon (" + h.getTelefon() + "): ");
        String tel = scanner.nextLine().trim();
        if (!tel.isEmpty()) h.setTelefon(tel);

        System.out.print("Yeni Adres (" + h.getAdres() + "): ");
        String adres = scanner.nextLine().trim();
        if (!adres.isEmpty()) h.setAdres(adres);

        System.out.print("Yeni E-posta (" + h.getEmail() + "): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) h.setEmail(email);

        System.out.println("✅ Hasta bilgileri güncellendi.");
        DosyaYardimcisi.verileriKaydet(hastalar);
    }

    // ---------- Hasta sil ----------
    public void hastaSil() {
        System.out.print("Silinecek hasta ID'si: ");
        String id = scanner.nextLine().trim();
        if (hastalar.remove(id) != null) {
            System.out.println("✅ Hasta silindi.");
            DosyaYardimcisi.verileriKaydet(hastalar);
        } else {
            System.out.println("❌ Bu ID ile kayıt bulunamadı.");
        }
    }
}

