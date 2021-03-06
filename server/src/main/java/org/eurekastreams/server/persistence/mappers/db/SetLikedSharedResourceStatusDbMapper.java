/*
 * Copyright (c) 2010-2011 Lockheed Martin Corporation
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

package org.eurekastreams.server.persistence.mappers.db;

import org.apache.commons.logging.Log;
import org.eurekastreams.commons.logging.LogFactory;
import org.eurekastreams.server.domain.stream.LikedSharedResource;
import org.eurekastreams.server.persistence.mappers.BaseArgDomainMapper;
import org.eurekastreams.server.persistence.mappers.requests.SetSharedResourceLikeMapperRequest;

/**
 * DB mapper to set the like/unlike status of a shared resource for a user.
 */
public class SetLikedSharedResourceStatusDbMapper extends
        BaseArgDomainMapper<SetSharedResourceLikeMapperRequest, Boolean>
{
    /**
     * Logger.
     */
    private Log log = LogFactory.make();

    /**
     * Set account status for a person.
     * 
     * @param inRequest
     *            the request
     * @return true if successful.
     */
    @Override
    public Boolean execute(final SetSharedResourceLikeMapperRequest inRequest)
    {
        // delete any existing like - simplifies logic
        getEntityManager().createQuery(
                "DELETE LikedSharedResource WHERE pk.personId = :personId "
                        + "AND pk.sharedResourceId = :sharedResourceId").setParameter("personId",
                inRequest.getPersonId()).setParameter("sharedResourceId", inRequest.getSharedResource().getId())
                .executeUpdate();

        // like if requested
        if (inRequest.getLikedStatus())
        {
            LikedSharedResource sharedResource = new LikedSharedResource(inRequest.getSharedResource().getId(),
                    inRequest.getPersonId());
            getEntityManager().persist(sharedResource);
        }
        return true;
    }
}
