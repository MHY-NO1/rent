package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.QuestionDao;
import com.aliapp.rent.entity.po.Question;
import com.aliapp.rent.service.QuestionService;
import org.springframework.stereotype.Service;

/**
 * (Question)表服务实现类
 *
 * @author makejava
 * @since 2021-05-18 09:49:05
 */
@Service("questionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionDao, Question> implements QuestionService {

}
