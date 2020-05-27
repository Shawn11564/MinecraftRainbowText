import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RainbowTextGUI extends JFrame {
	private JTextArea input;
	private JButton rainbowfy;
	private JPanel panel;
	private JTextField output;
	private JCheckBox enableBoldCheckBox;
	private JCheckBox toggleSignTextCheckBox;
	private JCheckBox useCustomRainbowCheckBox;
	private JTextField customRainbow;

	public RainbowTextGUI(String title, int width, int height) {
		super(title);
		setSize(width, height);
		rainbowfy.addActionListener(e -> output.setText(getRainbowText(input.getText())));

		setContentPane(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		output.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				output.selectAll();
			}
		});
		output.setEditable(false);
		input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				double max = toggleSignTextCheckBox.isSelected() ? 15 : 256;

				int length = input.getText().length();
				length *= enableBoldCheckBox.isSelected() ? 5 : 3;

				if (length > max) {
					int l = input.getText().length();
					input.setText(input.getText().substring(0, l - 1));
				}
			}
		});
		input.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				input.selectAll();
			}
		});
	}

	public String getRainbowText(String original) {
		char[] colors = useCustomRainbowCheckBox.isSelected() ? customRainbow.getText().toCharArray() : Utils.DEFAULT_RAINBOW.toCharArray();
		StringBuilder sb = new StringBuilder();
		int spot = 0;

		for (char c : original.toCharArray()) {
			if (c == ' ') {
				sb.append(' ');
			} else {
				sb.append('&').append(colors[spot]);
				if (enableBoldCheckBox.isSelected()) {
					sb.append('&').append('l');
				}
				sb.append(c);

				spot++;
				if (spot > colors.length - 1) {
					spot = 0;
				}
			}
		}

		return sb.toString();
	}

}
