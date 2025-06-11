/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameManager {
    private static GameManager Instance = null; //Variable para contener la unica instancia en el GameManager
    
    //Constantes para tamaño maximo
    private static final int MAX_PLAYERS = 100;
    private static final int MAX_GLOBAL_LOGS = 1000;
    
    /*
        Atributos de los datos del juego
    */
    private Player[] Players; //Arreglo de todos los jugadores registrados
    private int PlayersCount; //Contador de jugadores en el arreglo
    private String[] GlobalGameLog; //Historial global de todas las partidas
    private int GlobalLogCount; //Contador de logs globales
    private int TotalGamesPlayed; //Contador de todas las partidas jugadas del usuario
    private int HeroesWins; //Contador de todas las victorias con el equipo de Heroes
    private int VillainWins; //Contador de todas las victorias con el equipo de Villanos
    private Player CurrentPlayer; //Para saber cual es el jugador actualmente jugando
    
    public GameManager() {
        //Inicializar todas las listas vacias
        Players = new Player[MAX_PLAYERS];
        PlayersCount = 0;
        GlobalGameLog = new String[MAX_GLOBAL_LOGS];
        GlobalLogCount = 0;
        TotalGamesPlayed = 0;
        HeroesWins = 0;
        VillainWins = 0;
        CurrentPlayer = null;
        
        LoadPlayers();
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
        if (FindPlayerByUsername(Username) != null) {
            return false; //Por si ya existe el usuario
        }
        
        if (Password.length() != 5) {
            return false;
        }
        
        //Verificar que no se exceda el limite de jugadores
        if (PlayersCount >= MAX_PLAYERS) {
            return false;
        }

        //Validar que el usuario sea unico
        for (int i = 0; i < PlayersCount; i++) {
            if (Players[i].GetUsername().equalsIgnoreCase(Username)) {
                return false;
            }
        }
        
        //Validar que la contraseña tenga exactamente 5 caracteres
        if (Password == null || Password.length() != 5) {
            return false;
        }
        
        //Crear el nuevo jugador y agregarlo a la lista
        Player NewPlayer = new Player(Username, Password);
        Players[PlayersCount] = NewPlayer;
        PlayersCount++;
        
        SavePlayers(); //Guardar en archivo
        
        System.out.println("Jugador creado: " + Username);
        return true;
    }
    
    /*
        Validar el login
    */
    public boolean ValidateLogin(String Username, String Password) {
        Player player = FindPlayerByUsername(Username);
        
        if (player != null && player.IsActive() && player.CheckPassword(Password)) {
            return true; //Login exitoso
        }
        return false; //Login fallido
    }
    
    /*
        Intentar hacer login con un usuario
    */
    public Player Login(String Username, String Password) {
        //Buscar el jugador en la lista
        for (int i = 0; i < PlayersCount; i++) {
            Player p = Players[i];
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
        SavePlayers();
        return true;
    }
    
    /*
        Elimitar la cuenta del jugador actual
    */
    public boolean DeleteAccount(String Username) {
        Player Player = FindPlayerByUsername(Username);
        if (Player != null) {
            return false;
        }
        
        Player.SetActive(false); //Marcar como inactivo
        SavePlayers(); //Guardar cambios
        
        //Si es el jugador actual, hacer un logout
        if (CurrentPlayer != null && CurrentPlayer.equals(Player)) {
            CurrentPlayer = null;
        }
        return true;
    }
    
    /*
        Ranking
    */
    public Player[] GetRanking() {
        //Crear arreglo temporal para jugadores activos
        Player[] ActivePlayers = new Player[PlayersCount];
        int ActiveCount = 0;
        
        //Filtrar solamente los jugadores activos
        for (int i = 0; i < PlayersCount; i++) {
            if (Players[i].IsActive()) {
                ActivePlayers[ActiveCount] = Players[i];
                ActiveCount++;
            }
        }
        
        //Ordenar por puntos
        for (int i = 0; i < ActiveCount - 1; i++) {
            for (int j = 0; j < ActiveCount - 1 - i; j++) {
                if (ActivePlayers[i].GetPoints() < ActivePlayers[j + 1].GetPoints()) {
                    Player Temp = ActivePlayers[i];
                    ActivePlayers[j] = ActivePlayers[j + 1];
                    ActivePlayers[j + 1] = Temp;
                }
            }
        }
        
        //Crear arreglo del tamaño exacto para hacer un return
        Player[] Result = new Player[ActiveCount];
        for (int i = 0; i < ActiveCount; i++) {
            Result[i] = ActivePlayers[i];
        }
        return Result;
        
    }
    
    /*
        Cuenta de los jugadores activos
    */
    public int GetActivePlayersCount() {
        int Count = 0;
        for (int i = 0; i < PlayersCount; i++) {
            if (Players[i].IsActive()) {
                Count++;
            }
        }
        
        return Count;
    }
    
    /*
        Cuenta todos los jugador (los activos e inactivos)
    */
    public int GetTotalPlayersCount() {
        return PlayersCount;
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
        //Verificar limite de logs globales
        if (GlobalLogCount >= MAX_GLOBAL_LOGS) {
            return;
        }
        
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
        GlobalGameLog[GlobalLogCount] = log;
        GlobalLogCount++;
        
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
            if (LoserTeam.equals("HEROES")) {
                LoserPlayer.IncrementGamesAsHeroes();
            } else {
                LoserPlayer.IncrementGamesAsVillains();
            }
        }
        
        TotalGamesPlayed++; //Incrementar el contador total de partidas
        
        SavePlayers(); //Guardar cambios
    } 
    
    /*
        Buscar un jugador por su nombre de usuario
    */
    public Player FindPlayerByUsername(String Username) {
        if (Username == null) {
            return null;
        }
        for (int i = 0; i < PlayersCount; i++) {
            if (Players[i].GetUsername().equalsIgnoreCase(Username)) {
                return Players[i];
            }
        }

        return null;

    }
    
    /*
        Obtener jugadores disponibles para jugar (excluyendo al actual)
    */
    public Player[] GetAvailableOpponents() {
        //Crear arreglo temporal
        Player[] Opponents = new Player[PlayersCount];
        int OpponentCount = 0;
        
        //Agregar a todos los jugadores activos excepto el actual
        for (int i = 0; i < PlayersCount; i++) {
            Player p = Players[i];
            if (p != null && p.IsActive() && !p.equals(CurrentPlayer)) {
                Opponents[OpponentCount] = p;
                OpponentCount++;
            }
        }
        
        //Crear arreglo del tamaño exacto para hacer un return
        Player[] Result = new Player[OpponentCount];
        for (int i = 0; i < OpponentCount; i++) {
            Result[i] = Opponents[i];
        }
        
        return Result;
        
    }
    
    /*
        Obtener log globales
    */
    public String[] GetLogsGlobales() {
        String[] Resultado = new String[GlobalLogCount];
        for (int i = 0; i < GlobalLogCount; i++) {
            Resultado[i] = GlobalGameLog[i];
        }
        return Resultado;
        
    }
    
    /*
        Cargar jugadores desde archivo
    */
    private void LoadPlayers() {
        
    }
    
    /*
        Guardar en archivo
    */
    private void SavePlayers() {
        try {
            System.out.println("datos guardados en memoria");
        } catch (Exception e) {
            System.out.println("error al guardar: " + e.getMessage());
        }
    }
    
    /*
        Limpiar todos los datos
    */
    public void LimpiarDatos() {
        //Limpiar Usuarios
        for (int i = 0; i < PlayersCount; i++) {
            Players[i] = null;
        }
        
        PlayersCount = 0;
        
        //Limpiar logs globales
        for (int i = 0; i < GlobalLogCount; i++) {
            GlobalGameLog[i] = null;
        }
        GlobalLogCount = 0;
        
        //Reiniciar contadores
        TotalGamesPlayed = 0;
        HeroesWins = 0;
        VillainWins = 0;
        CurrentPlayer = null;
    }
    
    /*
        Verificar si se puede iniciar una partida
    */
    public boolean CanStartGame() {
        return GetActivePlayersCount() >= 2;
    }
    
    /*
        Obtener informacion del jugador actual
    */
    public String GetCurrentPlayerInfo() {
        if (CurrentPlayer == null) {
            return "No hay jugador logueado";
        }
        
        return "Usuario: " + CurrentPlayer.GetUsername() + "\nPuntos: " + CurrentPlayer.GetPoints() + "\nJuegos como Heroes: " + CurrentPlayer.GetGamesWithHeroes() + "\nJuegos como Villanos: " + CurrentPlayer.GetGamesWithVillains();
    }
}
