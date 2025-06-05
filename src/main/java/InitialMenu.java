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
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

//Panel personalizado con imagen de fondo
class BackgroundPanel extends JPanel {
    private BufferedImage BackgroundImage;
    
    public BackgroundPanel(BufferedImage BackgroundImage) {
        this.BackgroundImage = BackgroundImage;
        setOpaque(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (BackgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            
            //Dibujar la imagen escalada al tamaño del panel
            int PanelWidth = getWidth();
            int PanelHeight = getHeight();
            int ImageWidth = BackgroundImage.getWidth();
            int ImageHeight = BackgroundImage.getHeight();

            //Calcular escala para que la imagen cubra todo el panel
            double ScaleX = (double) PanelWidth / ImageWidth;
            double ScaleY = (double) PanelHeight / ImageHeight;
            double Scale = Math.max(ScaleX, ScaleY); //Usar la escala mayor para cubrir todo

            //Calcular posicion centrada
            int ScaleWidth = (int) (ImageWidth * Scale);
            int ScaleHeight = (int) (ImageHeight * Scale);
            int x = (PanelWidth - ScaleWidth) / 2;
            int y = (PanelHeight - ScaleHeight) / 2;

            //Escalar la imagen para que cubra todo el panel y este centrada
            g2d.drawImage(BackgroundImage, x, y, ScaleWidth, ScaleHeight, null);
        } else {
            g.setColor(new Color(30, 30, 30)); //Gris Oscuro
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}

//Menu de Inicio del juego
public class InitialMenu extends JFrame {
    
    //Variables para tener una funcionalidad personalizada y que se mire masiso
    private BackgroundPanel MainBackgroundPanel;
    private BufferedImage BackgroundImage;
    
    /**
     * Creates new form InitialMenu
     */
    
    public InitialMenu() {
        initComponents();
        SetupBackgroundandStyles();
        
        Timer RepaintTimer = new Timer(100, e -> {
            setVisible(false);
            setVisible(true);
            repaint();
            revalidate();
        });
        
        RepaintTimer.setRepeats(false);
        RepaintTimer.start();
    }
    
    private void SetupBackgroundandStyles() {
        LoadBackgroundImage(); //Cargar imagen de fondo
        
        //Configurar ventana
        setTitle("Marvel Heroes - Stratego Game");
        setLocationRelativeTo(null);
        setResizable(false);
        
        //Aqui solo estoy poniendo un bloque que agregue el fondo solamente si se cargo correctamente
        if (BackgroundImage != null) {
            SetupBackgroundImage();
        }
        
        //Aqui aplico estilos a los componntes que vaya agregando en el Design
        StyleComponents();
        
        //Forzar un repintado porque me estaba dando un error
        SwingUtilities.invokeLater(() -> {
            repaint();
            revalidate();
            
            //Forzar el repintando de todos los componentes
            repaintAllComponents(getContentPane());
        });
    }
    
    /*
        Forzar el repintado de todos los componentes
    */
    private void repaintAllComponents(Container Container) {
        for (Component Comp : Container.getComponents()) {
            Comp.repaint();
            if (Comp instanceof Container) {
                repaintAllComponents((Container) Comp);
            }
        }
    }
    
    //Cargar la imagen de fondo
    private void LoadBackgroundImage() {
        try {
            BackgroundImage = ImageIO.read(getClass().getResourceAsStream("/images/menu_bg.PNG"));
            
            if (BackgroundImage != null) {
                System.out.println("Imagen de fondo cargada exitosamente.");
            } else {
                //Ruta alternativa porque uno nunca sabe
                BackgroundImage = ImageIO.read(new File("src/main/resources/images/menu_bg.PNG"));
                if (BackgroundImage != null) {
                    System.out.println("Imagen cargada con la ruta alternativa");
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar imagen de fondo: " + e.getMessage());
            BackgroundImage = null;
        }
    }
    
    //Configurar imagen de fondo
    private void SetupBackgroundImage() {
        if (BackgroundImage != null) {
            try {
                //Estoy usando el JPanel para hacer un panel personalizado para el fondo porque este bendito fondo de menu principal no quiere cooperar
                JPanel BackgroundPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        if (BackgroundImage != null) {
                            Graphics2D g2d = (Graphics2D) g.create();
                            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                            
                            g2d.drawImage(BackgroundImage, 0, 0, getWidth(), getHeight(), null);
                            g2d.dispose();
                        }
                    }
                };
                
                //Aqui configuro el panel de fondo
                BackgroundPanel.setBounds(0, 0, getWidth(), getHeight());
                BackgroundPanel.setOpaque(false);
                
                //LayeredPane porque esta imagen de fondo funcionara porque asi lo dicta la Palabra de Dios
                getLayeredPane().add(BackgroundPanel, JLayeredPane.DEFAULT_LAYER);
                
                //Aqui aseguro que el ContentPane este encima
                getLayeredPane().setLayer(getContentPane(), JLayeredPane.PALETTE_LAYER);
                
                //Hacer que el ContentPane sea transparente para ver el fondo
                if (getContentPane() instanceof JComponent) {
                    ((JComponent) getContentPane()).setOpaque(false);
                }
                
                //Listener para redimensionar
                addComponentListener(new java.awt.event.ComponentAdapter() {
                    @Override
                    public void componentResized(java.awt.event.ComponentEvent evt) {
                        BackgroundPanel.setBounds(0, 0, getWidth(), getHeight());
                        BackgroundPanel.repaint();
                    }
                });
                
                System.out.println("Imagen de fondo aplicada correctamente.");
                
            } catch (Exception e) {
                System.out.println("Error aplicando fondo: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    //Aplicar estilo a componentes que vaya agregando en el Design
    private void StyleComponents() {
        StyleComponentsRecursively(getContentPane()); //Busco los componentes por su nombre y aplico los estilos
        
        //Forzar la visibilidad de los botones
        if (LoginButton != null) {
            LoginButton.setVisible(true);
            LoginButton.repaint();
        }
        
        if (CreatePlayerButton != null) {
            CreatePlayerButton.setVisible(true);
            CreatePlayerButton.repaint();
        }
        
        if (ExitButton != null) {
            ExitButton.setVisible(true);
            ExitButton.repaint();
        }
    }
    
    //Aplicar estilos recursivamente a todos los componentes
    private void StyleComponentsRecursively(Container Container) {
        for (Component Comp : Container.getComponents()) {
            //Aplicar estilos segun el tipo de componente
            if (Comp instanceof JButton) {
                StyleButton((JButton) Comp);
            } else if (Comp instanceof JLabel) {
                StyleLabel((JLabel) Comp);
            } else if (Comp instanceof Container) {
                //Aqui continuo recursivamente con los contenedores
                StyleComponentsRecursively((Container) Comp);
            }
        }
    }
    
    //El estilo de los botones
    private void StyleButton (JButton Button) {
        Button.setFont(new Font("Arial", Font.BOLD, 16));
        Button.setForeground(Color.white);
        Button.setFocusPainted(false);
        Button.setBorder(BorderFactory.createRaisedBevelBorder());
        
        //Colores segun el texto del boton
        String Text = Button.getText().toUpperCase();
        Color BackgroundColor;
        
        if (Text.contains("LOGIN") || Text.contains("LOG IN") || Text.contains("INICIAR SESION")) {
            BackgroundColor = new Color(70, 130, 180, 200); //Este color es un Azul pero transparente para que se mire masiso
        } else if (Text.contains("CREATE PLAYER") || Text.contains("CREAR JUGADOR")) {
            BackgroundColor = new Color(34, 139, 34, 200); //Este color es un Verde pero transparente para que se mire masiso
        } else if (Text.contains("EXIT") || Text.contains("SALIR")) {
            BackgroundColor = new Color(178, 34, 34, 200); //Este color es un Rojo pero transparente para que se mire masiso
        } else {
            BackgroundColor = new Color(100, 100, 100, 200); //Aqui pongo que por defecto el color de los botones sea un Gris pero transparente
        }
        
        Button.setBackground(BackgroundColor);
        Button.setOpaque(true);
        
        //Efecto para que los botones floten y se mire bien otro royo que me tomo como 10 siglos hacer
        addHoverEffect(Button, BackgroundColor);
    }
    
    //Aplicar estilo a los Labels
    private void StyleLabel(JLabel Label) {
        String Text = Label.getText().toUpperCase();
        
        //Estilo para los titulos principales
        if (Text.contains("MARVELHEROES") || Text.contains("MARVEL HEROES")) {
            Label.setFont(new Font("Arial", Font.BOLD, 28));
            Label.setForeground(Color.WHITE);
            Label.setHorizontalAlignment(JLabel.CENTER);
            
            //Estilo para los subtitulos
        } else if (Text.contains("STRATEGOGAME") || Text.contains("STRATEGO GAME")) { 
            Label.setFont(new Font("Arial", Font.ITALIC, 18));
            Label.setForeground(Color.WHITE);
            Label.setHorizontalAlignment(JLabel.CENTER);
            
            //Este else es por si acaso hay otros Labels
        } else {
            Label.setForeground(Color.WHITE);
        }
    }
    
    //Agregar el efecto de Hover a los botones
    private void addHoverEffect(JButton Button, Color OriginalColor) {
        Button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Color BrighterColor = new Color(Math.min(255, OriginalColor.getRed() + 30), Math.min(255, OriginalColor.getGreen() + 30), Math.min(255, OriginalColor.getBlue() + 30), OriginalColor.getAlpha());
                Button.setBackground(BrighterColor);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Button.setBackground(OriginalColor);
            }
        });
    }
    
    /*
        OpenMainMenu
    */
    private void OpenMainMenu(String Username) {
    System.out.println("Abriendo menu principal para usuario: " + Username);
    
        try {
            //Cerrar la ventana actual
            dispose();

            //Crear y mostrar el menu principal
            MainMenu MainMenu = new MainMenu(Username);
            MainMenu.setVisible(true);

            System.out.println("Menú principal abierto exitosamente para: " + Username);

        } catch (Exception e) {
            System.out.println("Error abriendo menu principal: " + e.getMessage());
            e.printStackTrace();

            //Si hay error, mostrar mensaje y volver a abrir InitialMenu
            JOptionPane.showMessageDialog(null, "Error abriendo el menú principal.\n" + "Detalles: " + e.getMessage() + "\n\n" + "Por favor, inténtalo nuevamente.", "Error - Menú Principal", JOptionPane.ERROR_MESSAGE);

            // Reabrir InitialMenu si hay error
            SwingUtilities.invokeLater(() -> {
                try {
                    new InitialMenu().setVisible(true);
                } catch (Exception ex) {
                    System.out.println("Error critico reabriendo InitialMenu: " + ex.getMessage());
                    System.exit(1);
                }
            });
        }
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
        CreatePlayerButton = new javax.swing.JButton();
        ExitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        LoginButton.setText("LOGIN");
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });

        CreatePlayerButton.setText("CREAR JUGADOR");
        CreatePlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreatePlayerButtonActionPerformed(evt);
            }
        });

        ExitButton.setText("SALIR");
        ExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CreatePlayerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ExitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LoginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(LoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(CreatePlayerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(ExitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Evento de Crear Jugador
    private void CreatePlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreatePlayerButtonActionPerformed
        // TODO add your handling code here:
        ShowCreatePlayerDialog createDialog = new ShowCreatePlayerDialog(this);
        String NewUsername = createDialog.ShowDialog();

        //Si la creacion fue exitosa, mostrar opcion para hacer un login automatico
        if (NewUsername != null) {
            System.out.println("Jugador creado exitosamente: " + NewUsername);

            int Option = JOptionPane.showConfirmDialog(this, "Jugador '" + NewUsername + "' creado exitosamente!\n\n" + "Deseas iniciar sesion automaticamente con tu nueva cuenta?", "Jugador Creado - Login Automatico", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (Option == JOptionPane.YES_OPTION) {
                System.out.println("Iniciando sesion automatica para: " + NewUsername);

                //Mostrar mensaje de transicion
                JOptionPane.showMessageDialog(this, "¡Bienvenido " + NewUsername + "!\n" + "Accediendo al menú principal...", "Sesión Iniciada", JOptionPane.INFORMATION_MESSAGE);

                //Abrir el menu principal directamente
                OpenMainMenu(NewUsername);
            } else {
                System.out.println("Usuario eligió no hacer login automático");
            }
        } else {
            System.out.println("Creación de jugador cancelada");
        }        
    }//GEN-LAST:event_CreatePlayerButtonActionPerformed

    //Evento de Iniciar Sesion
    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        // TODO add your handling code here:
        LoginDialog LoginDialog = new LoginDialog(this);
        String AuthenticatedUser = LoginDialog.ShowDialog();
        
        if (AuthenticatedUser != null) {
            System.out.println("Login exitoso para usuario: " + AuthenticatedUser);
            
            //Mostrar mensaje de exito
            JOptionPane.showMessageDialog(this, "Bienvenido " + AuthenticatedUser + "!\n" + "Accediendo al menu principal...", "Login exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            //Abrir el menu principal
            OpenMainMenu(AuthenticatedUser);
        } else {
            System.out.println("Login cancelado por el usuario");
        }        
    }//GEN-LAST:event_LoginButtonActionPerformed

    //Evento de Salir
    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButtonActionPerformed
        // TODO add your handling code here:
        int Respuesta = JOptionPane.showConfirmDialog(this, "Estas seguro de que quieres salir del juego?", "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (Respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_ExitButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InitialMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InitialMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InitialMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InitialMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InitialMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CreatePlayerButton;
    private javax.swing.JButton ExitButton;
    private javax.swing.JButton LoginButton;
    // End of variables declaration//GEN-END:variables
}
