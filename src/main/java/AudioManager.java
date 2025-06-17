/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */

import javax.sound.sampled.*;
import java.io.*;
import javax.swing.SwingUtilities;

/*
    Sistema de audio epico epicoso para un ambiente sumamente epico
*/
public class AudioManager {
    private static AudioManager Instance;
    
    //Configuracion del audio
    private boolean SoundEnabled = true;
    private boolean MusicEnabled = true;
    private float MasterVolume = 0.7f;
    private float MusicVolume = 0.5f;
    private float sfxVolume = 0.8f;
    
    //Arreglos para almacenar sonidos
    private static final int MAX_SOUNDS = 50;
    private static final int MAX_MUSIC = 10;
    
    //Arreglos de nombres y clips de sonidos
    private final String[] SoundEffectNames;
    private final Clip[] SoundEffectClips;
    private int SoundEffectCount;
    
    //Arreglos de nombres y clips de musica
    private final String[] MusicTrackNames;
    private final Clip[] MusicTrackClips;
    private int MusicTrackCount;
    
    //Control de reproduccion
    private Clip CurrentMusic;
    private String CurrentMusicName;
    private boolean IsMusicPlaying = false;
    
    //Control de musica continua
    private boolean KeepMusicPlaying = false;
    private String RequestedMusicTrack = "";
    
    //Le constructor
    private AudioManager() {
        //Inicializacion de los arreglos
        SoundEffectNames = new String[MAX_SOUNDS];
        SoundEffectClips = new Clip[MAX_SOUNDS];
        SoundEffectCount = 0;
        
        MusicTrackNames = new String[MAX_MUSIC];
        MusicTrackClips = new Clip[MAX_MUSIC];
        MusicTrackCount = 0;
        
        InitializeAudioSystem();
        LoadMarvelSounds();
    }
    
    public static AudioManager getInstance() {
        if (Instance == null) {
            Instance = new AudioManager();
        }
        return Instance;
    }
    
    private void InitializeAudioSystem() {
        try {
            if (!AudioSystem.isLineSupported(Port.Info.SPEAKER)) {
                System.out.println("Sistema de audio no disponible.");
                SoundEnabled = false;
                MusicEnabled = false;
            }
        } catch (Exception e) {
            System.err.println("Error inicializando sistema de audio: " + e.getMessage());
            SoundEnabled = false;
            MusicEnabled = false;
        }
    }
    
    /*
        Aqui se cargan todos los onido del juego usando arreglos
    */
    private void LoadMarvelSounds() {
        try {
            //Musica de fondo
            addMusicTrack("hero_theme", "sounds/music/hero_theme.wav");
            addMusicTrack("villain_theme", "sounds/music/villain_theme.wav");
            addMusicTrack("battle_music", "sounds/music/battle_music.wav");
            addMusicTrack("menu_music", "sounds/music/menu_music.wav");
            addMusicTrack("victory_hero", "sounds/music/victory_hero.wav");
            addMusicTrack("victory_villain", "sounds/music/victory_villain.wav");
            
            //Efectos de sonido generales
            addSoundEffect("button_click", "sounds/sfx/button_click.wav");
            addSoundEffect("button_hover", "sounds/sfx/button_hover.wav");
            addSoundEffect("piece_hover", "sounds/sfx/piece_hover.wav");
            addSoundEffect("piece_move", "sounds/sfx/piece_move.wav");
            addSoundEffect("piece_capture", "sounds/sfx/piece_capture.wav");
            addSoundEffect("game_start", "sounds/sfx/game_start.wav");
            addSoundEffect("turn_change", "sounds/sfx/turn_change.wav");
            addSoundEffect("invalid_move", "sounds/sfx/invalid_move.wav");
            addSoundEffect("notification", "sounds/sfx/notification.wav");
            
            //Efectos de batalla
            addSoundEffect("battle_clash", "sounds/battle/battle_clash.wav");
            addSoundEffect("hero_wins", "sounds/battle/hero_wins.wav");
            addSoundEffect("villain_wins", "sounds/battle/villain_wins.wav");
            addSoundEffect("bomb_explosion", "sounds/battle/bomb_explosion.wav");
            addSoundEffect("earth_captured", "sounds/battle/earth_captures.wav");
            
            //Voces de heroes
            addSoundEffect("iron_man_attack", "sounds/voices/iron_man_attack.wav");
            addSoundEffect("captain_america_attack", "sounds/voices/captain_america_attack.wav");
            addSoundEffect("thor_attack", "sounds/voices/thor_attack.wav");
            addSoundEffect("hulk_attack", "sounds/voices/hulk_attack.wav");
            
            addSoundEffect("thanos_attack", "sounds/voices/thanos_attack.wav");
            addSoundEffect("loki_attack", "sounds/voices/loki_attack.wav");
            addSoundEffect("red_skull_attack", "sounds/voices/red_skull_attack.wav");
            
            System.out.println("Sistema de audio Marvel cargado exitosamente.");
            System.out.println("Efectos cargados: " + SoundEffectCount);
            System.out.println("Musica cargada: " + MusicTrackCount);
        } catch (Exception e) {
            System.err.println("Algunos archivos de audio no se pudieron cargar: " + e.getMessage());
        }
    }
    
    /*
        Agrega una pista musical al arreglo
    */
    private void addMusicTrack(String Name, String FilePath) {
        if (MusicTrackCount >= MAX_MUSIC) {
            System.err.println("Limite de musica alcanzado");
            return;
        }
        
        try {
            Clip Clip = LoadAudioClip(FilePath);
            
            if (Clip != null) {
                MusicTrackNames[MusicTrackCount] = Name;
                MusicTrackClips[MusicTrackCount] = Clip;
                MusicTrackCount++;
            } else {
                //Generar musica sintetica
                Clip SynthClip = GenerateSyntheticMusic(Name);
                
                if (SynthClip != null) {
                    MusicTrackNames[MusicTrackCount] = Name;
                    MusicTrackClips[MusicTrackCount] = SynthClip;
                    MusicTrackCount++;
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando musica " + Name + ": " + e.getMessage());
        }
    }
    
    /*
        Agregar un efecto de sonido al arreglo
    */
    private void addSoundEffect(String Name, String FilePath) {
        if (SoundEffectCount >= MAX_SOUNDS) {
            System.err.println("Limite de efectos alcanzado");
            return;
        }
        
        try {
            Clip Clip = LoadAudioClip(FilePath);
            
            if (Clip != null) {
                SoundEffectNames[SoundEffectCount] = Name;
                SoundEffectClips[SoundEffectCount] = Clip;
                SoundEffectCount++;
            } else {
                //Generar efectos sinteticos como fallback
                Clip SynthClip = GenerateSyntheticSFX(Name);
                if (SynthClip != null) {
                    SoundEffectNames[SoundEffectCount] = Name;
                    SoundEffectClips[SoundEffectCount] = SynthClip;
                    SoundEffectCount++;
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando efecto " + Name + ": " + e.getMessage());
        }
    }
    
    /*
        Busca un clip de musica por nombre en el arreglo
    */
    private Clip FindMusicClip(String Name) {
        for (int i = 0; i < MusicTrackCount; i++) {
            if (MusicTrackNames[i] != null && MusicTrackNames[i].equals(Name)) {
                return MusicTrackClips[i];
            }            
        }
        return null;
        
    }
    
    /*
        Busca un clip de efcto por nombre en el arreglo
    */
    private Clip FindSoundEffectClip(String Name) {
        for (int i = 0; i < SoundEffectCount; i++) {
            if (SoundEffectNames[i] != null && SoundEffectNames[i].equals(Name)) {
                return SoundEffectClips[i];
            }
        }
        return null;
        
    }
    
    /*
        Carga un clip de audio desde archivo
    */
    private Clip LoadAudioClip(String FilePath) {
        try {
            //Primer metodo para cargar clips: desde resources
            InputStream AudioSrc = getClass().getResourceAsStream("/" + FilePath);
            if (AudioSrc == null) {
                //Segundo metodo para cargar clips: Desde el archivo directo
                File AudioFile = new File(FilePath);
                if (!AudioFile.exists()) {
                    //Tercer metodo para cargar clips: desde src/main/resources
                    AudioFile = new File("src/main/resources/" + FilePath);
                    if (!AudioFile.exists()) {
                        return null;
                    }
                }
                AudioSrc = new FileInputStream(AudioFile);
            }
            
            InputStream BufferedIn = new BufferedInputStream(AudioSrc);
            AudioInputStream AudioInputStream = AudioSystem.getAudioInputStream(BufferedIn);
            
            Clip Clip = AudioSystem.getClip();
            Clip.open(AudioInputStream);
            
            return Clip;
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            return null;
        }
    }
    
    /*
        Genera musica sintetica
    */
    private Clip GenerateSyntheticMusic(String TrackName) {
        try {
            float Frequency = GetSyntheticMusicFrequency(TrackName);
            int Duration = 10000; //10 segundos
            
            byte[] AudioData = GenerateTone(Frequency, Duration, 0.3f);
            
            AudioFormat Format = new AudioFormat(44100, 16, 1, true, false);
            AudioInputStream AudioInputStream = new AudioInputStream(new ByteArrayInputStream(AudioData), Format, AudioData.length / Format.getFrameSize());
            
            Clip Clip = AudioSystem.getClip();
            Clip.open(AudioInputStream);
            
            return Clip;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    /*
        Generar efectos de sonido sinteticos
    */
    private Clip GenerateSyntheticSFX(String EffectName) {
        try {
            float Frequency = GetSyntheticSFXFrequency(EffectName);
            int Duration = GetSyntheticSFXDuration(EffectName);
            float Volume = 0.5f;
            
            byte[] AudioData = GenerateTone(Frequency, Duration, Volume);
            
            AudioFormat Format = new AudioFormat(44100, 16, 1, true, false);
            AudioInputStream AudioInputStream = new AudioInputStream(new ByteArrayInputStream(AudioData), Format, AudioData.length / Format.getFrameSize());
            
            Clip Clip = AudioSystem.getClip();
            Clip.open(AudioInputStream);
            
            return Clip;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    /*
        Genera un tono sintetico
    */
    private byte[] GenerateTone(float Frequency, int DurationMs, float Volume) {
        int SampleRate = 44100;
        int Samples = (SampleRate * DurationMs) / 1000;
        byte[] AudioData = new byte[Samples * 2];
        
        for (int i = 0; i < Samples; i++) {
            double Time = (double) i / SampleRate;
            double Wave = Math.sin(2 * Math.PI * Frequency * Time);
            
            //Envelope para evitar los clicks
            double Envelope = 1.0;
            if (i < SampleRate * 0.1) {
                Envelope = (double) i / (SampleRate * 0.1);
            } else if (i > Samples - SampleRate * 0.1) {
                Envelope = (double) (Samples - i) / (SampleRate * 0.1);
            }
            
            short Sample = (short) (Wave * Envelope * Volume * Short.MAX_VALUE);
            
            AudioData[i * 2] = (byte) (Sample & 0xFF);
            AudioData[i * 2 + 1] = (byte) ((Sample >> 8) & 0xFF);
        }
        return AudioData;
        
    }
    
    /*
        -->     METODOS PARA OBTENER FRECUENCIAS SINTETICAS     <--
    */
    private float GetSyntheticMusicFrequency(String TrackName) {
        switch (TrackName) {
            case "hero_theme": return 440.0f;
            case "villain_theme": return 220.0f;
            case "battle_music": return 523.25f;
            case "menu_music": return 329.63f;
            case "victory_hero": return 659.25f;
            case "victory_villain": return 185.0f;
            default: return 440.0f;
        }
    }
    
    private float GetSyntheticSFXFrequency(String EffectName) {
        switch (EffectName) {
            case "button_click": return 1000.0f;
            case "button_hover": return 800.0f;
            case "piece_move": return 600.0f;
            case "piece_capture": return 1200.0f;
            case "battle_clash": return 200.0f;
            case "bomb_explosion": return 100.0f;
            case "notification": return 850.0f;
            case "invalid_move": return 150.0f;
            default: return 550.0f;
        }
    }
    
    private int GetSyntheticSFXDuration(String EffectName) {
        switch (EffectName) {
            case "button_click": return 100;
            case "button_hover": return 50;
            case "piece_move": return 200;
            case "piece_capture": return 300;
            case "battle_clash": return 500;
            case "bomb_explosion": return 800;
            case "notification": return 400;
            case "invalid_move": return 250;
            default: return 200;
        }
    }
    
    
    /*
        -->     METODOS PUBLICOS     <--
    */
    
    
    /*
        Reproducir musica solamente si ya no se esta reproduciendo
    */
    public void PlayMusic(String TrackName) {
        if (!MusicEnabled || !SoundEnabled) {
            return;
        }
        
        //Solo cambiar musica si es diferente a la actual
        if (CurrentMusicName != null && CurrentMusicName.equals(TrackName) && IsMusicPlaying) {
           return;
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                StopMusic();
                
                Clip MusicClip = FindMusicClip(TrackName);
                if (MusicClip != null) {
                    SetVolume(MusicClip, MusicVolume * MasterVolume);
                    MusicClip.setFramePosition(0);
                    MusicClip.loop(Clip.LOOP_CONTINUOUSLY);
                    MusicClip.start();
                    
                    CurrentMusic = MusicClip;
                    CurrentMusicName = TrackName;
                    IsMusicPlaying = true;
                }
            } catch (Exception e) {
                System.err.println("Error reproduciendo musica: " + e.getMessage());
            }
        });
    }
    
    /*
        Asegurar que cuerta musica siga reproduciendose
    */
    public void EnsureMusicPlaying(String TrackName) {
        if (!MusicEnabled || !SoundEnabled) {
            return;
        }
        
        //Solo reproducir si no hay musica o es diferente
        if (CurrentMusicName == null || !CurrentMusicName.equals(TrackName) || !IsMusicPlaying) {
            PlayMusic(TrackName);
        }
    }
    
    /*
        Forzar cambio de musica (para transiciones importantes)
    */
    public void ForceChangeMusic(String TrackName) {
        //Resetear estado actual para forzar el cambio
        CurrentMusicName = null;
        PlayMusic(TrackName);
    }
    
    /*
        Reproduce un efecto de sonido
    */
    public void PlaySoundEffect(String EffectName) {
        if (!SoundEnabled) {
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                Clip SFXClip = FindSoundEffectClip(EffectName);
                if (SFXClip != null) {
                    //Detener y reiniciar el clip si esta reproduciendose
                    if (SFXClip.isRunning()) {
                        SFXClip.stop();
                    }
                    SFXClip.setFramePosition(0); //Volver al inicio
                    SetVolume(SFXClip, sfxVolume * MasterVolume);
                    SFXClip.start();
                }
            } catch (Exception e) {
                System.err.println("Error reproduciendo efecto: " + e.getMessage());
            }
        });
    }
    
    /*
        Detiene la musica actual
    */
    public void StopMusic() {
        if (CurrentMusic != null && CurrentMusic.isRunning()) {
            CurrentMusic.stop();
            CurrentMusic.setFramePosition(0);
            IsMusicPlaying = false;
            System.out.println("musica detenida");
            CurrentMusicName = null;
        }
    }
    
    /*
        Duplica un clip para permitir multiples reproducciones
    */
    public Clip DuplicateClip(Clip OriginalClip) {
        try {
            if (OriginalClip.isOpen()) {
                //Encontrar el clip original para obtener los datos
                for (int i = 0; i < SoundEffectCount; i++) {
                    if (SoundEffectClips[i] == OriginalClip) {
                        //Recrear el clip desde el archivo
                        return RecreateClip(SoundEffectNames[i]);
                    }
                }
            }
            return AudioSystem.getClip(); //Si no es posible duplicar, se crea un nuevo clip
        } catch (Exception e) {
            System.err.println("Error duplicando clip: " + e.getMessage());
            return null; //Fallback
        }
    }
    
    /*
        Metodo helper para recrear clips
    */
    private Clip RecreateClip(String EffectName) {
        try {
            //Para efectos sinteticos, regenerar
            if (IsSyntheticEffect(EffectName)) {
                return GenerateSyntheticSFX(EffectName);
            } else {
                //Para archivos, cargar de nuevo
                String FilePath = GetFilePathForEffect(EffectName);
                return LoadAudioClip(FilePath);
            }
        } catch (Exception e) {
            System.err.println("Error recreando clip para " + EffectName + ": " + e.getMessage());
            return null;
        }
    }
    
    /*
        Metodo para ver si un efecto es sintetico
    */
    private boolean IsSyntheticEffect(String EffectName) {
        String[] SyntheticEffects = {
            //Efectos de UI
            "button_click",
            "button_hover",
            "piece_hover",
            "notification",
            "invalid_move",
            
            //Efectos de juego
            "piece_move",
            "piece_capture",
            "game_start",
            "turn_change",
            
            //Efectos de batalla
            "battle_clash",
            "hero_wins",
            "villain_wins",
            "bomb_explosion",
            "earth_captured",
            
            //Voces de personajes
            "iron_man_attack",
            "captain_america_attack",
            "thor_attack",
            "hulk_attack",
            "thanos_attack",
            "loki_attack",
            "red_skull_attack",
        };
        
        //Buscar en el arreglo si el efecto es sintetico
        for (int i = 0; i < SyntheticEffects.length; i++) {
            if (SyntheticEffects[i].equals(EffectName)) {
                return true; //Es un sonido sintetico
            }
        }
        return false; //No es un sonido sintetico
    }
    
    /*
        Metodo para obtener el path a los archivos de sonido
    */
    private String GetFilePathForEffect(String EffectName) {
        //Efectos de UI
        if (EffectName.equals("button_click")) return "sounds/sfx/button_click.WAV";
        if (EffectName.equals("button_hover")) return "sounds/sfx/button_hover.WAV";
        if (EffectName.equals("piece_hover")) return "sounds/sfx/piece_hover.WAV";
        if (EffectName.equals("notification")) return "sounds/sfx/notification.WAV";
        if (EffectName.equals("invalid_move")) return "sounds/sfx/invalid_move.WAV";
        
        //Efectos del juego
        if (EffectName.equals("piece_move")) return "sounds/sfx/piece_move.WAV";
        if (EffectName.equals("piece_capture")) return "sounds/sfx/piece_captured.WAV";
        if (EffectName.equals("game_start")) return "sounds/sfx/gam_start.WAV";
        if (EffectName.equals("turn_change")) return "sounds/sfx/turn_change.WAV";
        
        //Efectos de batalla
        if (EffectName.equals("battle_clash")) return "sounds/battle/battle_clash.WAV";
        if (EffectName.equals("hero_wins")) return "sounds/battle/hero_wins.WAV";
        if (EffectName.equals("villain_wins")) return "sounds/battle/villain_wins.WAV";
        if (EffectName.equals("bomb_explosion")) return "sounds/battle/bomb_explosion.WAV";
        if (EffectName.equals("earth_captured")) return "sounds/battle/earth_captured.WAV";
        
        //Voces de heroes
        if (EffectName.equals("iron_man_attack")) return "sounds/voices/iron_man_attack.WAV";
        if (EffectName.equals("captain_america_attack")) return "sounds/voices/captain_america_attack.WAV";
        if (EffectName.equals("thor_attack")) return "sounds/voices/thor_attack.WAV";
        if (EffectName.equals("hulk_attack")) return "sounds/voices/hulk_attack.WAV";
        
        //Voces de villanos
        if (EffectName.equals("thanos_attack")) return "sounds/voices/thanos_attack.WAV";
        if (EffectName.equals("loki_attack")) return "sounds/voices/loki_attack.WAV";
        if (EffectName.equals("red_skull_attack")) return "sounds/voices/red_skull_attack.WAV";
        
        //Ruta por defecto para efectos no mapeado
        return "sounds/sfx/" + EffectName + "/wav";
    }
    
    /*
        Mapear nombres de musica a sus rutas de archivos
    */
    private String GetFilePathForMusic(String TrackName) {
        if (TrackName.equals("hero_theme")) return "sounds/music/hero_theme.wav";
        if (TrackName.equals("villain_theme")) return "sounds/music/villain_theme.wav";
        if (TrackName.equals("battle_music")) return "sounds/music/battle_music.wav";
        if (TrackName.equals("menu_music")) return "sounds/music/menu_music.wav";
        if (TrackName.equals("victory_hero")) return "sounds/music/victory_hero.wav";
        if (TrackName.equals("victory_villain")) return "sounds/music/victory_villain.wav";
        
        
        return "sounds/music" + TrackName + ".wav"; //Ruta por defecto para musica no mapeada
    }
    
    /*
        metodo extra
    */
    private boolean IsSyntheticMusic(String TrackName) {
        //Lista de musica que se genera sinteticamente
        String[] SyntheticTrack = {"hero_theme", "villain_theme", "battle_music", "menu_music", "victory_hero", "victory_villain"};
        
        //Buscar en el arreglo
        for (int i = 0; i < SyntheticTrack.length; i++) {
            if (SyntheticTrack[i].equals(TrackName)) {
                return true;
            }
        }
        return false;
        
    }
    
    /*
        Pausar/reanudar la musica
    */
    public void ToggleMusic() {
        if (CurrentMusic != null) {
            if (CurrentMusic.isRunning()) {
                CurrentMusic.stop();
                IsMusicPlaying = false;
                System.out.println("Musica pausada");
            } else {
                CurrentMusic.start();
                IsMusicPlaying = true;
                System.out.println("Musica reanudada");
            }
        }
    }
    
    /*
        Cambiar a nueva musica con un fade entre las musicas
    */
    public void FadeToMusic(String NewTrackName) {
        if (!MusicEnabled) {
            return;
        }
        
        if (CurrentMusic != null && CurrentMusic.isRunning()) {
            FadeOutCurrentMusic();
            
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    PlayMusic(NewTrackName);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } else {
            PlayMusic(NewTrackName);
        }
    }
    
    /*
        Para darle un efecto de fade entre las musicas
    */
    private void FadeOutCurrentMusic() {
        if (CurrentMusic == null || !CurrentMusic.isRunning()) {
            return;
        }
        
        new Thread(() -> {
            try {
                float OriginalVolume = MusicVolume * MasterVolume;
                for (int i = 10; i >= 0; i--) {
                    SetVolume(CurrentMusic, OriginalVolume * i / 10f);
                    Thread.sleep(50);
                }
                StopMusic();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    /*
        Establece el volumen de un clip
    */
    private void SetVolume(Clip Clip, float Volume) {
        try {
            if (Clip != null && Clip.isOpen()) {
                FloatControl VolumeControl = (FloatControl) Clip.getControl(FloatControl.Type.MASTER_GAIN);
                float Min = VolumeControl.getMinimum();
                float Max = VolumeControl.getMaximum();

                //Convertir volumen lineal a decibelios
                float dB = (float) (Math.log(Math.max(0.0001f, Volume)) / Math.log(10.0) * 20.0);
                dB = Math.max(Min, Math.min(Max, dB));

                VolumeControl.setValue(dB);
            }
        } catch (Exception e) {
            //Algunos sistemas no soportan el control de volumen
        }
    }
    
    /*
        -->     METODOS DE CONFIGURACION     <--   
    */
    public void SetMasterVolume(float Volume) {
        this.MasterVolume = Math.max(0, Math.min(1, Volume));
        if (CurrentMusic != null) {
            SetVolume(CurrentMusic, MusicVolume * MasterVolume);
        }
    }
    
    public void SetMusicVolume(float Volume) {
        this.MusicVolume = Math.max(0, Math.min(1, Volume));
        if (CurrentMusic != null) {
            SetVolume(CurrentMusic, MusicVolume * MasterVolume);
        }
    }
    
    public void SetSFXVolume(float Volume) {
        this.sfxVolume = Math.max(0, Math.min(1, Volume));
    }
    
    public void SetSoundEnabled(boolean Enabled) {
        this.SoundEnabled = Enabled;
        if (!Enabled) {
            StopMusic();
        }
    }
    
    public void SetMusicEnabled(boolean Enabled) {
        this.MusicEnabled = Enabled;
        if (!Enabled) {
            StopMusic();
        }
    }
    
    /*
        -->     LOS GETS     <--    
    */
    //no sabia que las clases se podian hacer si son cortas
    public boolean IsSoundEnabled() {return SoundEnabled;}
    public boolean IsMusicEnabled() {return MusicEnabled;}
    public boolean IsMusicPlaying() {return IsMusicPlaying;}
    public String GetCurrentMusicName() {return CurrentMusicName;}
    public float GetMasterVolume() {return MasterVolume;}
    public float GetMusicVolume() {return MusicVolume;}
    public float GetSFXVolume() {return sfxVolume;}
    public int GetSoundEffectCount() {return SoundEffectCount;}
    public int GetMusicTrackCount() {return MusicTrackCount;}
    
    /*
        -->     METODOS DE CONVENIENCIA      <--
    */
    public void PlayMenuMusic() {EnsureMusicPlaying("menu_music");}
    public void PlayHeroMusic() {ForceChangeMusic("hero_theme");}
    public void PlayVillainMusic() {ForceChangeMusic("villain_theme");}
    public void PlayBattleMusic() {ForceChangeMusic("battle_music");}
    public void PlayVictoryHero() {ForceChangeMusic("victory_hero");}
    public void PlayVictoryVillain() {ForceChangeMusic("victory_villain");}
    
    public void PlayButtonClick() {PlaySoundEffect("button_click");}
    public void PlayButtonHover() {PlaySoundEffect("button_hover");}
    public void PlayPieceMove() {PlaySoundEffect("piece_move");}
    public void PlayPieceCapture() {PlaySoundEffect("piece_capture");}
    public void PlayInvalidMove() {PlaySoundEffect("invalid_move");}
    public void PlayNotification() {PlaySoundEffect("notification");}
    
    public void PlayGameStart() {PlaySoundEffect("game_start");}
    public void PlayTurnChange() {PlaySoundEffect("turn_change");}
    public void PlayBattleClash() {PlaySoundEffect("battle_clash");}
    public void PlayBombExplosion() {PlaySoundEffect("bomb_explosion");}
    
    
    /*
        Reproducir la voz de los personajes segun el nombre
    */
    public void PlayCharacterVoice(String CharacterName) {
        String VoiceName = CharacterName.toLowerCase().replace(" ", "_");
        PlaySoundEffect(VoiceName);
    }
    
    /*
        Lista de todos los sonidos disponibles
    */
    public void ListAvailableSounds() {
        System.out.println("Musica Disponible:");
        for (int i = 0; i < MusicTrackCount; i++) {
            System.out.println(" " + (i + 1) + ". " + MusicTrackNames[i]);
        }
        System.out.println("Efectos Disponibles");
        for (int i = 0; i < SoundEffectCount; i++) {
            System.out.println(" " + (i + 1) + ". " + SoundEffectNames[i]);
        }
    }
    
    /*
        Liberar recursos
    */
    public void cleanup() {
        StopMusic();
        
        //Cerrar todos los efectos
        for (int i = 0; i < SoundEffectCount; i++) {
            if (SoundEffectClips[i] != null) {
                SoundEffectClips[i].close();
                SoundEffectClips[i] = null;
            }
            SoundEffectNames[i] = null;
        }
        
        //Cerrar toda la musica
        for (int i = 0; i < MusicTrackCount; i++) {
            if (MusicTrackClips[i] != null) {
                MusicTrackClips[i].close();
                MusicTrackClips[i] = null;
            }
            MusicTrackNames[i] = null;
        }
        
        SoundEffectCount = 0;
        MusicTrackCount = 0;
        
        System.out.println("Sistema de audio cerrado.");
    }
}
