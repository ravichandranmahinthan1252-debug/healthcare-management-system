package view;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class FormBuilder {
    private final JPanel panel = new JPanel(new GridBagLayout());
    private final Map<String, JTextField> fields = new LinkedHashMap<>();
    private int row = 0;

    public FormBuilder addField(String label, String initial) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4,4,4,4);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0; gc.gridy = row; gc.weightx = 0;
        panel.add(new JLabel(label), gc);

        JTextField tf = new JTextField(24);
        tf.setText(initial == null ? "" : initial);

        gc.gridx = 1; gc.weightx = 1;
        panel.add(tf, gc);

        fields.put(label, tf);
        row++;
        return this;
    }

    public JPanel getPanel() { return panel; }

    public String get(String label) {
        JTextField tf = fields.get(label);
        return tf == null ? "" : tf.getText().trim();
    }
}
