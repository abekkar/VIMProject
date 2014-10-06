package com.csc.vim.framework.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.csc.vim.framework.util.DateUtils;

public class GenericDao {

	protected final Logger logger = LoggerFactory.getLogger(GenericDao.class);

	@Autowired
	protected DateUtils dateUtils;
}
