import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Cliente extends JFrame {
    private JButton boton;
    private Socket socket;
    private OutputStream outputStream;

    public Cliente() {
        setTitle("Boton - Cliente");
        setSize(new Dimension(200, 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        boton = new JButton("Encender");
        boton.setPreferredSize(new Dimension(100, 100));
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (boton.getText().equals("Encender")) {
                        outputStream.write('1');
                        boton.setText("Apagar");
                        System.out.println("Luz encendida");
                    } else {
                        outputStream.write('0');
                        boton.setText("Encender");
                        System.out.println("Luz apagada");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(boton);
        setVisible(true);

        try {
            socket = new Socket("127.0.0.1", 4444);
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Cliente c = new Cliente();
    }
}