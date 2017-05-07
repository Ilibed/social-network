package com.ilibed.music;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MusicRepository extends CrudRepository<Music, Integer> {
    default void saveMusic(Music music){

    }
    default List<Music> getAllMusic(){
        List<Music> musicList = new ArrayList<Music>();
        Music music = new Music();
        music.setId(1);
        music.setName("Big city life");
        music.setPath("assets/muz/Eminem – Big city life.mp3");
        musicList.add(music);

        music = new Music();
        music.setId(2);
        music.setName("Bitch");
        music.setPath("assets/muz/Eminem – Bitch.mp3");
        musicList.add(music);

        music = new Music();
        music.setId(3);
        music.setName("Brainless");
        music.setPath("assets/muz/Eminem – Brainless.mp3");
        musicList.add(music);

        return musicList;
    }
}
