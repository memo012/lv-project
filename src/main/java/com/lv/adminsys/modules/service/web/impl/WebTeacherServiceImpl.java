package com.lv.adminsys.modules.service.web.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lv.adminsys.common.constant.LvException;
import com.lv.adminsys.common.enums.TaskTypeEnum;
import com.lv.adminsys.common.utils.JSONResult;
import com.lv.adminsys.common.utils.PagedResult;
import com.lv.adminsys.modules.dao.LvCheckDao;
import com.lv.adminsys.modules.dao.LvLeaveDao;
import com.lv.adminsys.modules.dao.LvUserDao;
import com.lv.adminsys.modules.entity.LvCheckEntity;
import com.lv.adminsys.modules.entity.LvLeaveEntity;
import com.lv.adminsys.modules.entity.LvUserEntity;
import com.lv.adminsys.modules.service.AsyncService;
import com.lv.adminsys.modules.service.impl.TeacherCheckServiceImpl;
import com.lv.adminsys.modules.service.web.IWebTeacherService;
import com.lv.adminsys.modules.vo.web.WebTeacherCheckDTO;
import com.lv.adminsys.modules.vo.web.WebTeacherCheckVO;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiang
 * @version 1.0
 * @description TODO
 * @date 2019/12/25 下午2:42
 **/
@Service
@Transactional
public class WebTeacherServiceImpl implements IWebTeacherService {

    /**
     * 老师工号前缀
     */
    @Value("${teacher.start}")
    private String teacherNumStart;

    @Resource
    private TaskService taskService;

    @Resource
    private TeacherCheckServiceImpl teacherCheckServiceImpl;

    @Autowired
    private LvLeaveDao lvLeaveDao;

    @Autowired
    private LvUserDao lvUserDao;

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private LvCheckDao lvCheckDao;

    @Override
    public JSONResult queryTaskByUserId(WebTeacherCheckDTO teacherCheckDTO) {
        if (!teacherCheckDTO.createValidate()) {
            return JSONResult.build(402, LvException.ErrorMsg.REQUEST_PARAM_ERROR, null);
        }
        if (teacherCheckDTO.getAccount().startsWith(teacherNumStart)) {
            return queryTeacherMessage(teacherCheckDTO);
        }
        return findStudentMessage(teacherCheckDTO);
    }


    /**
     * 学生未审核任务和历史列表显示
     *
     * @param teacherCheckDTO type类型  0 -- 历史记录  1 -- 未审核记录
     * @return
     */
    private JSONResult findStudentMessage(WebTeacherCheckDTO teacherCheckDTO) {
        // 审核过的信息(历史记录)  0 -- 历史记录  1 -- 未审核记录
        if (teacherCheckDTO.getType() == 0) {
            PagedResult pagedResult = studentTaskList(teacherCheckDTO, TaskTypeEnum.HISTORY_TYPE.getTypeName());
            if (pagedResult == null) {
                return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
            }
            return JSONResult.ok(pagedResult);
        }
        // 学生未审核请假任务
        PagedResult pagedResult = studentTaskList(teacherCheckDTO, TaskTypeEnum.NO_AUDIT_TYPE.getTypeName());
        if (pagedResult == null) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        return JSONResult.ok(pagedResult);
    }


    /**
     * 老师未审核任务和历史列表显示
     *
     * @param teacherCheckDTO
     * @return
     */
    private JSONResult queryTeacherMessage(WebTeacherCheckDTO teacherCheckDTO) {
        // 审核过的信息(历史记录)  0 -- 历史记录  1 -- 未审核记录
        if (teacherCheckDTO.getType() == 0) {
            PagedResult pagedResult1 = historyList(teacherCheckDTO);
            if (pagedResult1 == null) {
                return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
            }
            return JSONResult.ok(pagedResult1);
        }
        // 老师未审核请假任务
        List<WebTeacherCheckVO> list = notAudit(teacherCheckDTO);
        if (CollectionUtils.isEmpty(list)) {
            return JSONResult.build(401, LvException.ErrorMsg.CAN_ONT_FIND_RECORD, null);
        }
        return JSONResult.ok(teacherDataPage(list, teacherCheckDTO));
    }


    /**
     * 老师分页封装
     *
     * @return
     */
    private PagedResult teacherDataPage(List<WebTeacherCheckVO> list, WebTeacherCheckDTO teacherCheckDTO) {
        PagedResult pagedResult = new PagedResult();
        pagedResult.setRows(list);
        pagedResult.setPage(teacherCheckDTO.getPageNum());
        pagedResult.setTotal(teacherCheckDTO.getPageSize());
        pagedResult.setRecords(teacherCheckServiceImpl.checkTaskByTeacherNum(teacherCheckDTO.getAccount()).size());
        return pagedResult;
    }

    /**
     * 老师未审核的请假任务
     *
     * @return
     */
    private List<WebTeacherCheckVO> notAudit(WebTeacherCheckDTO teacherCheckDTO) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(teacherCheckDTO.getAccount())
                .listPage(teacherCheckDTO.getPageNum(), teacherCheckDTO.getPageSize());
        if (CollectionUtils.isEmpty(tasks)) {
            return null;
        }
        List<WebTeacherCheckVO> list = new ArrayList<>();

        for (Task task :
                tasks) {
            LvLeaveEntity leaveInfo = teacherCheckServiceImpl.findTask(task.getProcessInstanceId());
            if (leaveInfo != null) {
                LvUserEntity user = lvUserDao.findUseMsgByUserNum(leaveInfo.getLvUserNum());
                try {
                    list.add(new WebTeacherCheckVO(
                            user.getLvUserName(), user.getLvUserNum(), leaveInfo.getLvBeginTime(), leaveInfo.getLvEndTime(),
                            task.getId(), leaveInfo.getLvId(), leaveInfo.getLvRelativeType(), leaveInfo.getLvRelativeName(),
                            leaveInfo.getLvRelativePhone(), leaveInfo.getLvApplyDetail(), leaveInfo.getLvReason(), leaveInfo.getLvStatus()

                    ));
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return list;
    }


    /**
     * 审核过的信息
     *
     * @param teacherCheckDTO
     * @return
     */
    public PagedResult historyList(WebTeacherCheckDTO teacherCheckDTO) {
        PagedResult grid = new PagedResult();
        List<WebTeacherCheckVO> list = new ArrayList<>();
        // 查出审核过的
        IPage<LvCheckEntity> lvCheckEntityIPage =
                lvCheckDao.selectPage(new Page<>(teacherCheckDTO.getPageNum(), teacherCheckDTO.getPageSize()),
                        new QueryWrapper<LvCheckEntity>()
                                .eq("lv_teacher_num", teacherCheckDTO.getAccount())
                                .orderByDesc("lv_check_id"));
        if (lvCheckEntityIPage == null) {
            return null;
        }
        for (LvCheckEntity lc :
                lvCheckEntityIPage.getRecords()) {
            LvLeaveEntity leaveInfo =
                    lvLeaveDao.selectOne(new QueryWrapper<LvLeaveEntity>().eq("lv_id", lc.getLvId()));
            LvUserEntity user =
                    lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", leaveInfo.getLvUserNum()));
            try {
                list.add(new WebTeacherCheckVO(
                        user.getLvUserName(), user.getLvUserNum(), leaveInfo.getLvBeginTime(), leaveInfo.getLvEndTime(),
                        null, leaveInfo.getLvId(), leaveInfo.getLvRelativeType(), leaveInfo.getLvRelativeName(),
                        leaveInfo.getLvRelativePhone(), leaveInfo.getLvApplyDetail(), leaveInfo.getLvReason(), leaveInfo.getLvStatus()

                ));
            } catch (Exception e) {
                return null;
            }
        }
        grid.setPage(teacherCheckDTO.getPageNum());
        grid.setTotal(lvCheckEntityIPage.getPages());
        grid.setRecords(lvCheckEntityIPage.getTotal());
        grid.setRows(list);
        return grid;
    }

    /**
     * 学生历史信息
     *
     * @param teacherCheckDTO
     * @param type            0 -- 历史 1 -- 未审核
     * @return
     */
    public PagedResult studentTaskList(WebTeacherCheckDTO teacherCheckDTO, Integer type) {
        IPage<LvLeaveEntity> lvLeaveEntities;
        if (type.equals(TaskTypeEnum.HISTORY_TYPE.getTypeName())) {
            lvLeaveEntities =
                    lvLeaveDao.selectPage(new Page<>(teacherCheckDTO.getPageNum(), teacherCheckDTO.getPageSize()),
                            new QueryWrapper<LvLeaveEntity>()
                                    .eq("lv_user_num", teacherCheckDTO.getAccount())
                                    .orderByDesc("lv_id"));
        } else {
            lvLeaveEntities =
                    lvLeaveDao.selectPage(new Page<>(teacherCheckDTO.getPageNum(), teacherCheckDTO.getPageSize()),
                            new QueryWrapper<LvLeaveEntity>()
                                    .eq("lv_user_num", teacherCheckDTO.getAccount())
                                    .eq("lv_status", "ing")
                                    .orderByDesc("lv_id"));
        }
        if (CollectionUtils.isEmpty(lvLeaveEntities.getRecords())) {
            return null;
        }
        List<WebTeacherCheckVO> list = new ArrayList<>();
        LvUserEntity lvUserEntity =
                lvUserDao.selectOne(new QueryWrapper<LvUserEntity>().eq("lv_user_num", teacherCheckDTO.getAccount()));
        for (LvLeaveEntity lv :
                lvLeaveEntities.getRecords()) {
            list.add(new WebTeacherCheckVO(
                    lvUserEntity.getLvUserName(), lvUserEntity.getLvUserNum(), lv.getLvBeginTime(), lv.getLvEndTime(),
                    null, lv.getLvId(), lv.getLvRelativeType(), lv.getLvRelativeName(), lv.getLvRelativePhone(),
                    lv.getLvApplyDetail(), lv.getLvReason(), lv.getLvStatus()

            ));
        }
        PagedResult grid = new PagedResult();
        grid.setPage(teacherCheckDTO.getPageNum());
        grid.setTotal(lvLeaveEntities.getPages());
        grid.setRecords(lvLeaveEntities.getTotal());
        grid.setRows(list);
        return grid;
    }
}
