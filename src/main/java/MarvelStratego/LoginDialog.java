package MarvelStratego;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Hp
 */

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends javax.swing.JDialog {

    private final JFrame ParentFrame;
    private String AuthenticatedUser;
    private boolean LoginSuccessful;
    
    private final AudioManager audioManager;
    
    /**
     * Creates new form LoginDialog
     * @param Parent
     */
    public LoginDialog(JFrame Parent) {
        super(Parent, true); //Modal Dialog
        this.ParentFrame = Parent;
        this.AuthenticatedUser = null;
        this.LoginSuccessful = false;
        
        audioManager = AudioManager.getInstance();
        
        
        initComponents();
        SetupCustomization();
        
        SetupAudioCleanup();
    }
    
    /*
        Cleanup del audio al cerrar
    */
    private void SetupAudioCleanup() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (audioManager != null) {
                    audioManager.StopMusic();
                }
            }
        });
    }
    
    //Configuraciones adicionales despues del initComponents() porque uno nunca sabe
    private void SetupCustomization() {
        setTitle("MARVEL HEROES - LOGIN");
        setLocationRelativeTo(ParentFrame);
        setResizable(false);
        
        
        CustomizeComponents(); //Aplicar los estilos personalizados
        
        getRootPane().setDefaultButton(LoginButton); //Configurar boton por defecto
        
        //Que se centre inicialmente en el campor de Username
        if (UsernameField != null) {
            UsernameField.requestFocus();
        }
    }
    
    //Personalizar los componentes creados en el Design
    private void CustomizeComponents() {
        CustomizeComponentsRecursively(getContentPane()); //Buscar y personalizar componentes por nombre
    }
    
    //Aplicar estilos recursivamente
    private void CustomizeComponentsRecursively(Container Container) {
        for (Component Comp : Container.getComponents()) {
            if (Comp instanceof JButton) {
                StyleButton((JButton) Comp);
            } else if (Comp instanceof JTextField || Comp instanceof JPasswordField) {
                StyleTextField(Comp);
            } else if (Comp instanceof JLabel) {
                StyleLabel((JLabel) Comp);
            } else if (Comp instanceof Container) {
                CustomizeComponentsRecursively((Container) Comp);
            }
        }
    }
    
    //Aplicar estilos a los botones
    private void StyleButton(JButton Button) {
        String Text = Button.getText().toUpperCase();
        Color BackgroundColor;
        
        if (Text.contains("INICIAR SESION") || Text.contains("LOG IN") || Text.contains("LOGIN")) {
            BackgroundColor = new Color(70, 130, 180);
        } else if (Text.contains("CANCELAR") || Text.contains("CANCEL")) {
            BackgroundColor = new Color(150, 150, 150);
        } else {
            BackgroundColor = new Color(100, 100, 100);
        }
        
        Button.setFont(new Font("Arial", Font.BOLD, 12));
        Button.setBackground(BackgroundColor);
        Button.setForeground(Color.WHITE);
        Button.setFocusPainted(false);
        Button.setBorder(BorderFactory.createRaisedBevelBorder());
        
        //Efecto Hover bien tuani
        addHoverEffect(Button, BackgroundColor);
    }
    
    //Aplicar el estilo a los campos de texto
    private void StyleTextField(Component TextField) {
        TextField.setFont(new Font("Arial", Font.PLAIN, 14));
    }
    
    //Aplicar el estilo a los Labels
    private void StyleLabel(JLabel Label) {
        String Text = Label.getText().toUpperCase();
        
        if (Text.contains("INICIAR SESION") || Text.contains("LOGIN")) {
            Label.setFont(new Font("Arial", Font.BOLD, 18));
            Label.setForeground(new Color(50, 50, 50));
        } else {
            Label.setFont(new Font("Arial", Font.BOLD, 14));
        }
    }
    
    //Agregar el efecto Hover a los botones
    private void addHoverEffect(JButton Button, Color OriginalColor) {
        Button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                audioManager.PlayButtonHover();
                Button.setBackground(OriginalColor.brighter());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Button.setBackground(OriginalColor);
            }
        });
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoginButton = new javax.swing.JButton();
        UsernameField = new javax.swing.JTextField();
        PasswordField = new javax.swing.JPasswordField();
        CancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        LoginButton.setBackground(new java.awt.Color(0, 204, 51));
        LoginButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LoginButton.setForeground(new java.awt.Color(0, 0, 0));
        LoginButton.setText("INICIAR SESION");
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });

        UsernameField.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        UsernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsernameFieldActionPerformed(evt);
            }
        });
        UsernameField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UsernameFieldKeyPressed(evt);
            }
        });

        PasswordField.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        PasswordField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordFieldActionPerformed(evt);
            }
        });
        PasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PasswordFieldKeyPressed(evt);
            }
        });

        CancelButton.setBackground(new java.awt.Color(204, 0, 0));
        CancelButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        CancelButton.setForeground(new java.awt.Color(255, 255, 255));
        CancelButton.setText("CANCELAR");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("INICIO DE SESION");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(UsernameField, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(PasswordField)
                            .addComponent(LoginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel1)))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(UsernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(LoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void UsernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsernameFieldActionPerformed
        // TODO add your handling code here:
        audioManager.PlayButtonHover();
        PasswordField.requestFocus();
    }//GEN-LAST:event_UsernameFieldActionPerformed

    private void PasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordFieldActionPerformed
        // TODO add your handling code here:
        audioManager.PlayButtonHover();
    }//GEN-LAST:event_PasswordFieldActionPerformed

    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        // TODO add your handling code here:
        audioManager.PlayButtonClick();
        AttemptLogin();
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        // TODO add your handling code here:
        audioManager.PlayButtonClick();
        CancelLogin();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void UsernameFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsernameFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            audioManager.PlayButtonHover();
            PasswordField.requestFocus();
        }
    }//GEN-LAST:event_UsernameFieldKeyPressed

    private void PasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            audioManager.PlayButtonHover();
            AttemptLogin();
        }
    }//GEN-LAST:event_PasswordFieldKeyPressed

    
    /*
        Logica de Autenticacion
    */
    //Intentar hacer un Login
    private void AttemptLogin() {
        String Username = UsernameField.getText().trim();
        String Password = new String(PasswordField.getPassword());
        
        //Validar los campos vacios
        if (Username.isEmpty()) {
            showError("Por favor ingrese su nombre de usuario");
            UsernameField.requestFocus();
            return;
        }
        
        if (Password.isEmpty()) {
            showError("Por favor ingrese su contraseña");
            PasswordField.requestFocus();
            return;
        }
        
        //Validar las credenciales que da el usuario
        if (ValidateCredentials(Username, Password)) {
            //Para un login Exitoso
            AuthenticatedUser = Username;
            LoginSuccessful = true;
            
            showSuccess("Bienvenido " + Username + "!");
            dispose();
        } else {
            showError("Usuario o contraseña incorrectos");
            PasswordField.setText(""); //Para limpiar la contraseña
            UsernameField.requestFocus();
            UsernameField.selectAll();
        }
    }
    
    //Cancelar el Login
    private void CancelLogin() {
        AuthenticatedUser = null;
        LoginSuccessful = false;
        dispose();
    }
    
    //Validar las credenciales hoy si
    private boolean ValidateCredentials(String Username, String Password) {
        //Validar que el nombre de usuario y la contraseña no esten vacias
        if (Username == null || Username.trim().isEmpty() || Password == null || Password.trim().isEmpty()  ) {
            return false;
        }
        
        //Usar el GameManager para validar
        GameManager gameManager = GameManager.GetInstance();
        Player LoggedPlayer = gameManager.Login(Username, Password);
        
        if (LoggedPlayer != null) {
            return true; //Un login exitoso, hace que el GameManager ya estableza el CurrentPlayer
        } else {
            return false; //Login fallido
        }
    }
    
    //Mostrar un mensaje de error
    private void showError(String Mensaje) {
        audioManager.PlayInvalidMove();
        JOptionPane.showMessageDialog(this, Mensaje, "Error de Login", JOptionPane.ERROR_MESSAGE);
    }
    
    //Mostrar un mensaje de exito
    private void showSuccess(String Mensaje) {
        audioManager.PlayNotification();
        JOptionPane.showMessageDialog(this, Mensaje, "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*
        Metodos Publicos
    */
    //Mostrar el dialogo y que haga un return del usuario autenticado
    public String ShowDialog() {
        //Limpiar los campos
        UsernameField.setText("");
        PasswordField.setText("");
        
        //Resetear el estado
        AuthenticatedUser = null;
        LoginSuccessful = false;
        
        //Hacerle un focus inicial al campo de usuario
        if (UsernameField != null) {
            UsernameField.requestFocus();
        }
        
        //Mostrar Dialogo
        setVisible(true);
        
        //Hacer un return del resultado
        return LoginSuccessful ? AuthenticatedUser : null;
    }
    
    //Verificar si el login fue exitoso
    public boolean IsLoginSuccessful() {
        return LoginSuccessful;
    }
    
    //Obetener el usuario autenticado
    public String GetAuthenticatedUser() {
        return AuthenticatedUser;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton LoginButton;
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JTextField UsernameField;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
