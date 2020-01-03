package com.lv.adminsys.modules.service.web.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lv.adminsys.common.constant.LvException;
import com.lv.adminsys.common.utils.BeanUtil;
import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.modules.dao.LvTeacherDao;
import com.lv.adminsys.modules.dao.LvUserDao;
import com.lv.adminsys.modules.entity.LvTeacherEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.service.web.IWebUserService;
import com.lv.adminsys.modules.shiro.ShiroEncrypt;
import com.lv.adminsys.modules.vo.web.user.StudentMessageVO;
import com.lv.adminsys.modules.vo.web.user.TeacherMessageVO;
import com.lv.adminsys.modules.vo.web.user.UserMessageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/28 下午4:09
 **/
@Service
@Transactional
public class WebUserServiceImpl implements IWebUserService {

    /**
     * 老师工号前缀
     */
    @Value("${teacher.start}")
    private String teacherNumStart;

    @Autowired
    private LvUserDao lvUserDao;

    @Autowired
    private LvTeacherDao lvTeacherDao;

    @Override
    public JSONResult findUserMessage(String userNum) {
        if (StringUtils.isEmpty(userNum)) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        if (userNum.startsWith(teacherNumStart)) {
            return findTeacherMessage(userNum);
        }
        return findStudentMessage(userNum);
    }

    @Override
    public JSONResult updateUserMessage(UserMessageVO userMessageVO) {
        if (!userMessageVO.isCreate()) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        if (userMessageVO.getLvUserNum().startsWith(teacherNumStart)) {
            return updateTeacherMessage(userMessageVO);
        }
        return updateStudentMessage(userMessageVO);
    }

    @Override
    public JSONResult updateUserPassword(String userNum, String password) {
        if (StringUtils.isEmpty(userNum) || StringUtils.isEmpty(password)) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        if (userNum.startsWith(teacherNumStart)) {
            return updateTeacherPassword(userNum, password);
        }
        return updateStudentPassword(userNum, password);
    }

    /**
     * 修改老师密码
     * @param userNum
     * @param password
     * @return
     */
    private JSONResult updateTeacherPassword(String userNum, String password) {
        String newPwd = ShiroEncrypt.encrypt(userNum, password);
        LvTeacherEntity lvTeacherEntity = new LvTeacherEntity();
        lvTeacherEntity.setLvTeacherNum(userNum);
        lvTeacherEntity.setLvTeacherPassword(newPwd);
        int result = lvTeacherDao.update(lvTeacherEntity, new QueryWrapper<LvTeacherEntity>().eq("lv_teacher_num", userNum));
        if (result > 0) {
            return JSONResult.ok();
        }
        return JSONResult.errorMsg("系统错误");
    }

    /**
     *  修改学生密码
     * @param userNum
     * @param password
     * @return
     */
    private JSONResult updateStudentPassword(String userNum, String password) {
        String newPwd = ShiroEncrypt.encrypt(userNum, password);
        LvUserEntity lvUserEntity = new LvUserEntity();
        lvUserEntity.setLvUserNum(userNum);
        lvUserEntity.setLvUserPassword(newPwd);
        int result = lvUserDao.update(lvUserEntity, new QueryWrapper<LvUserEntity>().eq("lv_user_num", userNum));
        if (result > 0) {
            return JSONResult.ok();
        }
        return JSONResult.errorMsg("系统错误");
    }

    /**
     * 修改老师个人信息
     *
     * @param userMessageVO
     * @return
     */
    private JSONResult updateTeacherMessage(UserMessageVO userMessageVO) {
        LvTeacherEntity lvTeacherEntity = BeanUtil.EtoT(userMessageVO, LvTeacherEntity.class);
        int result = lvTeacherDao.update(lvTeacherEntity, new QueryWrapper<LvTeacherEntity>().eq("lv_teacher_num", userMessageVO.getLvUserNum()));
        if (result > 0) {
            return JSONResult.ok();
        }
        return JSONResult.errorMsg("系统错误");
    }

    /**
     * 修改学生个人信息
     *
     * @param userMessageVO
     * @return
     */
    private JSONResult updateStudentMessage(UserMessageVO userMessageVO) {
        LvUserEntity lvUserEntity = BeanUtil.EtoT(userMessageVO, LvUserEntity.class);
        int result = lvUserDao.update(lvUserEntity, new QueryWrapper<LvUserEntity>().eq("lv_user_num", userMessageVO.getLvUserNum()));
        if (result > 0) {
            return JSONResult.ok();
        }
        return JSONResult.errorMsg("系统错误");
    }

    /**
     * 查看老师个人信息
     *
     * @param userNum
     * @return
     */
    private JSONResult findTeacherMessage(String userNum) {
        LvTeacherEntity userEntity = lvTeacherDao.selectOne(new QueryWrapper<LvTeacherEntity>().eq("lv_teacher_num", userNum));
        if (userEntity == null) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        TeacherMessageVO t = BeanUtil.EtoT(userEntity, TeacherMessageVO.class);
        return JSONResult.ok(new UserMessageVO(t.getLvEmail(), t.getLvQq(), t.getLvTeacherPhone(), t.getLvTeacherNum()));
    }

    /**
     * 查看学生个人信息
     *
     * @param userNum
     * @return
     */
    private JSONResult findStudentMessage(String userNum) {
        LvUserEntity userEntity = lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", userNum));
        if (userEntity == null) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        StudentMessageVO studentMessageVO = BeanUtil.EtoT(userEntity, StudentMessageVO.class);
        return JSONResult.ok(studentMessageVO);
    }
}
