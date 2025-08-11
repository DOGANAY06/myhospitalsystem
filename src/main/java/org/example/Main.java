package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HastaYonetimSistemi sistem = new HastaYonetimSistemi();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Hasta Kayıt Sistemi  ===");
            System.out.println("Aşağıdaki verilen kriterlere göre tuşlama yapınız...\n");
            System.out.println("1. Hasta Ekle");
            System.out.println("2. Hastaları Listele");
            System.out.println("3. Hasta Güncelle");
            System.out.println("4. Hasta Sil");
            System.out.println("5. Çıkış");
            System.out.print("Seçiminiz: ");

            String secim = scanner.nextLine();

            switch (secim) {
                case "1":
                    sistem.hastaEkle();
                    break;
                case "2":
                    sistem.hastalariListele();
                    break;
                case "3":
                    sistem.hastaGuncelle();
                    break;
                case "4":
                    sistem.hastaSil();
                    break;
                case "5":
                    System.out.println("Program kapatılıyor...");
                    return;
                default:
                    System.out.println("Geçersiz seçim, tekrar deneyin.");
            }
        }
    }
}
