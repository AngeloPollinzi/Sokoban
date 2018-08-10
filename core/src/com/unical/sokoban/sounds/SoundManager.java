package com.unical.sokoban.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Disposable;

public class SoundManager implements Disposable{

	private Music gameSoundtrack;
	private Music menuSoundtrack;
	private Music endSoundtrack;
	
	public SoundManager() {
		// TODO Auto-generated constructor stub
		gameSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("sounds/gameTrack.ogg"));
		menuSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("sounds/menuSoundtrack.ogg"));
		endSoundtrack = Gdx.audio.newMusic(Gdx.files.internal("sounds/endTrack.ogg"));
	}

	public void play(int musicId) {
		switch (musicId) {
		case 1:
			gameSoundtrack.setLooping(true);
			gameSoundtrack.play();
			break;
		case 2:
			menuSoundtrack.setLooping(true);
			menuSoundtrack.play();
			break;
		case 3:
			endSoundtrack.setLooping(true);
			endSoundtrack.play();
			break;
		default:
			break;
		}
	}

	public void stop(int musicId) {
		switch (musicId) {
		case 1:
			gameSoundtrack.stop();
			break;
		case 2:
			menuSoundtrack.stop();
			break;
		case 3:
			endSoundtrack.stop();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		menuSoundtrack.dispose();
		gameSoundtrack.dispose();
		endSoundtrack.dispose();
	}

}
