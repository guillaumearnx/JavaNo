import network.Client;
import network.Server;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * @author arnoux23u
 */
public class JavaNo {


    public static void main(String[] args) {
        new JFrame(){{
            setLayout(new BorderLayout());
            Font basic = new Font("Arial", Font.PLAIN, 20);
            add(new JLabel("Merci de choisir le mode de jeu") {{
                setHorizontalAlignment(SwingConstants.CENTER);
                setFont(basic);
            }}, BorderLayout.NORTH);
            JTextField name = new JTextField() {{
                setText("Player" + String.format("%04d", new Random().nextInt(10000)));
                setPreferredSize(new Dimension(200, 20));
                setHorizontalAlignment(SwingConstants.CENTER);
            }};
            JPanel choice = new JPanel(){{
                setLayout(new GridLayout(1, 2));
                setPreferredSize(new Dimension(300, 80));
            }};
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
            add(new JPanel() {{
                setLayout(new BorderLayout());
                add(choice, BorderLayout.NORTH);
                add(name, BorderLayout.SOUTH);
            }}, BorderLayout.CENTER);
            add(new JButton("Lancer") {{
                setFont(basic);
                addActionListener(e -> {
                    dispose();
                    if (server.isSelected())
                        new Server();
                    else
                        new Client(name.getText());
                });
            }}, BorderLayout.SOUTH);
        }};
    }

}
