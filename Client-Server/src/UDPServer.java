import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) {
        int porta = 9876;
        String fileOutput = "ricevuto_testo_lungo.txt";

        try (DatagramSocket socket = new DatagramSocket(porta);
             FileOutputStream fos = new FileOutputStream(fileOutput)) {

            System.out.println("Server UDP in ascolto sulla porta " + porta + "...");
            System.out.println("In attesa di ricevere il testo...\n");
            System.out.println("--------------------------------------------------");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket pacchettoRicevuto = new DatagramPacket(buffer, buffer.length);
                socket.receive(pacchettoRicevuto);

                // Controllo di fine file
                if (pacchettoRicevuto.getLength() == 0) {
                    System.out.println("\n--------------------------------------------------");
                    System.out.println("Segnale di fine file ricevuto. Trasferimento completato.");
                    break;
                }

                // 1. CONVERTIAMO I BYTE IN TESTO E LI STAMPIAMO A TERMINALE
                String testoRicevuto = new String(pacchettoRicevuto.getData(), 0, pacchettoRicevuto.getLength());
                System.out.print(testoRicevuto);

                // 2. SCRIVIAMO COMUNQUE I DATI NEL FILE DI DESTINAZIONE
                fos.write(pacchettoRicevuto.getData(), 0, pacchettoRicevuto.getLength());

                // Simuliamo il carico di lavoro del server con un ritardo di 100ms
                Thread.sleep(100);
            }

        } catch (Exception e) {
            System.err.println("Errore nel server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}