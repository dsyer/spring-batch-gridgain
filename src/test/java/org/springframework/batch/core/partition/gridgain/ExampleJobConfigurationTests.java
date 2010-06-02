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

import static org.junit.Assert.assertNotNull;

import org.gridgain.grid.GridFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dave Syer
 * 
 */
@ContextConfiguration(locations = { "/test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ExampleJobConfigurationTests {

	static {
		System.setProperty("GRIDGAIN_HOME",System.getProperty("user.home")+"/Programs/gridgain-2.1.1");
	}

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;
	@Autowired
	@Qualifier("step")
	private Step step;

	@BeforeClass
	public static void start() throws Exception {
		GridFactory.start();
	}

	@AfterClass
	public static void stop() throws Exception {
		GridFactory.stop(true);
	}

	@Test
	public void testSimpleProperties() throws Exception {
		assertNotNull(jobLauncher);
		assertNotNull(step);
	}

	@Test
	public void testLaunchJob() throws Exception {
		assertNotNull(jobLauncher.run(job, new JobParametersBuilder().addString("run.id", "integration.test").toJobParameters()));
	}

}
