import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class dStats extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public JLabel lblSolitarioSaltos;
	public JLabel lblIntentosSaltos;
	public JLabel lblExitosSaltos;
	public JLabel lblSolitarioClasico;
	public JLabel lblIntentosClasico;
	public JLabel lblExitosClasico;
	/**
	 * Create the dialog.
	 */
	public dStats() {
		setBounds(100, 100, 150, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			lblSolitarioSaltos = new JLabel("Solitario Saltos:");
			lblSolitarioSaltos.setBounds(12, 12, 104, 14);
			contentPanel.add(lblSolitarioSaltos);
		}
		{
			lblIntentosSaltos = new JLabel("Intentos: ");
			lblIntentosSaltos.setBounds(22, 48, 55, 14);
			contentPanel.add(lblIntentosSaltos);
		}
		{
			lblExitosSaltos = new JLabel("Exitos: ");
			lblExitosSaltos.setBounds(22, 84, 55, 14);
			contentPanel.add(lblExitosSaltos);
		}
		{
			lblSolitarioClasico = new JLabel("Solitario Clásico: ");
			lblSolitarioClasico.setBounds(12, 118, 104, 14);
			contentPanel.add(lblSolitarioClasico);
		}
		{
			lblIntentosClasico = new JLabel("Intentos: ");
			lblIntentosClasico.setBounds(22, 153, 55, 14);
			contentPanel.add(lblIntentosClasico);
		}
		{
			lblExitosClasico = new JLabel("Exitos: ");
			lblExitosClasico.setBounds(22, 190, 65, 14);
			contentPanel.add(lblExitosClasico);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
