package com.mou.gameforum.service.content.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mou.gameforum.entity.Comments;
import com.mou.gameforum.mapper.content.CommentMapper;
import com.mou.gameforum.service.content.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl
        extends ServiceImpl<CommentMapper, Comments>
        implements CommentService {

}
