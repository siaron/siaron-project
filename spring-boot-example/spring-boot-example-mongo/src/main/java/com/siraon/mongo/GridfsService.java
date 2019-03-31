package com.siraon.mongo;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author xielongwang
 * @create 2019-03-319:52 PM
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Service
public class GridfsService {


    @Autowired
    private GridFsTemplate gridFsTemplate;


    /**
     * 插入文件
     *
     * @param file
     * @return
     */
    public GridFSInputFile save(MultipartFile file) {
        try {
            gridFsTemplate.store(file.getInputStream(), file.getName(), file.getContentType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GridFSDBFile getById(ObjectId objectId) {
        return null;
    }
}