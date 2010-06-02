package org.springframework.batch.core.partition.gridgain;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.SpringVersion;

public class RemoteStepExecutor implements Serializable {
	
	private Log logger = LogFactory.getLog(getClass());
	
	private final StepExecution stepExecution;

	private final String stepName;

	private final String configLocation;

	public RemoteStepExecutor(String configLocation, String stepName, StepExecution stepExecution) {
		this.configLocation = configLocation;
		this.stepName = stepName;
		this.stepExecution = stepExecution;
	}

	public StepExecution execute() {

		Step step = (Step) new ClassPathXmlApplicationContext(configLocation).getBean(stepName, Step.class);;

		logger.info("Spring Version: " + SpringVersion.getVersion());
		
		try {
			step.execute(stepExecution);
		}
		catch (JobInterruptedException e) {
			stepExecution.getJobExecution().setStatus(BatchStatus.STOPPING);
			throw new UnexpectedJobExecutionException("TODO: this should result in a stop", e);
		}

		return stepExecution;

	}

	public String getStepName() {
		return stepName;
	}

}
