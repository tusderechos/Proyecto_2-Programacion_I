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


public class ShowCreatePlayerDialog extends javax.swing.JDialog {
    private final AudioManager audioManager;
    private final GameManager gameManager;

    private JFrame ParentFrame;
    private String CreatedUsername;
    private boolean PlayerCreated;
    
    /**
     * Creates new form ShowCreatePlayerDialog
     * @param Parent
     */
    public ShowCreatePlayerDialog(JFrame Parent) {
        super(Parent, true); //Modal Dialog
        this.ParentFrame = Parent;
        this.CreatedUsername = null;
        this.PlayerCreated = false;
        
        this.gameManager = GameManager.GetInstance();
        
        initComponents();
        SetupCustomization();
        
        this.audioManager = AudioManager.getInstance();
        audioManager.PlayMenuMusic();
        
        SetupAudioCleanup();
    }
    
    /*
        Metodo par poder parar la musica
    */
    private void SetupAudioCleanup() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                audioManager.StopMusic();
            }
        });
    }
    
    /*
        Configuraciones adicionales
    */
    private void SetupCustomization() {
        setTitle("MARVEL HEROES - CREAR JUGADOR");
        setLocationRelativeTo(null);
        setResizable(false);
        
        //Aplicar estilos personalizados
        CustomizeComponents();
        
        //Configurar boton por defecto
        getRootPane().setDefaultButton(CreateButton);
        
        //Darle un focus inicial al campo de Username
        if (UsernameField != null) {
            UsernameField.requestFocus();
        }
    }

    /*
        Aqui se personalizan los componentes que estan en el Design
    */
    private void CustomizeComponents() {
        CustomizeComponentsRecursively(getContentPane()); //Para buscar y personalizar cada componente por nombre
    }
    
    /*
        Aplicar estilos recursivamente
    */
    private void CustomizeComponentsRecursively(Container Container) {
        for(Component Comp : Container.getComponents()) {
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
    
    /*
        Aplicar estilo a los botones
    */
    private void StyleButton(JButton Button) {
        String Text = Button.getText().toUpperCase();
        Color BackgroundColor;
        
        if (Text.contains("CREAR JUGADOR") || Text.contains("CREATE PLAYER")) {
            BackgroundColor = new Color(34, 139, 34); //Un color Verde
        } else if (Text.contains("CANCEL") || Text.contains("CANCELAR")) {
            BackgroundColor = new Color(150, 150, 150); //Un color Gris
        } else {
            BackgroundColor = new Color(100, 100, 100);
        }
        
        Button.setFont(new Font("Arial", Font.BOLD, 12));
        Button.setBackground(BackgroundColor);
        Button.setForeground(Color.WHITE);
        Button.setFocusPainted(false);
        Button.setBorder(BorderFactory.createRaisedBevelBorder());
        
        //El increible efecto Hover
        addHoverEffect(Button, BackgroundColor);
    }
    
    /*
        Aplicarle estilos a los campos de texto
    */
    private void StyleTextField(Component TextField) {
        TextField.setFont(new Font("Arial", Font.PLAIN, 18));
    }
    
    /*
        Aplicarle el estilo a los Labels
    */
    private void StyleLabel(JLabel Label) {
        String Text = Label.getText().toUpperCase();
        
        if (Text.contains("CREAR JUGADOR") || Text.contains("CREATE PLAYER")) {
            Label.setFont(new Font("Arial", Font.BOLD, 18));
            Label.setForeground(new Color(50, 50, 50));
        } else {
            Label.setFont(new Font("Arial", Font.BOLD, 14));
        }
    }
    
    /*
        Agregar el efecto de Hover a los botones
    */
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

        Titulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        UsernameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        PasswordField = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        ConfirmPasswordField = new javax.swing.JPasswordField();
        CreateButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Titulo.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        Titulo.setText("CREAR NUEVO JUGADOR");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Usuario:");

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

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Contraseña:");

        PasswordField.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        PasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PasswordFieldKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Confirmar contraseña:");

        ConfirmPasswordField.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ConfirmPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ConfirmPasswordFieldKeyPressed(evt);
            }
        });

        CreateButton.setText("CREAR JUGADOR");
        CreateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateButtonActionPerformed(evt);
            }
        });

        CancelButton.setText("CANCELAR");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(UsernameField, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                    .addComponent(PasswordField)
                    .addComponent(ConfirmPasswordField)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Titulo)
                        .addGap(10, 10, 10)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(CreateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(Titulo)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(UsernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ConfirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
        Evento para el boton de crear jugador
    */
    private void CreateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateButtonActionPerformed
        // TODO add your handling code here:
        audioManager.PlayButtonClick();
        AttemptCreatePlayer();
    }//GEN-LAST:event_CreateButtonActionPerformed

    /*
        Evento para el boton de cancelar
    */
    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        // TODO add your handling code here:
        audioManager.PlayButtonClick();
        CancelCreation();
    }//GEN-LAST:event_CancelButtonActionPerformed

    /*
        Evento para cuando se le da enter en el campo de Username
    */
    private void UsernameFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsernameFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            audioManager.PlayButtonHover();
            PasswordField.requestFocus();
        }
    }//GEN-LAST:event_UsernameFieldKeyPressed

    /*
        Evento para cuando se le da enter en el campo de contrasena
    */
    private void PasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            audioManager.PlayButtonHover();
            ConfirmPasswordField.requestFocus();
        }
    }//GEN-LAST:event_PasswordFieldKeyPressed

    /*
        Evento para cuando se le da enter en el campo de confirmar contrasena
    */
    private void ConfirmPasswordFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ConfirmPasswordFieldKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            audioManager.PlayButtonHover();
            AttemptCreatePlayer();
        }
    }//GEN-LAST:event_ConfirmPasswordFieldKeyPressed

    private void UsernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsernameFieldActionPerformed

    /*
        -->     LOGICA PARA CREAR EL JUGADOR     <--
    */
    
    /*
        Intentar crear el jugador
    */
    private void AttemptCreatePlayer() {
        String Username = UsernameField.getText().trim();
        String Password = new String(PasswordField.getPassword());
        String ConfirmPassword = new String(ConfirmPasswordField.getPassword());
        
        //Validar los campos vacios
        if (Username.isEmpty()) {
            showError("Por favor ingrese un nombre de usuario");
            UsernameField.requestFocus();
            return;
        }
        
        if (Password.isEmpty()) {
            showError("Por favor ingrese una contraseña");
            PasswordField.requestFocus();
            return;
        }
        
        if (ConfirmPassword.isEmpty()) {
            showError("Por favor confirme su contraseña");
            ConfirmPasswordField.requestFocus();
            return;
        }
        
        //Validar la longitud minima
        if (Username.length() < 3) {
            showError("El nombre de usuario debe tener al menos 3 caracteres");
            UsernameField.requestFocus();
            UsernameField.selectAll();
            return;
        }
        
        if (Password.length() != 5) {
            showError("La contraseña debe tener exactamente 5 caracteres");
            PasswordField.requestFocus();
            PasswordField.selectAll();
            return;
        }
        
        //Validar que las contraseñas sean la misma
        if (!Password.equals(ConfirmPassword)) {
            showError("Las contraseñas no son las mismas");
            ConfirmPasswordField.requestFocus();
            ConfirmPasswordField.selectAll();
            return;
        }
        
        //Validar los caracteres especiales en el usuario
        if (!Username.matches("[a-zA-Z0-9_]+$")) {
            showError("El usuario solo puede contener letras, numero y guion bajo");
            UsernameField.requestFocus();
            UsernameField.selectAll();
            return;
        }
        
        //Verificar si el usuario ya exite
        if (UsernameExists(Username)) {
            showError("El nombre de usuario " + Username + " ya esta en uso");
            UsernameField.requestFocus();
            UsernameField.selectAll();
            return;
        }
        
        //Crear el jugador
        if (CreateNewPlayer(Username, Password)) {
            audioManager.PlayVictoryHero(); //Sonido epico de exito
            
            //Por si la creacion del jugador es exitosa
            CreatedUsername = Username;
            PlayerCreated = true;
            
            showSuccess("Jugador " + Username + " creado exitosamente!\n" + "Ahora puedes iniciar sesion con tu nueva cuenta.");
        
            dispose();
        } else {
            showError("Error al crear el jugador. Intentalo nuevamente");
        }
    }
    
    /*
        Cancelar la creacion del jugador
    */
    private void CancelCreation() {
        CreatedUsername = null;
        PlayerCreated = false;
        dispose();
    }
    
    /*
        Verificar si el usuario ya existe
    */
    private boolean UsernameExists(String Username) {
        Player ExistingPlayer = gameManager.FindPlayerByUsername(Username);
        return ExistingPlayer != null;
    }
    
    /*
        Crear nuevo jugador
    */
    private boolean CreateNewPlayer(String Username, String Password) {
        //Implementacion temporal
        //Aqui estaria conectando con el UserManager pero no lo he hecho

        try {
            System.out.println("Creando jugador: ");
            System.out.println("- Usuario: " + Username);
            System.out.println("- Contraseña: [PROTEGIDA]");
            
            boolean Success = gameManager.CreatePlayer(Username, Password);
            
            if (Success) {
                System.out.println("Jugador creado exitosamente");
            } else {
                System.out.println("Error: no se pudo crear el jugador");
            }
            
            return Success;
        } catch (Exception e) {
            System.out.println("Error creando jugador: " + e.getMessage());
            return false;
        }
    }
    
    /*
        Mostrar un mensaje de error
    */
    private void showError(String Message) {
        audioManager.PlayInvalidMove();
        JOptionPane.showMessageDialog(this, Message, "Error de Validacion", JOptionPane.ERROR_MESSAGE);
    }
    
    /*
        Mostrar un mensaje de exito
    */
    private void showSuccess(String Message) {
        audioManager.PlayNotification();
        JOptionPane.showMessageDialog(this, Message, "Jugador Creado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    /*
        -->     METODOS PUBLICOS     <--
    */
    
    /*
        Mostrar el dialogo y que haga un return con el usuario creado
    */
    
    public String ShowDialog() {
        //Limpiar los campos
        UsernameField.setText("");
        PasswordField.setText("");
        ConfirmPasswordField.setText("");
        
        //Resetear el estado de la creacion de jugador
        CreatedUsername = null;
        PlayerCreated = false;
        
        //Focus inicial
        if (UsernameField != null) {
            UsernameField.requestFocus();
        }
        
        //Mostrar el dialogo
        setVisible(true);
        
        //Aqui ya se hace el return
        return PlayerCreated ? CreatedUsername : null;
    }
    
    /*
        Verificar si la creacion fue exitosa
    */
    public boolean IsPlayerCreated() {
        return PlayerCreated;
    }
    
    /*
        Obtener el usuario creado
    */
    public String GetCreatedUsername() {
        return CreatedUsername;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JPasswordField ConfirmPasswordField;
    private javax.swing.JButton CreateButton;
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JLabel Titulo;
    private javax.swing.JTextField UsernameField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
