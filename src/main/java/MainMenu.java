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
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class MainMenu extends javax.swing.JFrame {

    private String CurrentUser;
    private BufferedImage BackgroundImage;
    private boolean IsGameInProgress;
    
    /**
     * Creates new form MainMenu
     */
    public MainMenu(String Username) {
        this.CurrentUser = Username;
        this.IsGameInProgress = false;
        
        LoadBackgroundImage(); //Cargar imagen de fondo antes de inicializar componentes
        
        initComponents();
        SetupCustomization();
    }
    
    /*
        Configuraciones adicionales
    */
    
    private void SetupCustomization() {
        setTitle("MARVEL HEROES - STRATEGO | JUGADOR: " + CurrentUser);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        //Aplicar la imagen de fondo solamente si se haya cargado
        if (BackgroundImage != null) {
            SetupBackgroundImage();
        }
        
        //Personalizar componentes
        CustomizeComponents();
        
        //Configurar el cierre de la ventana
        SetupWindowClosing();
        
        //Establecer el foco inicial
        if (StrateGOButton != null) {
            StrateGOButton.requestFocus();
        }
        
        //Mostrar mensaje de bienvenida
        ShowWelcomeMessage();
    }
    
    /*
        Cargar la imagen de fondo
    */
    private void LoadBackgroundImage() {
        try {
            BackgroundImage = ImageIO.read(getClass().getResourceAsStream("/images/menu_bg.PNG"));
            BackgroundImage = null;
            
            if (BackgroundImage != null) {
                System.out.println("Imagen de fondo del menu principal cargada exitosamente");
            } else {
                System.out.println("Probando ruta alternativa...");
                BackgroundImage = ImageIO.read(new File("src/main/resources/images/menu_bg.PNG"));
                if (BackgroundImage != null) {
                    System.out.println("imagen cargada con la ruta alternativa");
                }     
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar imagen defondo del menu principal: " + e.getMessage());
            BackgroundImage = null;
        }
    }
    
    /*
        Configurar imagen de fondo usando LayeredPane
    */
    private void SetupBackgroundImage() {
        if (BackgroundImage != null) {
            try {
                JPanel BackgroundPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        if (BackgroundImage != null) {
                            Graphics2D g2d = (Graphics2D) g.create();
                            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                            
                            //Escalar la imagen manteniendo proporcion
                            int PanelWidth = getWidth();
                            int PanelHeight = getHeight();
                            int ImageWidth = BackgroundImage.getWidth();
                            int ImageHeight = BackgroundImage.getHeight();
                            
                            double ScaleX = (double) PanelWidth / ImageWidth;
                            double ScaleY = (double) PanelHeight / ImageHeight;
                            double Scale = Math.max(ScaleX, ScaleY);
                            
                            int ScaledWidth = (int) (ImageWidth * Scale);
                            int ScaledHeight = (int) (ImageHeight * Scale);
                            int X = (PanelWidth - ScaledWidth) / 2;
                            int Y = (PanelHeight - ScaledHeight) / 2;
                            
                            g2d.drawImage(BackgroundImage, X, Y, ScaledWidth, ScaledHeight, null);
                            g2d.dispose();
                        }
                    }
                };
                
                //Configurar el Label del fondo
                BackgroundPanel.setBounds(0, 0, getWidth(), getHeight());
                BackgroundPanel.setOpaque(false);
                
                //Usando LayeredPane porque tenia el mismo problema que tenia en el menu inicial
                getLayeredPane().add(BackgroundPanel, JLayeredPane.DEFAULT_LAYER);
                
                //Agregar el LayeredPane al fondo
                getLayeredPane().setLayer(getContentPane(), JLayeredPane.PALETTE_LAYER);
                
                //Hacer que el ContentPane sea transparente para que se pueda ver el fondo
                getContentPane().setBackground(new Color(0, 0, 0, 0));
                if (getContentPane() instanceof JComponent) {
                    ((JComponent) getContentPane()).setOpaque(false);
                }
                
                //Esto es un Listener para poder dimensional
                addComponentListener(new java.awt.event.ComponentAdapter() {
                    @Override
                    public void componentResized(java.awt.event.ComponentEvent evt) {
                        BackgroundPanel.setBounds(0, 0, getWidth(), getHeight());
                        BackgroundPanel.repaint();
                    }
                });
                
                System.out.println("Imagen de fondo del menu principal aplicada correctamente");
                
            } catch (Exception e) {
                System.out.println("Error aplicando fondo del menu principal: " + e.getMessage());
            }
        }
    }
    
    /*
        Personalizar componentes creador en el Design
    */
    private void CustomizeComponents() {
        CustomizeComponentsRecursively(getContentPane());
    }
    
    /*
        Aplicar los estilos recursivamente (por como la trigesima vez)
    */
    private void CustomizeComponentsRecursively(Container Container) {
        for (Component Comp : Container.getComponents()) {
            if (Comp instanceof JButton) {
                StyleButton((JButton) Comp);
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
        
        if (Text.contains("STRATEGO") || Text.contains("MARVEL HEROES")) {
            BackgroundColor = new Color(186, 18, 31); //Un color rojo similar al que usa Marvel para darle un toque masiso
        } else if (Text.contains("CONFIGURACION") || Text.contains("SETTINGS")) {
            BackgroundColor = new Color(117, 117, 117); //Esto es un Gris
        } else if (Text.contains("MI PERFIL") || Text.contains("MY PROFILE")) {
            BackgroundColor = new Color(25, 118, 210); //Esto es un Azul
        } else if (Text.contains("UNIVERSO MARVEL") || Text.contains("MARVEL UNIVERSE")) {
            BackgroundColor = new Color(245, 124, 0); //Esto es como un Naranja/Dorado
        } else if (Text.contains("CERRAR SESION") || Text.contains("LOG OUT")) {
            BackgroundColor = new Color(0, 0, 0); //Negro para que no se mire igual al de configuracion porque antes de tenia un gris oscuro
        } else {
            BackgroundColor = new Color(100, 100, 100); //El color por defecto sera un Gris
        }
        
        Button.setFont(new Font("Arial", Font.BOLD, 14));
        Button.setBackground(BackgroundColor);
        Button.setForeground(Color.WHITE);
        Button.setFocusPainted(false);
        Button.setBorder(BorderFactory.createRaisedBevelBorder());
        Button.setPreferredSize(new Dimension(300, 45));
        
        //Agregar el bendito efecto Hover
        addHoverEffect(Button, BackgroundColor);
    }
    
    /*
        Agregar el bendito efecto Hover a los botones (he hecho esto 41238988 veces)
    */
    private void addHoverEffect(JButton Button, Color OriginalColor) {
        Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void MouseEntered(java.awt.event.MouseEvent evt) {
                Button.setBackground(OriginalColor.brighter());
            }
            
            public void MouseExited(java.awt.event.MouseEvent evt) {
                Button.setBackground(OriginalColor);
            }
        });
    }
    
    /*
        Configurar el cierre de ventana
    */
    private void SetupWindowClosing() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void WindClosing(java.awt.event.WindowEvent WindowEvent) {
                ExitToMainMenu();
            }
        });
    }
    
    /*
        Mostrar el mensaje de bienvenida
    */
    private void ShowWelcomeMessage() {
        //Un bloque de pura magia oscura magicamente magica
        Timer Timer = new Timer(1000, e -> {
           JOptionPane.showMessageDialog(this, "Bienvenido " + CurrentUser + "!\n" + "Selecciona una opcion del menu.", "Bienvenido a Marvel Heroes - Stratego", JOptionPane.INFORMATION_MESSAGE);
        });
        
        Timer.setRepeats(false);
        Timer.start();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StrateGOButton = new javax.swing.JButton();
        ConfigButton = new javax.swing.JButton();
        ProfileButton = new javax.swing.JButton();
        MarvelButton = new javax.swing.JButton();
        LogoutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        StrateGOButton.setText("STRATEGO - MARVEL HEROES!");
        StrateGOButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StrateGOButtonActionPerformed(evt);
            }
        });

        ConfigButton.setText("CONFIGURACION");
        ConfigButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfigButtonActionPerformed(evt);
            }
        });

        ProfileButton.setText("MI PERFIL");
        ProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProfileButtonActionPerformed(evt);
            }
        });

        MarvelButton.setText("UNIVERSO MARVEL");
        MarvelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MarvelButtonActionPerformed(evt);
            }
        });

        LogoutButton.setText("CERRAR SESION");
        LogoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LogoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MarvelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ConfigButton, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(StrateGOButton, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(148, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addComponent(StrateGOButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ConfigButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(MarvelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(LogoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*
        -->     METODOS PARA EVENTOS     <--
    */
    
    /*
        Evento para el boton de Stratego - Marvel Heroes
    */
    private void StrateGOButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StrateGOButtonActionPerformed
        // TODO add your handling code here:
        ShowStrategoSubmenu();
    }//GEN-LAST:event_StrateGOButtonActionPerformed

    /*
        Evento para el boton Configuracion
    */
    private void ConfigButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfigButtonActionPerformed
        // TODO add your handling code here:
        ShowConfiguration();
    }//GEN-LAST:event_ConfigButtonActionPerformed

    /*
        Evento para el boton de Mi Perfil
    */
    private void ProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfileButtonActionPerformed
        // TODO add your handling code here:
        ShowProfileSubmenu();
    }//GEN-LAST:event_ProfileButtonActionPerformed

    /*
        Evento para el boton de Universo Marvel
    */
    private void MarvelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MarvelButtonActionPerformed
        // TODO add your handling code here:
        ShowMarvelSubmenu();
    }//GEN-LAST:event_MarvelButtonActionPerformed

    /*
        Evento para el boton de Cerrar Sesion
    */
    private void LogoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutButtonActionPerformed
        // TODO add your handling code here:
        ExitToMainMenu();
    }//GEN-LAST:event_LogoutButtonActionPerformed

    
    /*
        -->     LOGICA DE FUNCIONALIDADES    <--
    */
    
    /*
        Mostrar el submenu de Stratego - Marvel Heroes
    */
    private void ShowStrategoSubmenu() {
        String[] Options = {"PARTIDA NUEVA"}; //Arreglo de las opciones disponibles
        
        int Choice = JOptionPane.showOptionDialog(this, "STRATEGO - MARVEL HEROES\n\nSelecciona una opcion:", "Stratego - Marvel Heroes", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Options, Options[0]);
        if (Choice == 0) {
            StartNewGame();
        }
    }
    
    /*
        Mostrar submenu de Mi Perfil
    */
    private void ShowProfileSubmenu() {
        String[] Options = {"LOG DE MIS ULTIMOS JUEGOS", "CAMBIAR MI PASSWORD", "ELIMINAR MI CUENTA"}; //Arreglo de las opciones disponibles
        
        int Choice = JOptionPane.showOptionDialog(this, "MI PERFIL \n\nSelecciona una opcion:", "Mi Perfil", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Options, Options[0]);
        
        switch(Choice) {
            case 0: //Opcion del Log de los ultimos juegos
                ShowGameLog();
                break;
            case 1: //Opcion de cambiar contraseña
                ChangePassword();
                break;
            case 2: //Opcion de eliminar cuenta
                DeleteAccount();
                break;
        }
    }
    
    /*
        Mostrar el submenu de Universo Marvel
    */
    private void ShowMarvelSubmenu() {
        String[] Options = {"RANKING", "BATALLAS"};
        
        int Choice = JOptionPane.showOptionDialog(this, "UNIVERSO MARVEL\n\nSelecciona una opcion:", "Universo Marvel", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Options, Options[0]);
        
        switch(Choice) {
            case 0:
                ShowRanking();
                break;
            case 1:
                ShowBattles();
                break;
        }
    }
    
    /*
        Iniciar nuevo Juego(que miedo que presion)
    */
    private void StartNewGame() {
        int Opcion = JOptionPane.showConfirmDialog(this, "Deseas iniciar una Nueva Partida?\n" + "Esto abrira la configuracion de partida.", "Partida Nueva", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (Opcion == JOptionPane.YES_OPTION) {
            //Tengo que abrir la ventana de juego
            
            JOptionPane.showMessageDialog(this, "Proximamente: Configuracion de partida nueva\n" + "Aqui podras elegir:\n" + "- Tipo de oponente(IA, Jugador)\n" + "- Dificultad de la IA\n" + "- Configuraciones especiales\n" + "- Seleccion de heroes marvel", "En desarrollo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /*
        Mostrar el log de juegos
    */
    private void ShowGameLog() {
        //Tengo que implementar el historial de partidas
        JOptionPane.showMessageDialog(this, "LOG DE MIS ULTIMOS PARTIDOS\n\n" + "Proximamente veras aqui:\n" + "- Historial de tus ultimas 20 partidas\n" + "- Resultado (Victoria/Derrota)\n" + "- Fecha y duracion\n" + "- Oponente enfrentado\n" + "- Heroes utilizados\n", "Historial de Partidas", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*
        Cambiar contraseña
    */
    private void ChangePassword() {
        JPanel Panel = new JPanel(new java.awt.GridLayout(3, 2, 10, 10)); //3 filas, 2 columnas, 10 pixeles de espacio vertical y 10 pixeles de espacio horizontal
        JPasswordField CurrentPassword = new JPasswordField(15);
        JPasswordField NewPassword = new JPasswordField(15);
        JPasswordField ConfirmPassword = new JPasswordField(15);
        
        Panel.add(new JLabel("Contraseña actual:"));
        Panel.add(CurrentPassword);
        Panel.add(new JLabel("Nueva contraseña:"));
        Panel.add(NewPassword);
        Panel.add(new JLabel("Confirmar nueva contraseña:"));
        Panel.add(ConfirmPassword);
        
        int Result = JOptionPane.showConfirmDialog(this, Panel, "Cambiar Contraseña", JOptionPane.OK_CANCEL_OPTION);
        
        if (Result == JOptionPane.OK_OPTION) {
            String Current = new String(CurrentPassword.getPassword());
            String NewPass = new String(NewPassword.getPassword());
            String Confirm = new String(ConfirmPassword.getPassword());
            
            if (Current.isEmpty() || NewPass.isEmpty() || Confirm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!NewPass.equals(Confirm)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas nuevas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (NewPass.length() < 5) {
                JOptionPane.showMessageDialog(this, "La nueva contraseña debe tener al menos 5 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //Me falta validar la contraseña actual y cambiarla
            JOptionPane.showMessageDialog(this, "Contraseña cambiada exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /*
        Eliminar cuenta
    */
    private void DeleteAccount() {
        int Option = JOptionPane.showConfirmDialog(this, "⚠️ ADVERTENCIA ⚠️\n\n" + "Estas seguro que deseas eliminar tu cuenta?\n" + "Esta accion no se puede deshacer.\n\n" + "Se perderan:\n" + "- Todos tus datos de partidas\n" + "- Tu ranking y estadisticas\n" + "- Tu progreso en el juego\n\n" + "Continuar con la eliminacion?", "Eliminar Cuenta - CONFIRMACION", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (Option == JOptionPane.YES_OPTION) {
            //Doble confirmacion porque uno nunca sabe
            String Confirmacion = JOptionPane.showInputDialog(this, "Para confirmar la elimination, escribe tu nombre de usuario:\n" + "Usuario actual: " + CurrentUser, "Confirmacion final", JOptionPane.WARNING_MESSAGE);
            
            if (Confirmacion != null && Confirmacion.equals(CurrentUser)) {
                //Tengo que hacer que literalmente se elimite la cuenta del sistema
                JOptionPane.showMessageDialog(this, "Cuenta eliminada exitosamente.\n" + "Lamentamos verte partir.", "Cuenta Eliminada", JOptionPane.INFORMATION_MESSAGE);
                
                //Volver al menu inicial
                ExitToMainMenu();
            }else if (Confirmacion != null) {
                JOptionPane.showMessageDialog(this, "El nombre de usuario no coincide.\n" + "Eliminacion cancelada.", "Error de Confirmacicn", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /*
        Mostrar el ranking de los jugadores
    */
    private void ShowRanking() {
        //Tengo que implementar el sistema de ranking
        JOptionPane.showMessageDialog(this,
            "RANKING DE JUGADORES\n\n" +
            "Proximamente veras aqui:\n" + "- Top 10 mejores jugadores\n" + "- Puntuacion y nivel\n" + "- Partidas ganadas/perdidas\n" + "- Tu posicion actual\n" + "- Estadisticas detalladas", "Ranking Marvel Heroes", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*
        Mostrar las epicas batallas de rap del frikismo
    */
    private void ShowBattles() {
        JOptionPane.showMessageDialog(this,
            "BATALLAS ÉPICAS\n\n" +
            "Proximamente encontraras:\n" + "- Batallas historicas de Marvel\n" + "- Recreacion de combates famosos\n" + "- Desafios especiales\n" + "- Eventos limitados\n" + "- Recompensas exclusivas", "Batallas Marvel", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*
        Mostrar configuracion
    */
    private void ShowConfiguration() {
        //Tengo que implementar el panel de configuracion
        JOptionPane.showMessageDialog(this,
            "Proximamente: Panel de Configuracion\n" + "Aqui podras configurar:\n" + "- Volumen de sonidos\n" + "- Resolucion de pantalla\n" + "- Efectos visuales\n" + "- Controles de teclado", "En Desarrollo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*
        Salir al menu principal osea cerrar sesion
    */
    private void ExitToMainMenu() {
        int Option = JOptionPane.showConfirmDialog(this, "Deseas cerrar sesion y volver al menu principal?", "Cerrar sesion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (Option == JOptionPane.YES_OPTION) {
            dispose(); //Para cerrar la ventana
            
            //Volver al menu principal
            SwingUtilities.invokeLater(() -> {
                new InitialMenu().setVisible(true);
            });
        }
    }
    
    
    /*
        -->     METODOS PUBLICOS     <--
    */
    
    /*
        Obtener el usuario actual
    */
    public String GetCurrentUser() {
        return CurrentUser;
    }
    
    /*
        Verificar si hay juego en progreso
    */
    public boolean IsGameInProgress() {
        return IsGameInProgress;
    }
    
    /*
        Establecer el estado del juego en progreso
    */
    public void SetGameInProgress(boolean InProgress) {
        this.IsGameInProgress = InProgress;
        
        //Actualizar titulo de ventana
        if (InProgress) {
            setTitle("MARVEL HEROES - STRATEGO | " + CurrentUser + " - Jugando");
        } else {
            setTitle("MARVEL HEROES - STRATEGO | Jugador: " + CurrentUser);
        }
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ConfigButton;
    private javax.swing.JButton LogoutButton;
    private javax.swing.JButton MarvelButton;
    private javax.swing.JButton ProfileButton;
    private javax.swing.JButton StrateGOButton;
    // End of variables declaration//GEN-END:variables
}
