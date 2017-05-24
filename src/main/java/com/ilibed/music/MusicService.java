package com.ilibed.music;

import com.ilibed.cloud.CloudStorage;
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
    private final MusicRepository musicRepository;

    @Autowired
    public MusicService(MusicRepository musicRepository){
        this.musicRepository = musicRepository;
    }

    public Music saveMusic(MultipartFile file, String fileName) throws ServiceException{

        if(file == null){
            throw new NullPointerException("MusicService, saveMusic : file parameter is null");
        }

        if(fileName == null){
            throw new NullPointerException("MusicService, saveMusic : fileName parameter is null");
        }


        byte[] bytes;
        Music music = new Music();

        try {
            if (!file.isEmpty()) {
                bytes = file.getBytes();
                File savedFile = new File(file.getOriginalFilename());
                Path path = savedFile.toPath();
                System.out.print(path);
                Files.write(path, bytes);
                String cloudPath = CloudStorage.uploadFile(savedFile);
                music.setName(fileName);
                music.setPath(cloudPath);
                musicRepository.save(music);
                savedFile.delete();
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
