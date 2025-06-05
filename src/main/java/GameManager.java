/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */

import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class GameManager {
    private static GameManager Instance = null; //Variable para contener la unica instancia en el GameManager
    
    /*
        Atributos de los datos del juego
    */
    private ArrayList<Player> Players; //Una lista de todos los jugadores registrados
    private ArrayList<String> GlobalGameLog; //Historial global de todas las partidas
    private int TotalGamesPlayed; //Contador de todas las partidas jugadas del usuario
    private int HeroesWins; //Contador de todas las victorias con el equipo de Heroes
    private int VillainWins; //Contador de todas las victorias con el equipo de Villanos
    private Player CurrentPlayer; //Para saber cual es el jugador actualmente jugando
    
    public GameManager() {
        //Inicializar todas las listas vacias
        Players = new ArrayList<>();
        GlobalGameLog = new ArrayList<>();
        TotalGamesPlayed = 0;
        HeroesWins = 0;
        VillainWins = 0;
        CurrentPlayer = null;
        
        //Creacion de usuarios de prueba solamente para el desarrollo
        CreatePlayer("Admin", "12345");
        CreatePlayer("Jugador1", "Pass1");
        CreatePlayer("Test", "Test1");
    }
    
    /*
        Metodo para tener la unica instancia de GameManager
        si no existe, crea una instancia, y si si existe, devuelve la instancia existente
    */
    public static GameManager GetInstance() {
        if (Instance == null) {
            Instance = new GameManager();
        }
        return Instance;
    }
    
    /*
        Manejo de jugadores
    */
    public boolean CreatePlayer(String Username, String Password) {
        //Verificar que el username no este vacio
        if (Username == null || Username.trim().isEmpty()) {
            return false;
        }

        //Validar que el usuario sea unico
        for (Player p : Players) {
            if (p.GetUsername().equalsIgnoreCase(Username)) {
                return false;
            }
        }
        
        //Validar que la contraseña tenga exactamente 5 caracteres
        if (Password == null || Password.length() != 5) {
            return false;
        }
        
        //Crear el nuevo jugador y agregarlo a la lista
        Player NewPlayer = new Player(Username, Password);
        Players.add(NewPlayer);
        
        System.out.println("Jugador creado: " + Username);
        return true;
    }
    
    /*
        Intentar hacer login con un usuario
    */
    public Player Login(String Username, String Password) {
        //Buscar el jugador en la lista
        for (Player p : Players) {
            if (p.GetUsername().equalsIgnoreCase(Username) && p.ValidatePassword(Password)) {
                CurrentPlayer = p; //Establecer como jugador actual
                System.out.println("Login Exitoso: " + Username);
                return p;
            }
        }
        System.out.println("Login fallido para: " + Username);
        return null; //Login fallido
        
    }
    
    /*
        Cerrar la sesion actual
    */
    public void Logout() {
        CurrentPlayer = null;
    }
    
    /*
        Obtener el jugador actualmente logueado
    */
    public Player getCurrentPlayer() {
        return CurrentPlayer;
    }
    
    /*
        Cambiar la contraseña del jugador actual
    */
    public boolean ChangePassword(String CurrentPassword, String NewPassword) {
        //Verificar que haya un jugador logueado
        if (CurrentPlayer == null) {
            return false;
        }
        
        //Verificar que la contraseña actual sea la correcta
        if (!CurrentPlayer.ValidatePassword(CurrentPassword)) {
            return false;
        }
        
        //Verificar que la nueva contraseña tenga 5 caracteres
        if (NewPassword == null || NewPassword.length() != 5) {
            return false;
        }
        
        //Cambiar la contraseña
        CurrentPlayer.SetPassword(NewPassword);
        return true;
    }
    
    /*
        Elimitar la cuenta del jugador actual
    */
    public boolean DeleteAccount(String Password) {
        //Verificar que si haya un jugador logueado
        if (CurrentPlayer == null) {
            return false;
        }
        
        //Verificar la contraseña
        if (!CurrentPlayer.ValidatePassword(Password)) {
            return false;
        }
        
        //Marcar la cuenta como inactiva
        CurrentPlayer.SetActive(false);
        return true;
    }
    
    /*
        Ranking
    */
    public ArrayList<Player> GetRanking() {
        ArrayList<Player> ActivePlayers = new ArrayList<>();
        //Filtrar solamente los jugadores activos
        for (Player p : Players) {
            if (p.IsActive()) {
                ActivePlayers.add(p);
            }
        }
        
        //Ordenar por puntos (mayor a menor)
        Collections.sort(ActivePlayers, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.GetPoints(), p1.GetPoints());
            }
        });
        
        return ActivePlayers;
    }
    
    /*
        Cuenta de los jugadores activos
    */
    public int GetActivePlayersCount() {
        int Count = 0;
        for (Player p : Players) {
            if (p.IsActive()) {
                Count++;
            }
        }
        
        return Count;
    }
    
    /*
        Cuenta todos los jugador (los activos e inactivos)
    */
    public int GetTotalPlayersCount() {
        return Players.size();
    }
    
    /*
        Obtiene el total de partidas jugadas
    */
    public int GetTotalGamesPlayed() {
        return TotalGamesPlayed;
    }
    
    /*
        Obtener las victorias del equipo de Heroes
    */
    public int getHeroesWins() {
        return HeroesWins;
    }
    
    /*
        Obtener las victorias del equipo de Villanos
    */
    public int GetVillainsWins() {
        return VillainWins;
    }
    
    /*
        Logs del juego
    */
    public void AddGameLog(String Winner, String Loser, String WinnerTeam, String LoserTeam, String EndType) {
        //Formatear la fecha actual
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String Date = sdf.format(new Date());
        String log = "";
        
        //Crear el mensaje segun el tipo de final
        switch(EndType) {
            case "TIERRA":
                log = Winner + " usando los " + WinnerTeam + " ha " + (WinnerTeam.equals("HEROES") ? "SALVADO" : "CAPTURADO") + " la TIERRA! Venciendo a " + Loser + " - " + Date;
                break;
            case "SIN_MOVIMIENTOS":
                log = Loser + " usando " + LoserTeam + " ha perdido por no tener movimientos validos disponibles ante " + Winner + " - " + Date;
                break;
            case "RETIRO":
                log = Winner + " usando " + WinnerTeam + " ha ganado ya que " + Loser + " usando " + LoserTeam + " se ha retirado del juego - " + Date;
                break;
        }
        
        //Agregar al log global
        GlobalGameLog.add(log);
        
        //Agregar a los logs de los jugadores
        Player WinnerPlayer = FindPlayerByUsername(Winner);
        Player LoserPlayer = FindPlayerByUsername(Loser);
        
        //Actualizar los datos del jugador
        if (WinnerPlayer != null) {
            WinnerPlayer.AddGameLog(log);
            WinnerPlayer.AddPoints(3); //3 Puntos por victoria
            
            //Incrementar el contador segun el equipo
            if (WinnerTeam.equals("HEROES")) {
                WinnerPlayer.IncrementGamesAsHeroes();
                HeroesWins++;
            } else {
                WinnerPlayer.IncrementGamesAsVillains();
                VillainWins++;
            }
        }
        
        //Actualizar los datos del perdedor
        if (LoserPlayer != null) {
            LoserPlayer.AddGameLog(log);
            
            //Incrementar el contador segun el equipo
            if (LoserPlayer.equals("HEROES")) {
                LoserPlayer.IncrementGamesAsHeroes();
            } else {
                LoserPlayer.IncrementGamesAsVillains();
            }
        }
        
        //Incrementar el contador total de partidas
        TotalGamesPlayed++;
    } 
    
    /*
        Buscar un jugador por su nombre de usuario
    */
    public Player FindPlayerByUsername(String Username) {
        for (Player p : Players) {
            if (p.GetUsername().equalsIgnoreCase(Username)) {
                return p;
            }
        }

        return null;

    }
    
    /*
        Obtener jugadores disponibles para jugar (excluyendo al actual)
    */
    public ArrayList<Player> GetAvailableOpponents() {
        ArrayList<Player> Opponents = new ArrayList<>();
        
        //Agregar a todos los jugadores activos excepto el actual
        for (Player p : Players) {
            if (p.IsActive() && !p.equals(CurrentPlayer)) {
                Opponents.add(p);
            }
        }
        
        return Opponents;
        
    }
}
