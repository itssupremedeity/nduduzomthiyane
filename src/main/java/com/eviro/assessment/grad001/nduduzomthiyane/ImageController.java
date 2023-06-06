package com.eviro.assessment.grad001.nduduzomthiyane;


import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/api/image")
public class ImageController {

    @GetMapping(value = "/{name}/{surname}/{\\w\\.\\w}")
    public FileSystemResource getHttpImgLink(@PathVariable String name,@PathVariable String surname){



        return null;
    }





}
