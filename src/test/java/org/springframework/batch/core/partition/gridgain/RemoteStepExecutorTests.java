/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.core.partition.gridgain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.gridgain.RemoteStepExecutor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.support.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dave Syer
 * 
 */
@ContextConfiguration(locations = "/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class RemoteStepExecutorTests {

	@Autowired
	private JobRepository jobRepository;

	@Test
	public void testCreateNewInstance() throws Exception {
		RemoteStepExecutor kit = new RemoteStepExecutor(null, "foo", null);
		assertEquals("foo", kit.getStepName());
	}

	@Test
	public void testExecuteRemotely() throws Exception {
		StepExecution stepExecution = jobRepository
				.createJobExecution("job", new JobParametersBuilder().addDate("date", new Date()).toJobParameters())
				.createStepExecution("step");
		jobRepository.add(stepExecution);
		RemoteStepExecutor kit = new RemoteStepExecutor("/launch-context.xml", "step", stepExecution);
		// simulate remote execution by deserializing
		kit = (RemoteStepExecutor) SerializationUtils.deserialize(SerializationUtils.serialize(kit));
		assertNotNull(kit.execute());
	}

}
