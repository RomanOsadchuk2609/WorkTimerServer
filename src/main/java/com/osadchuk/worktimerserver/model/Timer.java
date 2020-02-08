package com.osadchuk.worktimerserver.model;

import lombok.Data;

import java.util.Date;

@Data
public class Timer {
	private long id;
	private Date starttime;
	private Date endtime;
	private long performerId;
}
