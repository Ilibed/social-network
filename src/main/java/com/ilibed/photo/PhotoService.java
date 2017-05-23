package com.ilibed.photo;

import com.ilibed.cloud.CloudStorage;
import com.ilibed.exception.ServiceException;
import com.ilibed.music.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PhotoService {
    private PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository){
        this.photoRepository = photoRepository;
    }

    public Photo savePhoto(Photo photo){
        return photoRepository.save(photo);
    }

    public Photo findById(Integer id){
        return photoRepository.findOne(id);
    }

    public Photo createPhoto(MultipartFile file, Integer ownerId) throws ServiceException{
        Photo photo = new Photo();
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
                photo.setOwnerId(ownerId);
                photo.setPath(cloudPath);
                savedFile.delete();
            }
        }
        catch (IOException e){
            //logging
            throw new ServiceException(e.getMessage(), e);
        }

        return photo;
    }
}
