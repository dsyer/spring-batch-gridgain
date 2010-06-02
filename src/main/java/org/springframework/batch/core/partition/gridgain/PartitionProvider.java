package org.springframework.batch.core.partition.gridgain;

import java.util.Set;

import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.StepExecutionSplitter;

public class PartitionProvider {

	private final StepExecutionSplitter stepSplitter;
	private final StepExecution stepExecution;

	public PartitionProvider(StepExecutionSplitter stepSplitter, StepExecution stepExecution) {
		this.stepSplitter = stepSplitter;
		this.stepExecution = stepExecution;
	}

	public String getStepName() {
		return stepSplitter.getStepName();
	}

	public Set<StepExecution> getStepExecutions(int gridSize) throws JobExecutionException {
		return stepSplitter.split(stepExecution, gridSize);
	}

}
