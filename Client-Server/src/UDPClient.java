import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        String indirizzoServer = "127.0.0.1"; // Indirizzo di localhost
        int portaServer = 9876;
        String nomeFile = "testo_lungo.txt"; // Il file fornito nel materiale

        try (DatagramSocket socket = new DatagramSocket();
             FileInputStream fis = new FileInputStream(new File(nomeFile))) {

            InetAddress indirizzo = InetAddress.getByName(indirizzoServer);
            byte[] buffer = new byte[1024];
            int byteLetti;

            System.out.println("Inizio invio del file: " + nomeFile);

            // Legge dal file fino a 1024 byte alla volta
            while ((byteLetti = fis.read(buffer)) != -1) {
                DatagramPacket pacchetto = new DatagramPacket(buffer, byteLetti, indirizzo, portaServer);
                socket.send(pacchetto);
            }

            // Invio di un pacchetto vuoto per segnalare la fine del file (EOF)
            DatagramPacket pacchettoFine = new DatagramPacket(new byte[0], 0, indirizzo, portaServer);
            socket.send(pacchettoFine);

            System.out.println("Invio completato con successo.");

        } catch (Exception e) {
            System.err.println("Errore nel client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}