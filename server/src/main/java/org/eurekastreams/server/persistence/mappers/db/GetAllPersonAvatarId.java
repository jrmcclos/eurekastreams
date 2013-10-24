/*
 * Copyright (c) 2013 Lockheed Martin Corporation
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
import org.apache.commons.logging.LogFactory;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eurekastreams.server.persistence.mappers.ReadMapper;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * This mapper returns a list of imageIdentifier and avatar image blobs
 * 
 */
public class GetAllPersonAvatarId extends ReadMapper<String, List<Map<String, Object>>>
{
	private final Log logger = LogFactory.getLog(GetAllPersonAvatarId.class);
    /**
     * reutrn a map of image identifiers and image blobs
     * {@inheritDoc}
     * .
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> execute(final String inRequest)
    {
        return (List<Map<String, Object>>) getEntityManager().createQuery(
                "select new map(i.imageIdentifier as imageIdentifier, i.imageBlob as imageBlob)"
                        + " from Image i where i.imageIdentifier in (:avatarids)")
                        .setParameter("avatarids", new ArrayList<String>(Arrays.asList(inRequest.split(",")))).getResultList();
    }
}
