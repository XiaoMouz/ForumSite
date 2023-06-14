package com.mou.gameforum.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {void init();

    /**
     * 存储文件
     * @param file 文件
     */
    void store(MultipartFile file);

    /**
     * 存储文件至目录
     * @param file 文件
     * @param path 路径
     */
    void storeToPath(MultipartFile file,String path);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

}
