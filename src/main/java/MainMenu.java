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
import java.util.List;

public class MainMenu extends javax.swing.JFrame {

    private String CurrentUser;
    private BufferedImage BackgroundImage;
    private boolean IsGameInProgress;
    private final GameManager gameManager;
    
    private final AudioManager audioManager;
    
    /**
     * Creates new form MainMenu
     * @param Username
     */
    public MainMenu(String Username) {
        this.CurrentUser = Username;
        this.IsGameInProgress = false;
        this.gameManager = GameManager.GetInstance();
        
        audioManager = AudioManager.getInstance();
        audioManager.PlayMenuMusic();
        
        LoadBackgroundImage(); //Cargar imagen de fondo antes de inicializar componentes
        initComponents();
        SetupCustomization();
        
        SetupAudioCleanup();
    }
    
    /*
        Configuracion del audio y gestion de ventana
    */
    private void SetupAudioCleanup() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                //Parar musica
                if (audioManager != null) {
                    audioManager.StopMusic();
                }
            }
            
            @Override
            public void windowActivated(java.awt.event.WindowEvent windowEvent) {
                //Reanudar musica si se reactiva la ventana
                if (audioManager != null) {
                    audioManager.PlayMenuMusic();
                }
            }
        });
    }
    
    /*
        Configuraciones adicionales
    */
    
    private void SetupCustomization() {
        //Obetener datos del jugador actual
        Player CurrentPlayer = gameManager.FindPlayerByUsername(CurrentUser);
        String Title = ("MARVEL HEROES - STRATEGO | JUGADOR: " + CurrentUser);
        if (CurrentPlayer != null) {
            Title += " | PUNTOS: " + CurrentPlayer.GetPoints();
        }
        
        setTitle(Title);
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
    
    /*
        Configurar el cierre de ventana
    */
    private void SetupWindowClosing() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void WindowClosing(java.awt.event.WindowEvent WindowEvent) {
                audioManager.PlayButtonClick();
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
           Player CurrentPlayer = gameManager.FindPlayerByUsername(CurrentUser);
           String Message = "Bienvenido " + CurrentUser + "!\n";
           
            if (CurrentPlayer != null) {
                Message += "Puntos: " + CurrentPlayer.GetPoints() + "\n";
            }
            Message += "Selecciona una opcion del menu.";
            
            audioManager.PlayNotification();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
        audioManager.PlayButtonClick();
        
        String[] Options = {"PARTIDA NUEVA"};
        int Choice = JOptionPane.showOptionDialog(this, "STRATEGO - MARVEL HEROES\n\nSelecciona una opcion: ", "Stratego - Marvel Heroes", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Options, Options[0]);
        
        if (Choice == 0) {
            StartNewGame();
        }
    }//GEN-LAST:event_StrateGOButtonActionPerformed

    /*
        Evento para el boton Configuracion
    */
    private void ConfigButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfigButtonActionPerformed
        // TODO add your handling code here:
        audioManager.PlayButtonClick();
        ShowConfiguration();
    }//GEN-LAST:event_ConfigButtonActionPerformed

    /*
        Evento para el boton de Mi Perfil
    */
    private void ProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfileButtonActionPerformed
        // TODO add your handling code here:
        audioManager.PlayButtonClick();
        ShowProfileSubmenu();
    }//GEN-LAST:event_ProfileButtonActionPerformed

    /*
        Evento para el boton de Universo Marvel
    */
    private void MarvelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MarvelButtonActionPerformed
        // TODO add your handling code here:
        audioManager.PlayButtonClick();
        ShowMarvelSubmenu();
    }//GEN-LAST:event_MarvelButtonActionPerformed

    /*
        Evento para el boton de Cerrar Sesion
    */
    private void LogoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutButtonActionPerformed
        // TODO add your handling code here:
        audioManager.PlayButtonClick();
        
        int Respuesta = JOptionPane.showConfirmDialog(this, "Estas seguro de que quieres cerrar sesion?", "Cerrar sesion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (Respuesta == JOptionPane.YES_OPTION) {
            audioManager.PlayNotification();
            
            //Limpiar datos del jugador actual
            CurrentUser = null;
            
            //Parar la musica antes de cambiar la ventana
            if (audioManager != null) {
                audioManager.StopMusic();
            }
            
            //Volver al menu principal
            InitialMenu InitialMenu = new InitialMenu();
            InitialMenu.setVisible(true);
            this.dispose();
            
            System.out.println("Sesion cerrada exitosamente");
        }
    }//GEN-LAST:event_LogoutButtonActionPerformed

    
    /*
        -->     LOGICA DE FUNCIONALIDADES    <--
    */
    
    /*
        Mostrar submenu de Mi Perfil
    */
    private void ShowProfileSubmenu() {
        audioManager.PlayNotification();
        
        String[] Options = {"LOG DE MIS ULTIMOS JUEGOS", "CAMBIAR MI PASSWORD", "ELIMINAR MI CUENTA"}; //Arreglo de las opciones disponibles
        
        int Choice = JOptionPane.showOptionDialog(this, "MI PERFIL \n\nSelecciona una opcion:", "Mi Perfil", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Options, Options[0]);
        
        switch(Choice) {
            case 0: //Opcion del Log de los ultimos juegos
                audioManager.PlayButtonClick();
                ShowGameLog();
                break;
            case 1: //Opcion de cambiar contraseña
                audioManager.PlayButtonClick();
                ChangePassword();
                break;
            case 2: //Opcion de eliminar cuenta
                audioManager.PlayInvalidMove();
                DeleteAccount();
                break;
        }
    }
    
    /*
        Mostrar el submenu de Universo Marvel
    */
    private void ShowMarvelSubmenu() {
        audioManager.PlayNotification();
        
        String[] Options = {"RANKING", "BATALLAS"};
        
        int Choice = JOptionPane.showOptionDialog(this, "UNIVERSO MARVEL\n\nSelecciona una opcion:", "Universo Marvel", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Options, Options[0]);
        
        switch(Choice) {
            case 0:
                audioManager.PlayButtonClick();
                ShowRanking();
                break;
            case 1:
                audioManager.PlayVictoryHero();
                ShowBattles();
                break;
        }
    }
    
    /*
        Iniciar nuevo Juego(que miedo que presion)
    */
    private void StartNewGame() {
        audioManager.PlayNotification();
        
        int Opcion = JOptionPane.showConfirmDialog(this, "Deseas iniciar una Nueva Partida?\n" + "Esto abrira la configuracion de partida.", "Partida Nueva", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (Opcion == JOptionPane.YES_OPTION) {
            audioManager.PlayVictoryHero();
            
            JOptionPane.showMessageDialog(this, """
                                                Proximamente: Configuracion de partida nueva
                                                Aqui podras elegir:
                                                - Tipo de oponente(IA, Jugador)
                                                - Dificultad de la IA
                                                - Configuraciones especiales
                                                - Seleccion de heroes marvel""", "En desarrollo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /*
        Mostrar el log de juegos usando el GameManager
    */
    private void ShowGameLog() {
        audioManager.PlayNotification();
        
        Player CurrentPlayer = gameManager.FindPlayerByUsername(CurrentUser);
        
        if (CurrentPlayer == null) {
            audioManager.PlayInvalidMove();
            JOptionPane.showMessageDialog(this, "Error: No se pudo obtener la informacion del jugador.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
       String[] GameHistory = CurrentPlayer.GetGameLogs();
        
       
       if (GameHistory.length == 0) {
            JOptionPane.showMessageDialog(this, """
                                                LOG DE MIS ULTIMOS JUEGOS
                                                
                                                No tienes partidas registradas aun.
                                                Juega tu primera partida para comenzar tu historial!""", "Historial de Partidas", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder HistoryText = new StringBuilder();
            HistoryText.append("LOG DE MIS ULTIMOS JUEGOS\n");
            HistoryText.append("Usuario: ").append(CurrentUser).append("\n");
            HistoryText.append("Puntos totales: ").append(CurrentPlayer.GetPoints()).append("\n\n");
            HistoryText.append("Ultimas partidas:\n");
            
            for (int i = 0; i < GameHistory.length; i++) {
                HistoryText.append((i + 1)).append(". ").append(GameHistory[i]).append("\n");
            }
            
            JOptionPane.showMessageDialog(this, HistoryText.toString(), "Historial de Partidas", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /*
        Cambiar contraseña usando el GameManager
    */
    private void ChangePassword() {
        Player CurrentPlayer = gameManager.FindPlayerByUsername(CurrentUser);
        if (CurrentPlayer == null) {
            audioManager.PlayInvalidMove();
            JOptionPane.showMessageDialog(this, "Error: no se pudo obtener la informacion del jugador", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
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
            
            //Para confirmar que el usuario llene todos los campos para cambiar su contraseña
            if (Current.isEmpty() || NewPass.isEmpty() || Confirm.isEmpty()) {
                audioManager.PlayInvalidMove();
                JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //Para verificar que la contraseña actual que ponga el usuario sea la correcta
            if (!Current.equals(CurrentPlayer.GetPassword())) {
                audioManager.PlayInvalidMove();
                JOptionPane.showMessageDialog(this, "La contraseña actual es la incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //Para verificar que las contraseñas nuevas sean la misma
            if (!NewPass.equals(Confirm)) {
                audioManager.PlayInvalidMove();
                JOptionPane.showMessageDialog(this, "Las contraseñas nuevas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //Verificacion de que la contraseña sea exactamente de 5 caracteres
            if (NewPass.length() != 5) {
                audioManager.PlayInvalidMove();
                JOptionPane.showMessageDialog(this, "La nueva contraseña debe tener exactamente 5 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //Cambiar la contraseña usando el GameManager
            CurrentPlayer.SetPassword(NewPass);
            audioManager.PlayNotification();
            JOptionPane.showMessageDialog(this, "Contraseña cambiada exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /*
        Eliminar cuenta usando el GameManager
    */
    private void DeleteAccount() {
        Player CurrentPlayer = gameManager.FindPlayerByUsername(CurrentUser);
        if (CurrentPlayer == null) {
            audioManager.PlayInvalidMove();
            JOptionPane.showMessageDialog(this, "Error: No se pudo obtener la informacion del jugador", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        audioManager.PlayInvalidMove();
        
        int Option = JOptionPane.showConfirmDialog(this, """
                                                         ⚠️ ADVERTENCIA ⚠️
                                                         
                                                         Estas seguro que deseas eliminar tu cuenta?
                                                         Esta accion no se puede deshacer.
                                                         
                                                         Se perderan:
                                                         - Todos tus datos de partidas
                                                         - Tu ranking y estadisticas
                                                         - Tu progreso en el juego
                                                         
                                                         Continuar con la eliminacion?""", "Eliminar Cuenta - CONFIRMACION", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (Option == JOptionPane.YES_OPTION) {
            //Doble confirmacion porque uno nunca sabe
            String Confirmacion = JOptionPane.showInputDialog(this, "Para confirmar la elimination, escribe tu nombre de usuario:\n" + "Usuario actual: " + CurrentUser, "Confirmacion final", JOptionPane.WARNING_MESSAGE);
            
            if (Confirmacion != null && Confirmacion.equals(CurrentUser)) {
                //Marcar la cuenta como inactiva (elimina en este caso) siempre usando el GameManager
                boolean Success = gameManager.DeleteAccount(CurrentUser);
                
                if (Success) {
                    audioManager.PlayNotification();
                    JOptionPane.showMessageDialog(this, "Cuenta eliminada exitosamente.\n" + "Lamentamos verte partir.", "Cuenta Eliminada", JOptionPane.INFORMATION_MESSAGE);
                    
                    //Volver al menu inicial
                    ExitToMainMenu();
                } else {
                    audioManager.PlayInvalidMove();
                    JOptionPane.showMessageDialog(this, "Error al eliminar la cuenta\n" + "Intentalo de nuevo mas tarde.", "Error", JOptionPane.ERROR_MESSAGE);
                } 
            }else if (Confirmacion != null) {
                audioManager.PlayInvalidMove();
                JOptionPane.showMessageDialog(this, "El nombre de usuario no coincide.\n" + "Eliminacion cancelada.", "Error de Confirmacicn", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /*
        Mostrar el ranking de los jugadores
    */
    private void ShowRanking() {
        audioManager.PlayVictoryHero();
        
        Player[] AllPlayers = gameManager.GetRanking();
        
        if (AllPlayers.length == 0) {
            audioManager.PlayInvalidMove();
            JOptionPane.showMessageDialog(this, """
                                                RANKING DE JUGADORES
                                                
                                                No hay jugadores registrados en el sistema""", "Ranking Marvel Heroes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        //Arreglo temporal para jugadores activos
        Player[] ActivePlayers = new Player[AllPlayers.length];
        int ActiveCount = 0;
        
        for (int i = 0; i < AllPlayers.length; i++) {
            Player player = AllPlayers[i];
            if (player.IsActive()) {
                ActivePlayers[ActiveCount] = player;
                ActiveCount++;
            }
        }
        
        //Verificar si hay jugadores activos
        if (ActiveCount == 0) {
            audioManager.PlayInvalidMove();
            JOptionPane.showMessageDialog(this, "No hay jugadores activos en el sistema", "Ranking vacio", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        //Ordenar por puntos
        for (int i = 0; i < ActiveCount - 1; i++) {
            for (int j = 0; j < ActiveCount - 1 - i; j++) {
                if (ActivePlayers[j].GetPoints() < ActivePlayers[j + 1].GetPoints()) {
                    Player temp = ActivePlayers[j];
                    ActivePlayers[j] = ActivePlayers[j + 1];
                    ActivePlayers[j + 1] = temp;
                }
            }
        }
        
        StringBuilder RankingText = new StringBuilder(); //Un StringBuilder que me va a servir para darle correctamente el puesto en el ranking al usuario
        RankingText.append("🏆 RANKING DE JUGADORES 🏆\n\n" );
        
        int CurrentUserPosition = 0; //Para poder saber despues la posicion del jugador
        
        //for que va a estar contando desde 0 hasta el tamaño de ActivePlayers, osea que va a parar hasta que llegue al conteo de los jugadores activos, y que tambien sea menor a 10
        for (int i = 0; i < ActiveCount && i < 10 ; i++) {
            Player Player = ActivePlayers[i];
            String Position = (i + 1) + ".";
            String Medal = "";
            
            //Para darle un agregado a el top 3, con su propia medalla para quien quiera
            if (i == 0) Medal = "🥇";
            else if (i == 1) Medal = "🥈";
            else if (i == 2) Medal = "🥉";
            
            RankingText.append(String.format("%-3s %s %-15s - %d Puntos\n", Position, Medal, Player.GetUsername(), Player.GetPoints()));
            
            if (Player.GetUsername().equals(CurrentUser)) {
                CurrentUserPosition = i + 1;
            }
        }
        
        if (CurrentUserPosition > 0) {
            RankingText.append("\n🎯 Tu posición: #").append(CurrentUserPosition);
        }
        
        RankingText.append("\n\nTotal de jugadores activos: " ).append(ActiveCount);
        
        //Estadisticas globales
        RankingText.append("\n\nESTADISTICAS GLOBALES:");
        RankingText.append("\nPartidas totales: ").append(gameManager.GetTotalGamesPlayed());
        RankingText.append("\nVictorias Heroes: ").append(gameManager.getHeroesWins());
        RankingText.append("\nVictorias Villanos: ").append(gameManager.GetVillainsWins());
        
        JOptionPane.showMessageDialog(this, RankingText.toString(), "Ranking Marvel Heroes", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*
        Mostrar las epicas batallas de rap del frikismo
    */
    private void ShowBattles() {
        audioManager.PlayVictoryHero();
        
        //Mostrar las estadisticas del jugador actual
        Player CurrentPlayer = gameManager.FindPlayerByUsername(CurrentUser);
        if (CurrentPlayer == null) {
            audioManager.PlayInvalidMove();
            JOptionPane.showMessageDialog(this, "Error: No se pudo obtener la informacion del jugador", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] GameHistory = CurrentPlayer.GetGameLogs();
        int TotalGames = GameHistory.length;
        int Victories = 0;
        
        //Contar vistorias
        for (int i = 0; i < GameHistory.length; i++) {
            String Game = GameHistory[i];
            if (Game.toLowerCase().contains("victoria") || Game.toLowerCase().contains("gano")) {
                Victories++;
            }
        }
        
        int Defeats = TotalGames - Victories;
        double WinRate = TotalGames > 0 ? (Victories * 100.0 / TotalGames) : 0;
        
        StringBuilder BattleText = new StringBuilder();
        BattleText.append("⚔️ BATALLAS EPICAS ⚔️\n\n");
        BattleText.append("Estadisticas de ").append(CurrentUser).append(":\n");
        BattleText.append("• Batallas Totales: ").append(TotalGames).append(":\n");
        BattleText.append("• Victorias: ").append(Victories).append("\n");
        BattleText.append("• Derrotas: ").append(Defeats).append("\n");
        BattleText.append("• Porcentaje de vistoria: ").append(String.format("%.1f", WinRate)).append("\n");
        BattleText.append("• Puntos Totales: ").append(CurrentPlayer.GetPoints()).append("\n\n");
        BattleText.append("• Partidas con Heroes: ").append(CurrentPlayer.GetGamesWithHeroes()).append("\n");
        BattleText.append("• Partidas con Villanos: ").append(CurrentPlayer.GetGamesWithVillains()).append("\n");
        
        if (GameHistory.length == 0) {
            BattleText.append("Aun no has hecho ninguna batalla!\n");
            BattleText.append("Juega tu primera partida para comenzar tu leyenda.");
        } else {
            BattleText.append("ULTIMAS BATALLAS:\n");
            
            int MaxGames = GameHistory.length > 5 ? 5 : GameHistory.length;
            for (int i = GameHistory.length - MaxGames; i < GameHistory.length; i++) {
                BattleText.append("• ").append(GameHistory[i]).append("\n");
            }
            
            BattleText.append("\nProximamente encontraras:\n");
            BattleText.append("• Batallas historicas de Marvel\n");
            BattleText.append("• Recreacion de combates famosos\n");
            BattleText.append("• Desafios especiales\n");
            BattleText.append("• Eventos limitados\n");
            BattleText.append("• Recomensas exclusivas");
        }
        
        JOptionPane.showMessageDialog(this, BattleText.toString(), "Batallas Marvel", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*
        Mostrar configuracion
    */
    private void ShowConfiguration() {
        audioManager.PlayButtonClick();
        
        SwingUtilities.invokeLater(() -> {
            new Configuration().setVisible(true);
        });
    }
    
    /*
        Salir al menu principal osea cerrar sesion
    */
    private void ExitToMainMenu() {        
        int Option = JOptionPane.showConfirmDialog(this, "Deseas cerrar sesion y volver al menu principal?", "Cerrar sesion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (Option == JOptionPane.YES_OPTION) {
            audioManager.PlayNotification();
            
            //Parar la musica antes de cambiar ventana
            if (audioManager != null) {
                audioManager.StopMusic();
            }
            
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
            if (audioManager != null) {
                audioManager.PlayGameStart();
            }
        } else {
            setTitle("MARVEL HEROES - STRATEGO | Jugador: " + CurrentUser);
            if (audioManager != null) {
                audioManager.PlayMenuMusic();
            }
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
