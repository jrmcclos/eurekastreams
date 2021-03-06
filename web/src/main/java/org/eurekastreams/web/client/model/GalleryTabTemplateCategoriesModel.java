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
package org.eurekastreams.web.client.model;

import java.util.LinkedList;

import org.eurekastreams.server.service.actions.requests.EmptyRequest;
import org.eurekastreams.web.client.events.data.GotGalleryTabTemplateCategoriesResponseEvent;
import org.eurekastreams.web.client.ui.Session;

/**
 * Model for categories for GalleryTabTemplates.
 */
public class GalleryTabTemplateCategoriesModel extends BaseModel implements Fetchable<EmptyRequest>
{
    /**
     * Singleton.
     */
    private static GalleryTabTemplateCategoriesModel model = new GalleryTabTemplateCategoriesModel();

    /**
     * Gets the singleton.
     * 
     * @return the singleton.
     */
    public static GalleryTabTemplateCategoriesModel getInstance()
    {
        return model;
    }

    /**
     * {@inheritDoc}
     */
    public void fetch(final EmptyRequest request, final boolean useClientCacheIfAvailable)
    {
        super.callReadAction("getGalleryTabTemplateGalleryItemCategories", null,
                new OnSuccessCommand<LinkedList<String>>()
                {
                    public void onSuccess(final LinkedList<String> response)
                    {
                        Session.getInstance().getEventBus().notifyObservers(
                                new GotGalleryTabTemplateCategoriesResponseEvent(response));
                    }
                }, useClientCacheIfAvailable);
    }
}
