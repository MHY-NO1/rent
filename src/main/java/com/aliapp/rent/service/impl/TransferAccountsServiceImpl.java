package com.aliapp.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.aliapp.rent.dao.TransferAccountsDao;
import com.aliapp.rent.entity.po.TransferAccounts;
import com.aliapp.rent.service.TransferAccountsService;
import org.springframework.stereotype.Service;

/**
 * (TransferAccounts)表服务实现类
 *
 * @author makejava
 * @since 2021-06-29 11:06:43
 */
@Service("transferAccountsService")
public class TransferAccountsServiceImpl extends ServiceImpl<TransferAccountsDao, TransferAccounts> implements TransferAccountsService {

}
