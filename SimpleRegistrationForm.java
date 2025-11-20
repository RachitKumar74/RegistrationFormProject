import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleRegistrationForm extends JFrame implements ActionListener {

    private JLabel lblTitle;
    private JLabel lblName, lblEmail, lblPassword, lblGender, lblCountry;
    private JTextField txtName, txtEmail;
    private JPasswordField txtPassword;
    private JRadioButton rbMale, rbFemale;
    private ButtonGroup genderGroup;
    private JComboBox<String> cbCountry;
    private JButton btnRegister, btnReset, btnThemeToggle;
    private JCheckBox cbShowPassword;
    private boolean darkTheme = true;

    private final String[] countries = {"Select Country", "India", "USA", "UK", "Canada", "Australia"};

    // DARK palette
    private final Color DARK_BG = new Color(13, 20, 30);
    private final Color PANEL_BG = new Color(22, 32, 48);
    // brighter accent (teal) so it pops on dark
    private final Color ACCENT = new Color(0, 150, 136);
    private final Color INPUT_BG = new Color(30, 40, 55);
    private final Color INPUT_FG = Color.WHITE;
    private final Color BUTTON_BG = new Color(0, 130, 110);
    private final Color BUTTON_FG = Color.WHITE;
    private final Color FOOTER_FG = new Color(170, 180, 190);

    // LIGHT palette
    private final Color L_BG = Color.WHITE;
    private final Color L_PANEL = new Color(245, 247, 250);
    private final Color L_ACCENT = new Color(10, 95, 212);
    private final Color L_INPUT_BG = Color.WHITE;
    private final Color L_INPUT_FG = Color.BLACK;
    private final Color L_BUTTON_BG = new Color(10, 95, 212);
    private final Color L_BUTTON_FG = Color.WHITE;
    private final Color L_FOOTER_FG = new Color(90, 95, 105);

    public SimpleRegistrationForm() {
        setTitle("Registration Form - Themed (Modified)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // Header: larger title + prominent theme button
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(12, 12, 12, 12));
        header.setOpaque(false);

        lblTitle = new JLabel("Registration Form");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.add(lblTitle, BorderLayout.WEST);

        // Theme toggle button (made very visible)
        btnThemeToggle = new JButton("Switch to Light");
        btnThemeToggle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnThemeToggle.setPreferredSize(new Dimension(150, 36));
        btnThemeToggle.setFocusPainted(false);
        btnThemeToggle.setOpaque(true);
        btnThemeToggle.setBorderPainted(false);
        btnThemeToggle.addActionListener(e -> toggleTheme());
        header.add(btnThemeToggle, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Form panel
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(20, 22, 20, 22));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("SansSerif", Font.PLAIN, 14);

        lblName = new JLabel("Name:");
        lblName.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.22;
        form.add(lblName, gbc);

        txtName = new JTextField();
        txtName.setToolTipText("Enter full name");
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.78;
        form.add(txtName, gbc);

        lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.22;
        form.add(lblEmail, gbc);

        txtEmail = new JTextField();
        txtEmail.setToolTipText("example@domain.com");
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.78;
        form.add(txtEmail, gbc);

        lblPassword = new JLabel("Password:");
        lblPassword.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.22;
        form.add(lblPassword, gbc);

        JPanel passPanel = new JPanel(new BorderLayout(6, 0));
        txtPassword = new JPasswordField();
        txtPassword.setToolTipText("Minimum 4 characters");
        passPanel.add(txtPassword, BorderLayout.CENTER);
        cbShowPassword = new JCheckBox("Show");
        cbShowPassword.setFocusable(false);
        cbShowPassword.addActionListener(e -> {
            if (cbShowPassword.isSelected()) txtPassword.setEchoChar((char) 0);
            else txtPassword.setEchoChar('\u2022');
        });
        passPanel.add(cbShowPassword, BorderLayout.EAST);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.78;
        form.add(passPanel, gbc);

        lblGender = new JLabel("Gender:");
        lblGender.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.22;
        form.add(lblGender, gbc);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.78;
        form.add(genderPanel, gbc);

        lblCountry = new JLabel("Country:");
        lblCountry.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.22;
        form.add(lblCountry, gbc);

        cbCountry = new JComboBox<>(countries);
        

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 0.78;
        form.add(cbCountry, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        btnRegister = new JButton("Register");
        btnRegister.setPreferredSize(new Dimension(120, 36));
        btnRegister.setMnemonic(KeyEvent.VK_R);
        btnRegister.addActionListener(this);
        btnReset = new JButton("Reset");
        btnReset.setPreferredSize(new Dimension(100, 36));
        btnReset.setMnemonic(KeyEvent.VK_E);
        btnReset.addActionListener(e -> clearForm());
        buttons.add(btnReset);
        buttons.add(btnRegister);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        form.add(buttons, gbc);

        add(form, BorderLayout.CENTER);

        JLabel footer = new JLabel("Tip: Switch theme to change look | Data saved to registrations.csv");
        footer.setBorder(new EmptyBorder(8, 12, 10, 12));
        add(footer, BorderLayout.SOUTH);

        // Apply initial theme
        applyTheme(darkTheme);
        setVisible(true);
    }

    private void toggleTheme() {
        darkTheme = !darkTheme;
        applyTheme(darkTheme);
        btnThemeToggle.setText(darkTheme ? "Switch to Light" : "Switch to Dark");
    }

    private void applyTheme(boolean dark) {
        if (dark) {
            getContentPane().setBackground(DARK_BG);
            lblTitle.setForeground(Color.WHITE);
            // style button prominently with ACCENT
            styleButton(btnThemeToggle, ACCENT, Color.WHITE);
            styleButton(btnRegister, BUTTON_BG, BUTTON_FG);
            styleButton(btnReset, new Color(70, 80, 100), Color.WHITE);

            applyRecursiveStyle(this.getContentPane(), PANEL_BG, INPUT_BG, INPUT_FG, ACCENT, FOOTER_FG);
        } else {
            getContentPane().setBackground(L_BG);
            lblTitle.setForeground(Color.BLACK);
            styleButton(btnThemeToggle, L_ACCENT, Color.WHITE);
            styleButton(btnRegister, L_BUTTON_BG, L_BUTTON_FG);
            styleButton(btnReset, new Color(220, 220, 220), Color.BLACK);

            applyRecursiveStyle(this.getContentPane(), L_PANEL, L_INPUT_BG, L_INPUT_FG, L_ACCENT, L_FOOTER_FG);
        }
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void styleButton(JButton b, Color bg, Color fg) {
        b.setBackground(bg);
        b.setForeground(fg);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
    }

    private void applyRecursiveStyle(Container root, Color panelBg, Color inputBg, Color inputFg, Color accent, Color footerColor) {
        for (Component comp : root.getComponents()) {
            styleComponent(comp, panelBg, inputBg, inputFg, accent, footerColor);
        }
    }

    private void styleComponent(Component comp, Color panelBg, Color inputBg, Color inputFg, Color accent, Color footerColor) {
        if (comp instanceof JPanel) {
            comp.setBackground(panelBg);
            for (Component c : ((JPanel) comp).getComponents()) styleComponent(c, panelBg, inputBg, inputFg, accent, footerColor);
        } else if (comp instanceof JLabel) {
            ((JLabel) comp).setForeground(inputFg == Color.WHITE ? Color.WHITE : Color.BLACK);
            comp.setBackground(panelBg);
        } else if (comp instanceof JTextField) {
            ((JTextField) comp).setBackground(inputBg);
            ((JTextField) comp).setForeground(inputFg);
            ((JTextField) comp).setCaretColor(inputFg);
            ((JTextField) comp).setBorder(BorderFactory.createLineBorder(accent.darker()));
        } else if (comp instanceof JPasswordField) {
            ((JPasswordField) comp).setBackground(inputBg);
            ((JPasswordField) comp).setForeground(inputFg);
            ((JPasswordField) comp).setCaretColor(inputFg);
            ((JPasswordField) comp).setBorder(BorderFactory.createLineBorder(accent.darker()));
        } else if (comp instanceof JButton) {
            // leave buttons styled individually
        } else if (comp instanceof JComboBox) {
            comp.setBackground(inputBg);
            comp.setForeground(inputFg);
        } else if (comp instanceof JCheckBox || comp instanceof JRadioButton) {
            ((AbstractButton) comp).setOpaque(false);
            ((AbstractButton) comp).setForeground(inputFg);
        } else if (comp instanceof Container) {
            for (Component c : ((Container) comp).getComponents()) styleComponent(c, panelBg, inputBg, inputFg, accent, footerColor);
        }

        if (comp instanceof JLabel) {
            String txt = ((JLabel) comp).getText();
            if (txt != null && txt.contains("Tip:")) ((JLabel) comp).setForeground(footerColor);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) handleRegister();
    }

    private void handleRegister() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String gender = rbMale.isSelected() ? "Male" : rbFemale.isSelected() ? "Female" : "";
        String country = (String) cbCountry.getSelectedItem();

        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Please enter your name.", "Validation", JOptionPane.WARNING_MESSAGE); txtName.requestFocus(); return; }
        if (!email.matches(".+@.+\\..+")) { JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Validation", JOptionPane.WARNING_MESSAGE); txtEmail.requestFocus(); return; }
        if (password.length() < 4) { JOptionPane.showMessageDialog(this, "Password should be at least 4 characters.", "Validation", JOptionPane.WARNING_MESSAGE); txtPassword.requestFocus(); return; }
        if (gender.isEmpty()) { JOptionPane.showMessageDialog(this, "Select gender.", "Validation", JOptionPane.WARNING_MESSAGE); return; }
        if (country == null || "Select Country".equals(country)) { JOptionPane.showMessageDialog(this, "Select a country.", "Validation", JOptionPane.WARNING_MESSAGE); return; }

        if (saveToCSV(name, email, gender, country)) {
            String message = "Registration Successful!\n\nName: " + name + "\nEmail: " + email + "\nGender: " + gender + "\nCountry: " + country;
            JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Could not save data. Check file permissions.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean saveToCSV(String name, String email, String gender, String country) {
        String file = "registrations.csv";
        try (FileWriter fw = new FileWriter(file, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            name = name.replace(",", " ");
            email = email.replace(",", " ");
            gender = gender.replace(",", " ");
            country = country.replace(",", " ");
            fw.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n", timestamp, name, email, gender, country));
            fw.flush();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        genderGroup.clearSelection();
        cbCountry.setSelectedIndex(0);
        cbShowPassword.setSelected(false);
        txtPassword.setEchoChar('\u2022');
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new SimpleRegistrationForm());
    }
}