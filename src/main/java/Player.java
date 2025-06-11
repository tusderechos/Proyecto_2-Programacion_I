/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */


public class Player {
    
    //Atributos basicos
    private String Username;
    private String Password;
    private int Points;
    private boolean Active;

    private String[] GameLogs;
    private int GameLogsCount;
    private static final int MAX_LOGS = 20;
    
    public boolean ValidatePassword(String Password) {
        return this.Password.equals(Password);
    }
    
    //Contadores
    private int GamesWithHeroes;
    private int GamesWithVillains;
    
    public Player(String Username, String Password) {
        this.Username = Username;
        this.Password = Password;
        this.Points = 0;
        this.Active = true;
        this.GamesWithHeroes = 0;
        this.GamesWithVillains = 0;
        
        this.GameLogs = new String[MAX_LOGS];
        this.GameLogsCount = 0;
    }
    
    /*
        Obtener el nombre de usuario
    */
    public String GetUsername() {
        return Username;
    }
    
    /*
        Cambiar el nombre de usuario
    */
    public void SetUsername(String Username) {
        this.Username = Username;
    }
    
    /*
        Obtener la contra actual
    */
    public String GetPassword() {
        return Password;
    }
    
    /*
        Cambiar la contra
    */
    public void SetPassword(String NewPassword) {
        this.Password = NewPassword;
    }
    
    /*
        Obtener los puntos actuales
    */
    public int GetPoints() {
        return Points;
    }

    /*
        Establecer los puntos
    */
    public void SetPoints(int Points) {
        this.Points = Points;
    }
    
    /*
        Agregar puntos
    */
    public void AddPoints(int AddPoints) {
        this.Points += AddPoints;
    }

    /*
        Verificar que la cuenta este activa
    */
    public boolean IsActive() {
        return Active;
    }

    /*
        Activa o desactivar la cuenta
    */
    public void SetActive(boolean Active) {
        this.Active = Active;
    }

    /*
        Obtener el historial de juegos
    */
    public String[] GetGameLogs() {
        String[] Result = new String[GameLogsCount];
        for (int i = 0; i < GameLogsCount; i++) {
            Result[i] = GameLogs[i];
        }
        return Result;
    }

    /*
        Obtener la cantidad de juegos jugados con el equipo de Heroes
    */
    public int GetGamesWithHeroes() {
        return GamesWithHeroes;
    }

    /*
        Establecer la cantidad de juegos con el equipo de Heroes
    */
    public void SetGamesWithHeroes(int GamesWithHeroes) {
        this.GamesWithHeroes = GamesWithHeroes;
    }
    
    /*
        Incrementar los juegos con el equipo de Heroes
    */
    public void IncrementGamesAsHeroes() {
        this.GamesWithHeroes++;
    }
    
    /*
        Obtener la cntidad de juegos jugados con el equipo de Villanos
    */
    public int GetGamesWithVillains() {
        return GamesWithVillains;
    }

    /*
        Establecer la cantidad de juegos con el equipo de Villanos
    */
    public void SetGamesWithVillains(int GamesWithVillains) {
        this.GamesWithVillains = GamesWithVillains;
    }
    
    /*
        Incrementar los juegos con el equipo de Villanos
    */
    public void IncrementGamesAsVillains() {
        this.GamesWithVillains++;
    }
    
    /*
        -->     METODOS AUXILEARES     <--
    */
    
    /*
        Comprobar si la contra ingresada coincide con la actual
    */
    public boolean CheckPassword(String Attempt) {
        return this.Password.equals(Attempt);
    }
    
    /*
        Agregar una linea de log al hostorial de partidas
        Mantiene solo los ultimos 20 registros
    */
    public void AddGameLog(String LogEntry) {
        if (GameLogsCount < MAX_LOGS) {
            //Si hay espacio, se agrega
            GameLogs[GameLogsCount] = LogEntry;
            GameLogsCount++;
        } else {
            //Si esta lleno, mover todos los espacios a la izquierda y agregar al final
            for (int i = 0; i < MAX_LOGS; i++) {
                GameLogs[i] = GameLogs[i + 1];
            }
            GameLogs[MAX_LOGS - 1] = LogEntry; //GameLogsCounts se mantiene en MAX_LOGS
        }
    }
    
    /*
        Obtener log especifico por indice
    */
    public String GetGameLog(int Indice) {
        if (Indice >= 0 && Indice < GameLogsCount) {
            return GameLogs[Indice];
        }
        return null;
    }
    
    /*
        Verificar si hay logs
    */
    public boolean HasGameLogs() {
        return GameLogsCount > 0;
    }
    
    /*
        Limpiar historial de logs
    */
    public void ClearGameLogs() {
        for (int i = 0; i < GameLogsCount; i++) {
            GameLogs[i] = null;
        }
        GameLogsCount = 0;
    }
    
    /*
        Convertir a String para mostrar
    */
    @Override
    public String toString() {
        return Username + " (pts: " + Points + ")";
    }
}   
