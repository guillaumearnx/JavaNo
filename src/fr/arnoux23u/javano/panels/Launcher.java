package fr.arnoux23u.javano.panels;

import fr.arnoux23u.javano.network.Client;
import fr.arnoux23u.javano.network.Server;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Launcher extends JPanel {

    public Launcher(JFrame parent) {
        JTextField name = new JTextField();
        name.setText("Player"+String.format("%04d", new Random().nextInt(10000)));
        name.setPreferredSize(new Dimension(200,20));
        name.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout(new BorderLayout());
        Font font = new Font("Arial", Font.PLAIN, 20);
        this.add(new JLabel("Merci de choisir le mode de jeu") {{
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(font);
        }}, BorderLayout.NORTH);
        JPanel choice = new JPanel();
        choice.setLayout(new GridLayout(1, 2));
        choice.setPreferredSize(new Dimension(300, 80));
        JRadioButton server = new JRadioButton("Server") {{
            setHorizontalAlignment(CENTER);
            addActionListener(e -> name.setVisible(false));
        }};
        JRadioButton client = new JRadioButton("Client") {{
            setHorizontalAlignment(CENTER);
            setSelected(true);
            addActionListener(e -> name.setVisible(true));
        }};
        new ButtonGroup() {{
            add(server);
            add(client);
        }};
        choice.add(server);
        choice.add(client);
        this.add(new JPanel(){{
            setLayout(new BorderLayout());
            add(choice, BorderLayout.NORTH);
            add(name, BorderLayout.SOUTH);
        }}, BorderLayout.CENTER);
        this.add(new JButton("Lancer") {{
            setFont(font);
            addActionListener(e -> {
                parent.dispose();

                if (server.isSelected()) {
                    /*JFrame f = new JFrame();
                    f.setTitle("JavaNO - Server");
                    sp.setPreferredSize(new Dimension(1400, 800));
                    f.setContentPane(sp);
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.requestFocusInWindow();
                    f.pack();
                    f.setVisible(true);*/
                    new Server();

                }else {
                    new Client(name.getText());
                }

            });
        }}, BorderLayout.SOUTH);
    }

}
