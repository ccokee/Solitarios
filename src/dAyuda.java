import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class dAyuda extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public dAyuda() {
		FileReader reader;
		ImageIcon icon;
		try {
			icon = new ImageIcon("Images/Jorge.jpg");
			reader = new FileReader("Ayuda/ayuda");
			FileReader reader2 =new FileReader("Ayuda/acercade");
			setBounds(100, 100, 640, 480);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(new BorderLayout(0, 0));
			{
				JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
				contentPanel.add(tabbedPane, BorderLayout.CENTER);
				{
					JPanel panel = new JPanel();
					tabbedPane.addTab("Ayuda", null, panel, null);
					panel.setLayout(new BorderLayout(0, 0));
					{
						JTextArea textPane = new JTextArea();
						JScrollPane sampleScrollPane = new JScrollPane (textPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						panel.add(sampleScrollPane, BorderLayout.CENTER);
						textPane.setEditable(false);
						textPane.read(reader, "Ayuda/ayuda");
					}
				}
				{
					JPanel panel = new JPanel();
					tabbedPane.addTab("Acerca de...", null, panel, null);
					panel.setLayout(null);
				
					Box horizontalBox = Box.createHorizontalBox();
					horizontalBox.setBounds(12, 12, 274, 386);
					panel.add(horizontalBox);
				
					JLabel lblJorgeCurbera = new JLabel();
					lblJorgeCurbera.setIcon(icon);
					horizontalBox.add(lblJorgeCurbera);
				
					Box verticalBox = Box.createVerticalBox();
					verticalBox.setBounds(298, 12, 315, 386);
					panel.add(verticalBox);
				
					JTextArea textPane = new JTextArea();
					JScrollPane sampleScrollPane = new JScrollPane (textPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
					verticalBox.add(sampleScrollPane);
					textPane.setEditable(false);
					textPane.read(reader2, "Ayuda/acercade");
				}
			}
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton okButton = new JButton("OK");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dispose();
						}
					});
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
