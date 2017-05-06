package com.ilibed.music;

import com.ilibed.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MusicController {
    private MusicService musicService;

    @Autowired
    public MusicController(MusicService musicService){
        this.musicService = musicService;
    }

    @RequestMapping(value = "/api/upload/music")
    @ResponseBody
    public ResponseEntity<Music> uploadMusic(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName){
        try {
            Music music = musicService.saveMusic(file, fileName);

            return new ResponseEntity<Music>(music, HttpStatus.OK);
        }
        catch (ServiceException e){
            //logging
            System.out.print(e.getMessage());
        }

        return new ResponseEntity<Music>(new Music(), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/api/get/sounds")
    @ResponseBody
    public ResponseEntity<List<Music>> getSoundsList(){
        //For bad??
        return new ResponseEntity<List<Music>>(musicService.getSounds(), HttpStatus.OK);
    }
}
