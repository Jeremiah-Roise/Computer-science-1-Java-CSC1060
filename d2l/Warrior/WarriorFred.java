//package edu.arapahoe.csc160;
import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class WarriorFred {
    public WarriorFred(){
     sound.add("Wham!");
     sound.add("Boffo!!");
     sound.add("KaPow!");
     sound.add("As you wish!");
    }

	private String nickName = "unknown";
	private int lifeForce = 10;
	private int damage = 0;
    private boolean nickNameSet = false;
	
	String[] armory = {"Exploding Pumpkin", "Candy Corn", "Paper Bag"};
    ArrayList<String> sound = new ArrayList<String>();

    public void setNickname(String name){
        if (nickNameSet == true) {
            return;
        }
        this.nickName = name;
        if (getNickName().equals("Montoya")){
            sound.add("Hello. My name is Inigo Montoya,\n you killed my father. Prepare to die.");
        }
        if (getNickName().equals("Vizzini")){
            sound.add("Never go in against a Sicilian when death is on the line! Ahahahahahaha!");
        }
        nickNameSet = true;
    }

    public String getNickName(){
        return this.nickName;
    }

    public int getLifeForce(){
        return this.lifeForce;
    }

    public int getDamage(){
        return this.damage;
    }

	public void reSpawn() {
		lifeForce = 10;
	}
	
	public void takeDamage(int n) {
        if (isdead() == true) {
            return;
        }
		damage += n;
		if (damage >= 10) {
			lifeForce--;
			damage = 0;
		}
        reaction();
        System.out.println(getNickName() + " was attacked! \033[1;31m-|====> " + n + " damage!\033[1;39m\n");
        try {
            tone(n * 20, 500, 1);
            if (isdead() == true) {
                System.out.println(getNickName() + " \033[1;31mDied!!\033[1;39m");
                
                for (int i = 60; i > 40; i--) {
                    tone(i, 50);
                }
            }
        } catch (Exception e) {
            System.out.println("Sorry the sound isn't working :(");
        }
	}
	
	public void reaction() {
		Random r = new Random();

        String reactionText = "\n" + getNickName()+ " - \"\033[1;36m" + sound.get(r.nextInt(sound.size())) + "\033[1;39m\"";
		System.out.println(reactionText);
        char[] chararray = new char[reactionText.length()-16];
        Arrays.fill(chararray, '_');
        String str = new String(chararray);
        str+="/";
        str.replace("_","");
        System.out.println(str);
	}
	
	public boolean isdead() {
		if (lifeForce <= 0) return true;
		else return false;
	}
	
	public boolean isalive() {
		if (lifeForce > 0) return true;
		else return false;
	}
	
    public void printInfo() {
        if (isdead() == true) {
            return;
        }
        System.out.println("\n|" + getNickName() + " stats:");

        System.out.println("\033[1;31m|    |              ");
        System.out.println("|:---)=============>");
        System.out.println("|    |              ");
        System.out.println("|Damage: " + getDamage());

        System.out.println("\033[1;32m|/^\\/^\\\n|\\    /\n| \\__/");
        System.out.println("|Life Force: " + getLifeForce() + "\033[1;39m");

        System.out.println("|Armory:");
        System.out.print("|");
        for(int i=0;i<armory.length;i++){
            System.out.print(this.armory[i] + " | ");
        }
        System.out.println("\n");
    }


    public static float SAMPLE_RATE = 8000f;

      public static void tone(int hz, int msecs) 
         throws LineUnavailableException 
      {
         tone(hz, msecs, 1.0);
      }

      public static void tone(int hz, int msecs, double vol)
          throws LineUnavailableException 
      {
        byte[] buf = new byte[1];
        AudioFormat af = 
            new AudioFormat(
                SAMPLE_RATE, // sampleRate
                8,           // sampleSizeInBits
                1,           // channels
                true,        // signed
                false);      // bigEndian
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af);
        sdl.start();
        for (int i=0; i < msecs*8; i++) {
          double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
          buf[0] = (byte)(Math.sin(angle) * 127.0 * vol);
          sdl.write(buf,0,1);
        }
        sdl.drain();
        sdl.stop();
        sdl.close();
      }
}
