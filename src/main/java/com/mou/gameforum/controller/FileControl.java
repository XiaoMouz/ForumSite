package com.mou.gameforum.controller;


import com.mou.gameforum.entity.vo.RequestResult;
import com.mou.gameforum.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;


@Controller
public class FileControl {
    StorageService storageService;

    @Autowired
    public FileControl(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/files")
    public String listUploadedFiles(Model model) throws IOException {


        model.addAttribute("files", storageService.loadAll().map(
                        path -> MvcUriComponentsBuilder.fromMethodName(FileControl.class,
                                "serveFile", path.getFileName().toString()).build().toUri().toString()
                ).collect(Collectors.toList()));

        return "file/list";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/files/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

        storageService.store(file);

        return new RequestResult<>(200, "Upload success", null).toString();
    }
}
