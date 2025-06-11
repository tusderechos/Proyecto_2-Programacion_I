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
    private String[] SoundEffectNames;
    private Clip[] SoundEffectClips;
    private int SoundEffectCount;
    
    //Arreglos de nombres y clips de musica
    private String[] MusicTrackNames;
    private Clip[] MusicTrackClips;
    private int MusicTrackCount;
    
    //Control de reproduccion
    private Clip CurrentMusic;
    private String CurrentMusicName;
    private boolean IsMusicPlaying = false;
    
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
            addMusicTrack("vistory_hero", "sounds/music/victory_hero.wav");
            addMusicTrack("vistory_villain", "sounds/music/victory_villain.wav");
            
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
                //Generar efectos sinteticos
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
            InputStream AudioSrc = getClass().getResourceAsStream("/" + FilePath);
            if (AudioSrc == null) {
                File AudioFile = new File(FilePath);
                if (!AudioFile.exists()) {
                    return null;
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
            
            AudioFormat Format = new AudioFormat(4100, 16, 1, true, false);
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
            double Time = (double) i / Samples;
            double Wave = Math.sin(2 * Math.PI * Frequency * Time);
            
            //Envelope para evitar los clicks
            double Envelope = 1.0;
            if (i < SampleRate * 0.1) {
                Envelope = (double) i / (SampleRate * 0.1);
            } else if (i > Samples - SampleRate * 0.1) {
                Envelope = (double) (Samples - 1) / (SampleRate * 0.1);
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
        String[] TrackNames = {"hero_theme", "villain_theme", "battle_music", "menu_music", "victory_hero", "victory_villain"};
        float[] Frequencies = {440.0f, 220.0f, 523.25f, 329.63f, 659.25f, 185.0f};
        
        for (int i = 0; i < TrackNames.length; i++) {
            if (TrackNames[i].equals(TrackName)) {
                return Frequencies[i];
            }
        }
        return 440.0f; //Default
    }
    
    private float GetSyntheticSFXFrequency(String EffectName) {
        String[] EffectNames = {"button_click", "button_hover", "piece_move", "piece_capture", "battle_clash", "bomb_explosion", "notification", "invalid_move"};
        float[] Frequencies = {1000.0f, 800.0f, 600.0f, 1200.0f, 200.0f, 100.0f, 880.0f, 150.0f};
        
        for (int i = 0; i < EffectNames.length; i++) {
            if (EffectNames[i].equals(EffectName)) {
                return Frequencies[i];
            }
        }
        return 500.0f; //Default
    }
    
    private int GetSyntheticSFXDuration(String EffectName) {
        String[] EffectNames = {"button_click", "button_hover", "piece_move", "piece_capture", "battle_clash", "bomb_explosion", "notification", "invalid_move"};
        int[] Durations = {100, 50, 200, 300, 500, 800, 400, 250};
        
        for (int i = 0; i < EffectNames.length; i++) {
            if (EffectNames[i].equals(EffectName)) {
                return Durations[i];
            }
        }
        return 200; //Default
    }
    
    /*
        -->     METODOS PUBLICOS     <--
    */
    
    /*
        Reproduce musica de fondo con un loop
    */
    public void PlayMusic(String TrackName) {
        if (!MusicEnabled || !SoundEnabled) {
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
                    
                    System.out.println("Reproduciendo musica: " + TrackName);
                }
            } catch (Exception e) {
                System.err.println("Error reproduciendo musica: " + e.getMessage());
            }
        });
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
                    Clip ClipCopy = DuplicateClip(SFXClip); //Crea una copia para multiples reproducciones
                    if (ClipCopy != null) {
                        SetVolume(ClipCopy, sfxVolume * MasterVolume);
                        ClipCopy.setFramePosition(0);
                        ClipCopy.start();
                        
                        //Limpiar cuando termine
                        ClipCopy.addLineListener(event -> {
                            if (event.getType() == LineEvent.Type.STOP) {
                                ClipCopy.close();
                            }
                        });
                    }
                }
            } catch (Exception e) {
                System.err.println("Error reproduciendo efecto: " + e.getMessage());
            }
        });
    }
    
    /*
        Duplica un clip para permitir multiples reproducciones
    */
    public Clip DuplicateClip(Clip OriginalClip) {
        try {
            Clip NewClip = AudioSystem.getClip();
            return OriginalClip;
        } catch (Exception e) {
            return OriginalClip; //Fallback
        }
    }
    
    /*
        Detiene la musica actual
    */
    public void StopMusic() {
        if (CurrentMusic != null && CurrentMusic.isRunning()) {
            CurrentMusic.stop();
            CurrentMusic.setFramePosition(0);
            IsMusicPlaying = false;
            System.out.println("Musica detenida.");
        }
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
                for (int i = 10; i >= 10; i--) {
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
            FloatControl VolumeControl = (FloatControl) Clip.getControl(FloatControl.Type.MASTER_GAIN);
            float Min = VolumeControl.getMinimum();
            float Max = VolumeControl.getMaximum();
            float Gain = Min + (Max - Min) * Math.max(0, Math.min(1, Volume));
            VolumeControl.setValue(Gain);
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
    public void PlayMenuMusic() {PlayMusic("menu_music");}
    public void PlayHeroMusic() {PlayMusic("hero_theme");}
    public void PlayVillainMusic() {PlayMusic("villain_theme");}
    public void PlayBattleMusic() {PlayMusic("battle_music");}
    public void PlayVictoryHero() {PlayMusic("victory_hero");}
    public void PlayVictoryVillain() {PlayMusic("victory_villain");}
    
    public void PlayButtonClick() {PlaySoundEffect("button_click");}
    public void PlayButtonHover() {PlaySoundEffect("button_hover");}
    public void PlayPieceMove() {PlaySoundEffect("piece_move");}
    public void PlayPieceCapture() {PlaySoundEffect("piece_capture");}
    public void PlayBattleClash() {PlaySoundEffect("battle_clash");}
    public void PlayBombExplosion() {PlaySoundEffect("bomb_explosion");}
    public void PlayInvalidMove() {PlaySoundEffect("invalid_move");}
    public void PlayNotification() {PlaySoundEffect("notification");}
    public void PlayGameStart() {PlaySoundEffect("game_start");}
    public void PlayTurnChange() {PlaySoundEffect("turn_change");}
    
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
