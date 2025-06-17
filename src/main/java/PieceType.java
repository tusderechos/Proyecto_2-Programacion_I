/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

/**
 *
 * @author Hp
 */
import java.util.List;

//Enum para tipos de piezas
enum PieceType {
    //HEROES
    //Rango 10
    MR_FANTASTIC(10, "Mr. Fantastic", true),
    
    //Rango 9
    CAPTAIN_AMERICA(9, "Captain America", true),
    
    //Rando 8
    PROFESSOR_X(8, "Professor X",true),
    NICK_FURY(8, "Nick Fury", true),
    
    //Rango 7
    SPIDER_MAN(7, "Spider-Man", true),
    WOLVERINE(7, "Wolverine", true),
    NAMOR(7, "Namor", true),
    
    //Rango 6
    DAREDEVIL(6, "Daredevil", true),
    SILVER_SURFER(6, "Silver Surfer", true),
    HULK(6, "Hulk", true),
    IRON_MAN(6, "Iron Man", true),
    
    //Rango 5
    THOR(5, "Thor", true),
    HUMAN_TORCH(5, "Human Torch", true),
    CYCLOPS(5, "Cyclops", true),
    INVISIBLE_WOMAN(5, "Invisible Woman", true),
    
    //Rango 4
    GHOST_RIDER(4, "Ghost Rider", true),
    PUNISHER(4, "Punisher", true),
    BLADE(4, "Blade", true),
    THE_THING(4, "The Thing", true),
    
    //Rango 3
    EMMA_FROST(3, "Emma Frost", true),
    SHE_HULK(3, "She Hulk", true),
    GIANT_MAN(3, "Giant Man", true),
    BEAST(3, "Beast", true),
    COLOSSUS(3, "Colossus", true),
    
    //Rango 2
    GAMBIT(2, "Gambit", true),
    SPIDER_GIRL(2, "Spider-Girl", true),
    ICE_MAN(2, "Ice Man", true),
    STORM(2, "Storm", true),
    PHOENIX(2, "Pheonix", true),
    DR_STRANGE(2, "Dr. Strange", true),
    ELEKTRA(2, "Elektra", true),
    NIGHTCRAWLER(2, "Nightcrawler", true),
    
    //Rango 1
    BLACK_WIDOW(1, "Black Widow", true),
    
    //Bombas
    NOVA_BLAST_1(-1, "Nova Blast", true),
    NOVA_BLAST_2(-1, "Nova Blast", true),
    NOVA_BLAST_3(-1, "Nova Blast", true),
    NOVA_BLAST_4(-1, "Nova Blast", true),
    NOVA_BLAST_5(-1, "Nova Blast", true),
    NOVA_BLAST_6(-1, "Nova Blast", true),
    
    //Bandera
    PLANET_EARTH(-2, "Planet Earth", true),
    
    //VILANOS
    //Rango 10
    DR_DOOM(10, "Dr. Doom", false),
    
    //Rango 9
    GALACTUS(9, "Galactus", false),
    
    //Rango 8
    KINGPIN(8, "Kingpin", false),
    MAGNETO(8, "Magneto", false),
    
    //Rango 7
    APOCALYPSE(7, "Apocalypse", false),
    GREEN_GOBLIN(7, "Green Goblin", false),
    VENOM(7, "Venom", false),
    
    //Rango 6
    BULLSEYE(6, "Bullseye", false),
    OMEGA_RED(6, "Omega Red", false),
    ONSLAUGHT(6, "Onslaught", false),
    RED_SKULL(6, "Red Skull", false),
    
    //Rango 5
    MYSTIQUE(5, "Mystique", false),
    MYSTERIO(5, "Mysterio", false),
    DR_OCTOPUS(5, "Dr. Octopus", false),
    DEADPOOL(5, "Deadpool", false),
    
    //Rango 4
    ABOMINATION(4, "Abomination", false),
    THANOS(4, "Thanos", false),
    BLACK_CAT(4, "Black Cat", false),
    SABRETOOTH(4, "Sabretooth", false),
    
    //Rango 3
    JUGGERNAUT(3, "Juggernaut", false),
    RHINO(3, "Rhino", false),
    CARNAGE(3, "Carnage", false),
    MOLE_MAN(3, "Mole Man", false),
    LIZARD(3, "Lizard", false),
    
    //Rango 2
    MR_SINISTER(2, "Mr. Sinister", false),
    SENTINEL_1(2, "Sentinel", false),
    ULTRON(2, "Ultron", false),
    SANDMAN(2, "Sandman", false),
    LEADER(2, "Leader", false),
    VIPER(2, "Viper", false),
    SENTINEL_2(2, "Sentinel", false),
    ELECTRO(2, "Electro", false),
    
    //Rango 1
    BLACK_WIDOW_VILLAIN(1, "Black Widow", false),
    
    //Bombas
    PUMPKIN_BOMB_1(-1, "Pumpkin Bomb", false),
    PUMPKIN_BOMB_2(-1, "Pumpkin Bomb", false),
    PUMPKIN_BOMB_3(-1, "Pumpkin Bomb", false),
    PUMPKIN_BOMB_4(-1, "Pumpkin Bomb", false),
    PUMPKIN_BOMB_5(-1, "Pumpkin Bomb", false),
    PUMPKIN_BOMB_6(-1, "Pumpkin Bomb", false),
    
    //Bandera
    PLANET_EARTH_CAPTURED(-2, "Planet Earth Captured", false);
    
    private final int Rank;
    private final String Name;
    private final boolean IsHero;
    
    PieceType(int Rank, String Name, boolean IsHero) {
        this.Rank = Rank;
        this.Name = Name;
        this.IsHero = IsHero;
    }
    
    public int getRank() {return Rank;}
    public String getName() {return Name;}
    public boolean IsHero() {return IsHero;}
    
    public boolean isBomb() {
        return Rank == -1;
    }
    
    public boolean isLand() {
        return Rank == -2;
    }
    
    public boolean canMove() {
        return !isBomb() && !isLand();
    }
    
    public boolean CanKillHighestRank() {
        return Rank == 1;
    }
    
    public boolean CanMoveMuliple() {
        return Rank == 2;
    }
    
    public boolean CanDestroyBombs() {
        return Rank == 3;
    }
    
    public boolean IsHighestRank() {
        return Rank == 10;
    }
    
    /*
        Obtiene todas las piezas de un bando especifico
    */
    public static PieceType[] GetPieceForSide(boolean IsHero) {
        //Contar primero cuantas piezas hay para el bando
        int Count = 0;
        for (PieceType Type : values()) { //values es un metodo automatico generado por Java pa los enums, que en este caso hace un return con todos los PieceType del enum
            if (Type.IsHero() == IsHero) {
                Count++;
            }
        }
        
        //Crear un arreglo del tamaño correcto
        PieceType[] Pieces = new PieceType[Count];
        int Index = 0;
        for (PieceType Type : values()) { //Iterar nuevamente por todos los PieceType
            if (Type.IsHero() == IsHero) {
                Pieces[Index++] = Type;
            }
        }
        return Pieces;
    }
    
    /*
        Obtiene un conjunto completo de 40 piezas para un bando
    */
    public static PieceType[] GetStandardArmy(boolean IsHero) {
        PieceType[] Army = new PieceType[40];
        int Index = 0;
        
        if (IsHero) {
            //Heroes
            Army[Index++] = MR_FANTASTIC; //Rango 10
            Army[Index++] = CAPTAIN_AMERICA; //Rango 9
            Army[Index++] = PROFESSOR_X; Army[Index++] = NICK_FURY; //Rango 8
            Army[Index++] = SPIDER_MAN; Army[Index++] = WOLVERINE; Army[Index++] = NAMOR; //Rango 7
            Army[Index++] = DAREDEVIL; Army[Index++] = SILVER_SURFER; Army[Index++] = HULK; Army[Index++] = IRON_MAN; //Rango 6
            Army[Index++] = THOR; Army[Index++] = HUMAN_TORCH; Army[Index++] = CYCLOPS; Army[Index++] = INVISIBLE_WOMAN; //Rango 5
            Army[Index++] = GHOST_RIDER; Army[Index++] = PUNISHER; Army[Index++] = BLADE; Army[Index++] = THE_THING; //Rango 4
            Army[Index++] = EMMA_FROST; Army[Index++] = SHE_HULK; Army[Index++] = GIANT_MAN; Army[Index++] = BEAST; Army[Index++] = COLOSSUS; //Rango 3
            Army[Index++] = GAMBIT; Army[Index++] = SPIDER_GIRL; Army[Index++] = ICE_MAN; Army[Index++] = STORM; Army[Index++] = PHOENIX; Army[Index++] = DR_STRANGE; Army[Index++] = ELEKTRA; Army[Index++] = NIGHTCRAWLER; //Rango 2
            Army[Index++] = BLACK_WIDOW; //Rango 1
            Army[Index++] = NOVA_BLAST_1; Army[Index++] = NOVA_BLAST_2; Army[Index++] = NOVA_BLAST_3; Army[Index++] = NOVA_BLAST_4; Army[Index++] = NOVA_BLAST_5; Army[Index++] = NOVA_BLAST_6; //6 Bombas
            Army[Index++] = PLANET_EARTH; //1 Tierra
            
        } else {
            //Villanos
            Army[Index++] = DR_DOOM; //Rango 10
            Army[Index++] = GALACTUS; //Rango 9
            Army[Index++] = KINGPIN; Army[Index++] = MAGNETO; //Rango 8
            Army[Index++] = APOCALYPSE; Army[Index++] = GREEN_GOBLIN; Army[Index++] = VENOM; //Rango 7
            Army[Index++] = BULLSEYE; Army[Index++] = OMEGA_RED; Army[Index++] = ONSLAUGHT; Army[Index++] = RED_SKULL; //Rango 6
            Army[Index++] = MYSTIQUE; Army[Index++] = MYSTERIO; Army[Index++] = DR_OCTOPUS; Army[Index++] = DEADPOOL; //Rango 5
            Army[Index++] = ABOMINATION; Army[Index++] = THANOS; Army[Index++] = BLACK_CAT; Army[Index++] = SABRETOOTH; //Rango 4
            Army[Index++] = JUGGERNAUT; Army[Index++] = RHINO; Army[Index++] = CARNAGE; Army[Index++] = MOLE_MAN; Army[Index++] = LIZARD; //Rango 3
            Army[Index++] = MR_SINISTER; Army[Index++] = SENTINEL_1; Army[Index++] = ULTRON; Army[Index++] = SANDMAN; Army[Index++] = LEADER; Army[Index++] = VIPER; Army[Index++] = SENTINEL_2; Army[Index++] = ELECTRO; //Rango 2
            Army[Index++] = BLACK_WIDOW_VILLAIN; //Rango 1
            Army[Index++] = PUMPKIN_BOMB_1; Army[Index++] = PUMPKIN_BOMB_2; Army[Index++] = PUMPKIN_BOMB_3; Army[Index++] = PUMPKIN_BOMB_4; Army[Index++] = PUMPKIN_BOMB_5; Army[Index++] = PUMPKIN_BOMB_6; //6 Bombas
            Army[Index++] = PLANET_EARTH_CAPTURED; //1 Tierra
            
        }
        return Army;
    }
}

/*
    Clase para representar una posicion en el tablero
*/
class Position {
    private final int Row;
    private final int Col;
    
    public Position(int Row, int Col) {
        this.Row = Row;
        this.Col = Col;
    }
    
    public int getRow() {return Row;}
    public int getCol() {return Col;}
    
    public boolean isValid() {
        return Row >= 0 && Row <= 10 && Col >= 0 && Col <= 10;
    }
    
    public boolean isLake() {
        //Zonas prohibidas en el centro del tablero
        return (Row >= 4 && Row <= 5 && Col >= 2 && Col <= 3) || (Row >= 4 && Row <= 5 && Col >= 6 && Col <= 7);
    }
    
    public boolean IsHeroDeploymentZone() {
        //Primeras 4 filas (0-3) para Heroes
        return Row >= 0 && Row <= 3;
    }
    
    public boolean IsVillainDeploymentZone() {
        //Ultimas 4 filas (6-9) para Villanos
        return Row >= 6 && Row <= 9;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position Position = (Position) obj;
        return Row == Position.Row && Col == Position.Col;
    }
    
    @Override
    public String toString() {
        return "(" + Row + "," + Col + ")";
    }
}

/*
    Clase para representar una pieza
*/
class Piece {
    private PieceType Type;
    private boolean Revealed;
    private Position position;
    private final boolean IsHero;
    
    public Piece(PieceType Type, Position position) {
        this.Type = Type;
        this.position = position;
        this.Revealed = false;
        this.IsHero = Type.IsHero();
    }
    
    //Getters y Setters
    public PieceType getType() {return Type;}
    public boolean isRevealed() {return Revealed;}
    public void setRevealed(boolean Revealed) {this.Revealed = Revealed;}
    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}
    public boolean IsHero() {return IsHero;}
    
    //Metodos de conveniencia
    public boolean canMove() {return Type.canMove();}
    public int getRank() {return Type.getRank();}
    public String getName() {return Type.getName();}
    public boolean isBomb() {return Type.isBomb();}
    public boolean isLand() {return Type.isLand();}
    
    //Metodos pa rangos
    public boolean CanKillHighestRank() {return Type.getRank() == 1;}
    public boolean CanMoveMultiple() {return Type.getRank() == 2;}
    public boolean CanDestroyBombs() {return Type.getRank() == 3;}
    public boolean IsHigestRank() {return Type.getRank() == 10;}
    
    @Override
    public String toString() {
        return Type.getName() + " at " + position;
    }
}

/*
    Resultado de un movimiento
*/
enum MoveResult {
    VALID_MOVE("Movimiento valido"),
    INVALID_DESTINATION("Destino invalido"),
    BLOCKED_PATH("Camino bloqueado"),
    OCCUPIED_BY_OWN_PIECE("Ocupado por pieza propia"),
    PIECE_CANNOT_MOVE("La pieza no se puede mover"),
    OUT_OF_BOUNDS("Fuera de los limites del tablero"),
    LAKE_ZONE("No se puede mover a zona de lago"),
    NOT_YOUR_TURN("No es tu turno"),
    NO_PIECE_AT_POSITION("No hay pieza en esa posision"),
    BATTLE_REQUIRED("Se requiere batalla");
    
    private final String Description;
    
    MoveResult(String Description) {
        this.Description = Description;
    }
    
    public String getDescription() {
        return Description;
    }
}

/*
    Resultado de una batalla
*/
class BattleResult {
    private final Piece Attacker;
    private final Piece Defender;
    private final Piece Winner;
    private final boolean Tie;
    private final String Description;
    private final boolean LandCaptured;
    
    public BattleResult(Piece Attacker, Piece Defender, Piece Winner, boolean Tie, String Description) {
        this.Attacker = Attacker;
        this.Defender = Defender;
        this.Winner = Winner;
        this.Tie = Tie;
        this.Description = Description;
        this.LandCaptured = Defender != null && Defender.isLand();
    }
    
    //Getters
    public Piece getAttacker() {return Attacker;}
    public Piece getDefender() {return Defender;}
    public Piece getWinner() {return Winner;}
    public boolean isTie() {return Tie;}
    public String getDescription() {return Description;}
    public boolean isLandCaptured() {return LandCaptured;}
    public boolean isGameWinning() {return LandCaptured;}
}

/*
    Clase principal para la logica de los movimientos
*/
class Movement {
    //Validar si un movimiento es posible
    public static MoveResult ValidateMove(Piece piece, Position From, Position To, Piece[][] Board, boolean IsHeroTurn) {
        //Verificar que hay una pieza en la position inicial
        if (piece == null) {
            return MoveResult.NO_PIECE_AT_POSITION;
        }
        
        //Verificar que sea el turno correcto
        if (piece.IsHero() != IsHeroTurn) {
            return MoveResult.NOT_YOUR_TURN;
        }
        
        //Verificar que la pieza se pueda mover
        if (!To.isValid()) {
            return MoveResult.PIECE_CANNOT_MOVE;
        }
        
        //Verificar que el destino este dentro del tablero
        if (!To.isValid()) {
            return MoveResult.OUT_OF_BOUNDS;
        }
        
        //Verificar que el destino no sea una zona prohibida
        if (!To.isValid()) {
            return MoveResult.LAKE_ZONE;
        }
        
        //Verificar que no esta ocuipado por una pieza propia
        Piece TargetPiece = Board[To.getRow()][To.getCol()];
        if (TargetPiece != null && TargetPiece.IsHero() == piece.IsHero()) {
            return MoveResult.OCCUPIED_BY_OWN_PIECE;
        }
        
        //Si hay una pieza enemiga, es una batalla
        if (TargetPiece != null && TargetPiece.IsHero() != piece.IsHero()) {
            return MoveResult.BATTLE_REQUIRED;
        }
        
        //Validar el tipo de movimiento segun el rango
        if (piece.getRank() == 2) {
            //Rango 2 puede moverse multiples casills ortogonalmente (tipo la torre de ajedrez)
            return ValidateMultipleMove(From, To, Board);
        } else {
            return ValidateSingleMove(From, To);
        }
    }
    
    /*
        Valida movimiento de una sola casilla (piezas normales)
    */
    private static MoveResult ValidateSingleMove(Position From, Position To) {
        int RowDiff = Math.abs(To.getRow() - From.getRow());
        int ColDiff = Math.abs(To.getCol() - From.getCol());
        
        //Debe ser exactamente una casilla adyacente ortogonal
        if ((RowDiff == 01 && ColDiff == 0) || RowDiff == 0 && ColDiff == 1) {
            return MoveResult.VALID_MOVE;
        }
        return MoveResult.INVALID_DESTINATION;
    }
    
    /*
        Valida movimientos multiples (solamente para el rango 2)
    */
    private static MoveResult ValidateMultipleMove(Position From, Position To, Piece[][] Board) {
        int RowDiff = To.getRow() - From.getRow();
        int ColDiff = To.getCol() - From.getCol();
        
        //Debe ser movimiento ortogonal (vertical o horiztontal)
        if (RowDiff != 0 && ColDiff != 0) {
            return MoveResult.INVALID_DESTINATION;
        }
        
        //Si es el mismo lugar
        if (RowDiff == 0 && ColDiff == 0) {
            return MoveResult.INVALID_DESTINATION;
        }

        //Verificar que el camino esta libre
        int StepRow = Integer.compare(RowDiff, 0);
        int StepCol = Integer.compare(ColDiff, 0);
        
        int CurrentRow = From.getRow() + StepRow;
        int CurrentCol = From.getCol() + StepCol;
        
        //Verificar cada casilla en el camino (sin incluir la inicial y final)
        while (CurrentRow != To.getRow() || CurrentCol != To.getCol()) {
            Position CurrentPos = new Position(CurrentRow, CurrentCol);
            
            //Verificar que no es zona prohibida
            if (CurrentPos.isLake()) {
                return MoveResult.BLOCKED_PATH;
            }
            
            //Verificar que no hay piezas bloqueando
            if (Board[CurrentRow][CurrentCol] != null) {
                return MoveResult.BLOCKED_PATH;
            }
            
            CurrentRow += StepRow;
            CurrentCol += StepCol;
        }
        return MoveResult.VALID_MOVE;
    }
    
    /*
        Ejecuta una btalla entre dos piezas
    */
    public static BattleResult ExecuteBattle(Piece Attacker, Piece Defender) {
        //Revelar ambas piezas
        Attacker.setRevealed(true);
        Defender.setRevealed(true);
        
        String Description = Attacker.getName() + " vs " + Defender.getName() + "! ";
        
        //Caso especial: captura de tierra
        if (Defender.isLand()) {
            Description += Attacker.getName() + " ha capturado la " + Defender.getName() + "!";
            return new BattleResult(Attacker, Defender, Attacker, false, Description);
        }
        
        //Caso especial: bomba vs cualquier personaje
        if (Defender.isBomb()) {
            if (Attacker.getRank() == 3) {
                //Solamente el rango 3 va a poder destruir bombas
                Description += Attacker.getClass() + " desactiva la bomba!";
                return new BattleResult(Attacker, Defender, Attacker, false, Description);
            } else {
                //Cualquier otra pieza muere contra la bomba
                Description += Attacker.getName() + " es destruido por la bomba!";
                return new BattleResult(Attacker, Defender, Defender, false, Description);
            }
        }
        
        //Caso especial: Rango 1 atacando al rango 10
        if (Attacker.getRank() == 1 && Defender.getRank() == 10) {
            Description += "El rango 1 ha eleminado al rango 10!!";
            return new BattleResult(Attacker, Defender, Attacker, false, Description);
        }
        
        //Batalla normal basada en rangos
        if (Attacker.getRank() > Defender.getRank()) {
            Description += Attacker.getName() + " Gana!";
            return new BattleResult(Attacker, Defender, Attacker, false, Description);
        } else if (Defender.getRank() > Attacker.getRank()) {
            Description += Defender.getName() + " Gana!";
            return new BattleResult(Attacker, Defender, Defender, false, Description);
        } else {
            //Empate - ambas piezas se destruyes
            Description += "Ha habido un empate! Ambos personajes han sido eliminados!";
            return new BattleResult(Attacker, Defender, null, true, Description);
        }
    }
    
    /*
        Obtiene todas las posiciones adyacentes validas para una pieza
    */
    public static Position[] getValidMoves(Piece piece, Piece[][] Board, boolean IsHeroTurn) {
        if (piece == null || !piece.canMove() || piece.IsHero() != IsHeroTurn) {
            return new Position[0];
        }
        
        Position CurrentPos = piece.getPosition();
        
        if (piece.getRank() == 2) {
            //Rango 2 peude moverse en las 4 direcciones ortogonales multiples casillas
            return GetMultipleMoves(CurrentPos, Board, piece.IsHero());
        } else {
            //Piezas normales solo se mueven una casilla
            Position[] TempMoves = new Position[4]; //Maximo 4 direcciones
            int Count = 0;
            
            int[][] Direcciones = {{-1, 0}, {1,0}, {0,-1}, {0,1}}; //Arriba. abajo, izquierda, derecha
            
            for (int[] Dir : Direcciones) {
                Position NewPos = new Position(CurrentPos.getRow() + Dir[0], CurrentPos.getCol() + Dir[1]);
                
                MoveResult Result = ValidateMove(piece, CurrentPos, NewPos, Board, IsHeroTurn);
                if (Result == MoveResult.VALID_MOVE || Result == MoveResult.BATTLE_REQUIRED) {
                    TempMoves[Count++] = NewPos;
                }
            }
            
            //Crear arreglo del tamaño exacto
            Position[] ValidMoves = new Position[Count];
            for (int i = 0; i < Count; i++) {
                ValidMoves[i] = TempMoves[i];
            }
            return ValidMoves;
        }
    }
    
    /*
        Obtiene movimientos validos para rango 2 (movimiento multiple)
    */
    private static Position[] GetMultipleMoves(Position From, Piece[][] Board, boolean IsHero) {
        Position[] TempMoves = new Position[28]; //Maximo teorico en tablero 10x10
        int Count = 0;
        
        int[][] Direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for (int[] Dir : Direcciones) {
            int CurrentRow = From.getRow();
            int CurrentCol = From.getCol();
            
            while (true) {
                CurrentRow += Dir[0];
                CurrentCol += Dir[1];
                Position NewPos = new Position(CurrentRow, CurrentCol);
                
                //Verficar limites del tablero
                if (!NewPos.isValid()) {
                    break;
                }
                
                //Verificar zonas prohibidas
                Piece PieceAtPos = Board[CurrentRow][CurrentCol];
                if (PieceAtPos != null) {
                    //Si es pieza enemiga, puede atacar pero no pasar
                    if (PieceAtPos.IsHero() != IsHero) {
                        TempMoves[Count++] = NewPos;
                    }
                    break;
                }
                
                //Casilla vacia - Puede moverse
                TempMoves[Count++] = NewPos;
            }
        }
        
        //Crear arreglo del tamaño exacto
        Position[] ValidMoves = new Position[Count];
        for (int i = 0; i < Count; i++) {
            ValidMoves[i] = TempMoves[i];
        }
        return ValidMoves;
    }
    
    /*
        Verifica si un jugador tiene movimientos valid disponibles
    */
    public static boolean HasValidMoves(Piece[][] Board, boolean IsHeroTurn) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Piece piece = Board[row][col];
                if (piece != null && piece.IsHero() == IsHeroTurn && piece.canMove()) {
                    Position[] Moves = getValidMoves(piece, Board, IsHeroTurn);
                    if (Moves.length > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /*
        Cuenta las piezas moviles de un bando
    */
    public static int CountMovablePieces(Piece[][] Board, boolean IsHero) {
        int Count = 0;
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Piece piece = Board[row][col];
                if (piece != null && piece.IsHero() == IsHero && piece.canMove()) {
                    Count++;
                }
            }
        }
        return Count;
    }
    
    /*
        Busca la bandera de un bando especifico
    */
    public static Position FindLand(Piece[][] Board, boolean IsHero) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Piece piece = Board[row][col];
                if (piece != null && piece.IsHero() == IsHero && piece.isLand()) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }
    
    /*
        Verificar las condiciones de victoria
    */
    public static GameState CheckGameState(Piece[][] Board) {
        //Verificar si alguna bandera fue capturada
        Position HeroLand = FindLand(Board, true);
        Position VillainLand = FindLand(Board, false);
        
        if (HeroLand == null) {
            return GameState.VILLAINS_WIN_LAND_CAPTURED;
        }
        
        if (VillainLand == null) {
            return GameState.HEROES_WIN_LAND_CAPTURED;
        }
        
        //Verificar si algun bando no tiene piezas moviles
        int HeroMovablePieces = CountMovablePieces(Board, true);
        int VillainMovablePieces = CountMovablePieces(Board, false);
        
        if (HeroMovablePieces == 0) {
            return GameState.VILLAINS_WIN_NO_MOVES;
        }
        
        if (VillainMovablePieces == 0) {
            return GameState.HEROES_WIN_NO_MOVES;
        }
        
        return GameState.IN_PROGRESS;
    }
    
    /*
        Obtiene todas las piezas capturadas de un bando
    */
    public static Piece[] GetCapturedPieces(Piece[] AllPieces, boolean IsHero) {
        //Contar primero las piezas capturadas
        int Count = 0;
        for (Piece piece : AllPieces) {
            if (piece.IsHero() == IsHero && piece.getPosition() == null) {
                Count++;
            }
        }
        
        //Crear arreglo del tamaño correcto
        Piece[] CapturedPieces = new Piece[Count];
        int Index = 0;
        for (Piece piece : AllPieces) {
            if (piece.IsHero() && piece.getPosition() == null) {
                CapturedPieces[Index++] = piece;
            }
        }
        return CapturedPieces;
    }
    
    /*
        Enum para el estado del juego
    */
    enum GameState {
        IN_PROGRESS("Juego en Progreso"),
        HEROES_WIN_LAND_CAPTURED("Los Heroes ganan! La Tierra ha sido capturada!"),
        VILLAINS_WIN_LAND_CAPTURED("Los Villanos ganan! La Tierra ha sido capturada!"),
        HEROES_WIN_NO_MOVES("Los Heroes ganan! Los Villanos se han quedado sin movimientos"),
        VILLAINS_WIN_NO_MOVES("Los Villanos ganan! Los Heroes se han quedado sin movimientos"),
        HEROES_WIN_SURRENDER("Los Heroes ganan! Los Villanos se han rendido!"),
        VILLAINS_WIN_SURRENDER("Los Villanos ganan! Los Heroes se han rendido!");
        
        private final String Description;
        
        GameState(String Description) {
            this.Description = Description;
        }
        
        public String getDescription() {
            return Description;
        }
        
        public boolean isGameOver() {
            return this != IN_PROGRESS;
        }
    }
}