/*
 * Copyright (c) 2011 Lockheed Martin Corporation
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
package org.eurekastreams.server.persistence.mappers.db.metrics;

import java.util.List;

import org.eurekastreams.server.domain.dto.StreamDTO;
import org.eurekastreams.server.persistence.mappers.MapperTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test fixture for GetStreamsByDailyAverageViewsDbMapper.
 */
public class GetStreamsByDailyAverageViewersDbMapperTest extends MapperTest
{
    /**
     * System under test.
     */
    private GetStreamsByDailyAverageViewersDbMapper sut;

    /**
     * Setup method.
     */
    @Before
    public void setup()
    {
        sut = new GetStreamsByDailyAverageViewersDbMapper(10);
        sut.setEntityManager(getEntityManager());
    }

    /**
     * Test execute.
     */
    @Test
    public void test()
    {
        List<StreamDTO> results = sut.execute(10);
        Assert.assertEquals(2L, results.size());

        // PERSON: fordp2
        StreamDTO dto1 = results.get(0);

        // PERSON: fordp
        StreamDTO dto2 = results.get(1);

        Assert.assertEquals("fordp2", dto1.getUniqueId());
        Assert.assertEquals("fordp", dto2.getUniqueId());

        // two data points - 100000 and 99999, 3 days. we round up, so get 66667
        Assert.assertEquals(66667, dto1.getFollowersCount());

        // one data point - 100000, 3 days. we round up, so get 33334
        Assert.assertEquals(33334, dto2.getFollowersCount());
    }

    /**
     * Test with no metrics data in the database.
     */
    @Test
    public void testWithNoMetricsData()
    {
        getEntityManager().createQuery("DELETE FROM DailyUsageSummary").executeUpdate();
        getEntityManager().clear();
        Assert.assertEquals(0, sut.execute(10).size());
    }
}
