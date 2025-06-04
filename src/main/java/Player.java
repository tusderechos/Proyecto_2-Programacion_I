/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */

import java.util.ArrayList;
import java.util.List;

public class Player {
    
    //Atributos basicos
    private String Username;
    private String Password;
    private int Points;
    private boolean Active;
    private List<String> GameLogs;
    
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
        this.GameLogs = new ArrayList<>();
    }
    
    /*
        Obtener el nombre de usuario
    */
    public String GetUsername() {
        return Username;
    }
    
    /*
        Cambair el nombre de usuario
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
    public List<String> GetGameLogs() {
        return GameLogs;
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
        //Si hay mas de 20 logs, se elimina el mas antiguo
        if (this.GameLogs.size() >= 20) {
            this.GameLogs.remove(0);
        }
        this.GameLogs.add(LogEntry);
    }
    
    /*
        Convertir a String para mostrar
    */
    @Override
    public String toString() {
        return Username + " (pts: " + Points + ")";
    }
}   
