/*
 * Copyright (c) 2010 Lockheed Martin Corporation
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.eurekastreams.commons.logging.LogFactory;
import org.eurekastreams.server.domain.EntityType;
import org.eurekastreams.server.domain.stream.Activity;
import org.eurekastreams.server.persistence.mappers.BaseArgDomainMapper;
import org.eurekastreams.server.persistence.mappers.cache.CacheKeys;

/**
 * This mapper finds all cache keys that could contain a reference to one of the activities passed as input. Lists of
 * starred activities are not included, nor are the streams that the activities were posted to .
 * 
 * NOTE: This is probably incomplete. This is used for deleting activities. Rather than maintain these lists, we're
 * looking into ignoring deleted activity ids in lists and removing those ids as they're found.
 */
public class GetListsContainingActivities extends BaseArgDomainMapper<List<Long>, List<String>>
{
    /**
     * Local instance of logger.
     */
    private final Log logger = LogFactory.make();

    /**
     * Execute database queries to find which cached lists of activity ids could contain a reference to a given list of
     * activity ids.
     * 
     * @param activityIds
     *            the list of activity ids.
     * @return the list of cache keys that could contain a reference to the input activities.
     */
    @SuppressWarnings("unchecked")
    public List<String> execute(final List<Long> activityIds)
    {
        Set<String> lists = new HashSet<String>();
        Set<String> authorAccountIds = new HashSet<String>();

        if (logger.isTraceEnabled())
        {
            logger.trace("Retrieve the activities for the supplied activity ids: " + activityIds.size());
        }

        List<Activity> activities = getHibernateSession().createQuery("from Activity where id in (:activityIds)")
                .setParameterList("activityIds", activityIds).list();

        if (logger.isTraceEnabled())
        {
            logger.trace("Loop through the retrieved activities " + activities.size()
                    + " to build the list of stream scopes and the authorAccountIds.");
        }
        for (Activity activity : activities)
        {
            // Add the destination streamscope to the list of streamscopes that need
            // to have their corresponding streamviews looked up for.
            lists.add(CacheKeys.ENTITY_STREAM_BY_SCOPE_ID + activity.getRecipientStreamScope().getId());

            // gets the author account ids for later use
            // Actors don't always have to be people, but we want the people here
            // so that their followers can be looked up and their following streams
            // can be updated.
            if (activity.getActorType() == EntityType.PERSON)
            {
                authorAccountIds.add(activity.getActorId());
            }
        }

        if (logger.isTraceEnabled())
        {
            logger.trace("Retrieve the CompositeStreamViews that contain the streamscopes: " + activities.size()
                    + " for the activities to be deleted.");
        }

        if (logger.isTraceEnabled())
        {
            logger.trace("Retrieve the follower ids of the authors: " + authorAccountIds.size()
                    + " of the activities to be deleted: ");
        }
        // following lists for everyone following the authors of the expired activities.
        List<Long> followerIds = getHibernateSession().createQuery(
                "select f.pk.followerId from Follower f where f.pk.followingId in "
                        + "(select id from Person p where p.accountId in (:authorIds))").setParameterList("authorIds",
                authorAccountIds).list();

        if (logger.isTraceEnabled())
        {
            logger.trace("Retrieved follower ids " + followerIds.size()
                    + " of the authors of the activities to be deleted: ");
        }
        for (long followerId : followerIds)
        {
            lists.add(CacheKeys.ACTIVITIES_BY_FOLLOWING + followerId);
        }

        lists.add(CacheKeys.EVERYONE_ACTIVITY_IDS);

        if (logger.isTraceEnabled())
        {
            logger.trace("Retrieve all lists containing the supplied activities count: " + lists.size() + " keys: "
                    + lists);
        }

        return new ArrayList(lists);
    }

}
