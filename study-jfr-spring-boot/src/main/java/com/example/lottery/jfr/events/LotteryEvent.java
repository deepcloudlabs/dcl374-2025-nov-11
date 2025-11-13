package com.example.lottery.jfr.events;

import java.util.UUID;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Enabled
@Category("Rest Controller Event")
@Description("This is a rest controller event")
@Label("Rest Controller Event")
@Name("com.example.lottery.jfr.events.LotteryEvent")
public class LotteryEvent extends Event {
	public String id = UUID.randomUUID().toString();
	public String methodName;
	public String className;
	public long interval;
	public long sequenceId;
}
