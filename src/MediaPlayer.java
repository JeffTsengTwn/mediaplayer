

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

enum MODE{PLAY,PAUSE,STOP,REPLAY,OPEN}
public class MediaPlayer implements ActionListener{
	JFrame f;
	JButton btnPlay, btnPause, btnStop, btnReplay, btnOpen;
	JSlider sldTime;
	JPanel p;
	FileDialog flg;
	AudioInputStream audioInputStream;
	AudioFormat audioFormat;
	Clip clip;
	File file;
	String filePath=""; 
	Thread myThread;
	public static void main(String [] arg)
	{
		new MediaPlayer();
	}
	public  void AudioLoad()
	{
		filePath = flg.getDirectory()+flg.getFile();
		f.setTitle(filePath);
		if (clip != null)
			clip.close();
		clip = null;
		audioInputStream = null;
		file = null;
		audioFormat = null;
		file = new File(filePath);
		try {
			audioInputStream = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		audioFormat = audioInputStream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
		try {
			clip  = (Clip) AudioSystem.getLine(info);
			clip.open(audioInputStream);
		} catch (LineUnavailableException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	public MediaPlayer()
	{
		f = new JFrame("MyFrame");
		p =  new JPanel(new FlowLayout());
		f.setLayout(new BorderLayout());
		btnPlay = new JButton();
		btnPause = new JButton();
		btnStop = new JButton();
		btnReplay = new JButton();
		btnOpen = new JButton();
		sldTime = new JSlider(0,100,0);
		
		btnPlay.setIcon(new ImageIcon("./image/play.png"));
		btnPause.setIcon(new ImageIcon("./image/pause.png"));
		btnStop.setIcon(new ImageIcon("./image/stop.png"));
		btnReplay.setIcon(new ImageIcon("./image/replay.png"));
		btnOpen.setIcon(new ImageIcon("./image/open.png"));
		
		btnPlay.setActionCommand("PLAY");
		btnPause.setActionCommand("PAUSE");
		btnStop.setActionCommand("STOP");
		btnReplay.setActionCommand("REPLAY");
		btnOpen.setActionCommand("OPEN");
		btnPlay.addActionListener(this);
		btnPause.addActionListener(this);
		btnReplay.addActionListener(this);
		btnStop.addActionListener(this);
		btnOpen.addActionListener(this);
		flg = new FileDialog(f, "∂}±“¥C≈È¿…", FileDialog.LOAD);
		p.add(btnPlay);
		p.add(btnPause);
		p.add(btnStop);
		p.add(btnReplay);
		p.add(btnOpen);
		f.add(p, BorderLayout.SOUTH);
		f.add(sldTime, BorderLayout.NORTH);
		f.pack();
		f.setVisible(true);
		
	}



	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		MODE id = null;
		for (MODE i: MODE.values())
			if (i.toString().equals(cmd))
				id =i;
		switch (id) {
			case PLAY:
				clip.start();
				new Timer(1, new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						sldTime.setMaximum((int) audioInputStream.getFrameLength());
						sldTime.setValue(clip.getFramePosition());
					}}).start();
				break;
			case PAUSE:
				clip.stop();
				break;
			case STOP:
				AudioLoad();
				break;
			case REPLAY:
				AudioLoad();
				clip.start();
				break;
			case OPEN:
				flg.show();
				if(!flg.getFile().equals("null"))
					AudioLoad();
				
				break;
			default:
		}
			
		
	}
}
