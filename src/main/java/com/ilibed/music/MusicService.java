package com.ilibed.music;

import com.ilibed.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class MusicService {
    private final static String SOUNDS_LOAD_PATH = "../static/assets/muz/";
    private final static String SOUNDS_STORE_PATH = "assets/muz/";
    private final MusicRepository musicRepository;

    @Autowired
    public MusicService(MusicRepository musicRepository){
        this.musicRepository = musicRepository;
    }

    public Music saveMusic(MultipartFile file, String fileName) throws ServiceException{
        byte[] bytes;
        Music music = new Music();

        try {
            if (!file.isEmpty()) {
                bytes = file.getBytes();
                Path path = new File(SOUNDS_LOAD_PATH + file.getOriginalFilename()).toPath();
                Files.write(path, bytes);
                music.setName(fileName);
                music.setPath(SOUNDS_STORE_PATH + file.getOriginalFilename());
                musicRepository.save(music);
            }
        }
        catch (IOException e){
            //logging
            throw new ServiceException(e.getMessage(), e);
        }

        return music;
    }

    public List<Music> getSounds(){
        return (List<Music>) musicRepository.findAll();
    }
}
