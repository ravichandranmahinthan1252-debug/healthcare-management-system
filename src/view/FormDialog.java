package view;

import javax.swing.*;
import java.awt.*;

/**
 * Small helper to show a modal form dialog.
 */
public class FormDialog {
    public static boolean showConfirm(Component parent, String title, JPanel form) {
        int res = JOptionPane.showConfirmDialog(parent, form, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        return res == JOptionPane.OK_OPTION;
    }

    public static JTextField field(String value) {
        JTextField tf = new JTextField(20);
        tf.setText(value == null ? "" : value);
        return tf;
    }

    public static JPanel twoCol(String... labelAndValuePairs) {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4,4,4,4);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < labelAndValuePairs.length; i += 2) {
            gc.gridx = 0; gc.gridy = i/2; gc.weightx = 0;
            p.add(new JLabel(labelAndValuePairs[i]), gc);
            gc.gridx = 1; gc.weightx = 1;
            p.add(new JLabel(labelAndValuePairs[i+1]), gc);
        }
        return p;
    }
}
