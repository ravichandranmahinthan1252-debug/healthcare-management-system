package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {
    private final JTabbedPane tabs = new JTabbedPane();

    private final PatientsPanel patientsPanel = new PatientsPanel();
    private final CliniciansPanel cliniciansPanel = new CliniciansPanel();
    private final AppointmentsPanel appointmentsPanel = new AppointmentsPanel();
    private final PrescriptionsPanel prescriptionsPanel = new PrescriptionsPanel();
    private final ReferralsPanel referralsPanel = new ReferralsPanel();
    private final StaffPanel staffPanel = new StaffPanel();
    private final FacilityPanel facilityPanel = new FacilityPanel();

    private final JLabel status = new JLabel("Ready");

    public MainFrame() {
        super("Healthcare Management System (MVC + Singleton, Swing)");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        tabs.addTab("Patients", patientsPanel);
        tabs.addTab("Clinicians", cliniciansPanel);
        tabs.addTab("Staff", staffPanel);
        tabs.addTab("Facilities", facilityPanel);
        tabs.addTab("Appointments", appointmentsPanel);
        tabs.addTab("Prescriptions", prescriptionsPanel);
        tabs.addTab("Referrals", referralsPanel);

        add(tabs, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);

        setJMenuBar(buildMenuBar());
    }

    private JMenuBar buildMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");

        JMenuItem chooseDataDir = new JMenuItem("Choose Data Folder...");
        chooseDataDir.setActionCommand("chooseDataDir");
        file.add(chooseDataDir);

        JMenuItem chooseOutDir = new JMenuItem("Choose Output Folder...");
        chooseOutDir.setActionCommand("chooseOutDir");
        file.add(chooseOutDir);

        JMenuItem reload = new JMenuItem("Reload Data");
        reload.setActionCommand("reload");
        file.add(reload);

        file.addSeparator();
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        file.add(exit);

        bar.add(file);
        return bar;
    }

    public void setStatus(String text) { status.setText(text); }

    public PatientsPanel getPatientsPanel() { return patientsPanel; }
    public CliniciansPanel getCliniciansPanel() { return cliniciansPanel; }
    public StaffPanel getStaffPanel() { return staffPanel; }
    public FacilityPanel getFacilityPanel() { return facilityPanel; }
    public AppointmentsPanel getAppointmentsPanel() { return appointmentsPanel; }
    public PrescriptionsPanel getPrescriptionsPanel() { return prescriptionsPanel; }
    public ReferralsPanel getReferralsPanel() { return referralsPanel; }

    public void attachMenuListener(java.awt.event.ActionListener l) {
        JMenuBar bar = getJMenuBar();
        JMenu file = bar.getMenu(0);
        for (int i = 0; i < file.getItemCount(); i++) {
            JMenuItem item = file.getItem(i);
            if (item == null) continue;
            String cmd = item.getActionCommand();
            if (cmd != null && (cmd.equals("chooseDataDir") || cmd.equals("chooseOutDir") || cmd.equals("reload"))) {
                item.addActionListener(l);
            }
        }
    }

    public File chooseDirectory(String title, File initial) {
        JFileChooser fc = new JFileChooser(initial);
        fc.setDialogTitle(title);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = fc.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) return fc.getSelectedFile();
        return null;
    }

    public void showError(String message, Exception ex) {
        String m = message + (ex != null ? "\n" + ex.getMessage() : "");
        JOptionPane.showMessageDialog(this, m, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
