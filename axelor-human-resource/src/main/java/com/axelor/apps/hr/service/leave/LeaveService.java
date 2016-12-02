/**
 * Axelor Business Solutions
 *
 * Copyright (C) 2016 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axelor.apps.hr.service.leave;

import java.io.IOException;
import java.math.BigDecimal;

import javax.mail.MessagingException;

import org.joda.time.LocalDate;

import com.axelor.apps.base.db.WeeklyPlanning;
import com.axelor.apps.hr.db.Employee;
import com.axelor.apps.hr.db.LeaveRequest;
import com.axelor.apps.message.db.Message;
import com.axelor.auth.db.User;
import com.axelor.exception.AxelorException;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.persist.Transactional;

public interface LeaveService {
	
	public BigDecimal computeDuration(LeaveRequest leave) throws AxelorException;

	@Transactional(rollbackOn = {AxelorException.class, Exception.class})
	public void manageSentLeaves(LeaveRequest leave) throws AxelorException;

	@Transactional(rollbackOn = {AxelorException.class, Exception.class})
	public void manageValidLeaves(LeaveRequest leave) throws AxelorException;

	@Transactional(rollbackOn = {AxelorException.class, Exception.class})
	public void manageRefuseLeaves(LeaveRequest leave) throws AxelorException;

	@Transactional(rollbackOn = {AxelorException.class, Exception.class})
	public void manageCancelLeaves(LeaveRequest leave) throws AxelorException;

	public double computeStartDateWithSelect(LocalDate date, int select, WeeklyPlanning weeklyPlanning);

	public double computeEndDateWithSelect(LocalDate date, int select, WeeklyPlanning weeklyPlanning);

	@Transactional
	public LeaveRequest createEvents(LeaveRequest leave) throws AxelorException;
	
	public BigDecimal computeLeaveDays(LocalDate fromDate, LocalDate toDate, User user) throws AxelorException;
	
	public BigDecimal computeLeaveDaysByLeaveRequest(LocalDate fromDate, LocalDate toDate, LeaveRequest leaveRequest, Employee employee) throws AxelorException;
	
	public void getLeaveReason(ActionRequest request, ActionResponse response);

	@Transactional
	public void insertLeave(ActionRequest request, ActionResponse response) throws AxelorException;
	
	@Transactional
	public void cancelLeave(LeaveRequest leaveRequest);
	
	@Transactional(rollbackOn = {AxelorException.class, Exception.class})
	public void confirm(LeaveRequest leaveRequest) throws AxelorException;
				
	public Message sendConfirmationEmail(LeaveRequest leaveRequest) throws AxelorException, ClassNotFoundException, InstantiationException, IllegalAccessException, MessagingException, IOException;
		
	@Transactional(rollbackOn = {AxelorException.class, Exception.class})
	public void validate(LeaveRequest leaveRequest) throws AxelorException;
	
	public Message sendValidationEmail(LeaveRequest leaveRequest) throws AxelorException, ClassNotFoundException, InstantiationException, IllegalAccessException, MessagingException, IOException;
	
	@Transactional(rollbackOn = {AxelorException.class, Exception.class})
	public void refuse(LeaveRequest leaveRequest) throws AxelorException;

	public Message sendRefusalEmail(LeaveRequest leaveRequest) throws AxelorException, ClassNotFoundException, InstantiationException, IllegalAccessException, MessagingException, IOException;
		
	public boolean willHaveEnoughDays(LeaveRequest leaveRequest);
}
