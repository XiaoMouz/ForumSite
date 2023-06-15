package com.mou.gameforum.controller;


import com.mou.gameforum.entity.vo.RequestResult;
import com.mou.gameforum.service.StorageService;
import com.mou.gameforum.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletResponse;
import okhttp3.Response;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * 别动这玩意，我不知道怎么跑起来的
 */
@Controller
public class FileControl {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FileControl.class);
    StorageService storageService;

    @Autowired
    public FileControl(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/files")
    public String listUploadedFiles(Model model) throws IOException {

        List<String> files = storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileControl.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString()
        ).collect(Collectors.toList());
        files.removeIf(s -> !s.substring(s.lastIndexOf("/")).contains("."));
        List<String> directories = storageService.loadAllDirectories().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileControl.class,
                        "listDirectoriesInDirectory", path.getFileName().toString(),model).build().toUri().toString()
        ).collect(Collectors.toList());

        model.addAttribute("files", files);
        model.addAttribute("directories", directories);

        return "file/list";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/directories/{directory:.+}/files")
    public String listFilesInDirectory(@PathVariable String directory, Model model) throws IOException {

        List<String> files = storageService.listResources(null,directory).stream().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileControl.class,
                        "serveFileInDirectory", directory, path.getFilename().toString()).build().toUri().toString()
        ).collect(Collectors.toList());

        model.addAttribute("files", files);
        model.addAttribute("directory", directory);

        return "file/list-directories-in-directory";
    }

    @GetMapping("/directories/{directory:.+}")
    public String listDirectoriesInDirectory(@PathVariable String directory, Model model) throws IOException {
        // check directory is muti-level if is set parentdirectory
        String parentDirectory = null;
        if(directory.contains("/")){
            parentDirectory = directory.substring(0,directory.lastIndexOf("/"));
        }
        List<String> directories = storageService.listDirectories(parentDirectory,directory).map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileControl.class,
                        "serveDirectoryInDirectory", directory, path.getFileName().toString(),model).build().toUri().toString()
        ).collect(Collectors.toList());

        List<String> files = storageService.listResources(null,directory).stream().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileControl.class,
                        "serveFileInDirectory", directory, path.getFilename().toString()).build().toUri().toString()
        ).collect(Collectors.toList());

        model.addAttribute("files", files);
        model.addAttribute("directories", directories);
        model.addAttribute("directory", directory);

        return "file/list-directories-in-directory";
    }

    @GetMapping("/files/{directory:.+}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFileInDirectory(@PathVariable String directory, @PathVariable String filename) {
        logger.info("download"+directory+"/"+filename);
        Resource file = storageService.loadAsResource(directory + "/" + filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/directories/{directory:.+}/{filename:.+}")
    public String serveDirectoryInDirectory(@PathVariable String directory, @PathVariable String filename, Model model) {

        String dir = directory + "/" + filename;

        List<String> files = storageService.listResources(null,dir).stream().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileControl.class,
                        "serveFileInDirectory", dir, path.getFilename().toString()).build().toUri().toString()
        ).collect(Collectors.toList());

        String parentDirectory = null;
        if(directory.contains("/")){
            parentDirectory = directory.substring(0,directory.lastIndexOf("/"));
        }
        List<String> directories = storageService.listDirectories(parentDirectory,dir).map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileControl.class,
                        "serveDirectoryInDirectory", dir, path.getFileName().toString(),model).build().toUri().toString()
        ).collect(Collectors.toList());

        model.addAttribute("files", files);
        model.addAttribute("directories", directories);
        model.addAttribute("directory", dir);

        return "file/list-directories-in-directory";
    }

    @PostMapping("/files/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   HttpServletResponse response) throws IOException {

        storageService.store(file);

        ResponseUtils.responseJson(response,new RequestResult<>(200, "Upload success", null));
        return null;
    }

    @PostMapping("/files/upload/{path:.+}")
    public String handleFileUploadToPath(@RequestParam("file") MultipartFile file,
                                         @PathVariable String path,
                                         HttpServletResponse response) throws IOException {

        storageService.storeToPath(file, path);
        ResponseUtils.responseJson(response,new RequestResult<>(200, "Upload success", null));
        return null;
    }

    //@PostMapping("/directories/{directory:.+}/create-directory")
    public String handleCreateDirectory(@RequestParam("name") String name, @PathVariable String directory) {

        storageService.createDirectory(directory + "/" + name);

        return new RequestResult<>(200, "Directory created successfully", null).toString();
    }

    //@PostMapping("/directories/{directory:.+}/delete-directory")
    public String handleDeleteDirectory(@RequestParam("name") String name, @PathVariable String directory) {

        storageService.deleteDirectory(directory + "/" + name);

        return new RequestResult<>(200, "Directory deleted successfully", null).toString();
    }

}
