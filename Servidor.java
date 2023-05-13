import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Servidor extends JFrame {
    private JPanel panel;
    private Thread hilo;
    private volatile boolean activo = true;

    public Servidor() {
        setTitle("Etiqueta - Servidor");
        setSize(new Dimension(200, 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(100, 100));
        panel.setBackground(Color.WHITE);

        add(panel);
        setVisible(true);

        try {
            ServerSocket serverSocket = new ServerSocket(4444);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();

            hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (activo) {
                            int data = inputStream.read();
                            if (data == '1') {
                                blink(Color.YELLOW, Color.WHITE);
                            } else if (data == '0') {
                                panel.setBackground(Color.WHITE);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            hilo.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void blink(Color c1, Color c2) {
        try {
            for (int i = 0; i < 50; i++) {
                panel.setBackground(c1);
                Thread.sleep(500);
                panel.setBackground(c2);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Servidor s = new Servidor();
    }
}