/**
 * 작성자 : 김준희
 * 작성일시 : 2021.09.08
 * 설명 : 일정표 서비스 구현
 */
package kr.co.enders.ums.ems.sch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.enders.ums.ems.cam.vo.TaskVO;
import kr.co.enders.ums.ems.sch.dao.ScheduleDAO;
import kr.co.enders.ums.ems.sch.vo.ScheduleVO;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	private ScheduleDAO scheduleDAO;

	@Override
	public List<TaskVO> getScheduleList(TaskVO taskVO) throws Exception{
		return scheduleDAO.getScheduleList(taskVO);
	}

	@Override
	public List<ScheduleVO> getScheduleAggrList(TaskVO taskVO) throws Exception{
		return scheduleDAO.getScheduleAggrList(taskVO);
	}
	
	@Override
	public int getScheduleGrant(TaskVO taskVO) throws Exception{
		return scheduleDAO.getScheduleGrant(taskVO);
	}

}
