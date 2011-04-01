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
package org.eurekastreams.web.client.ui.pages.master;

import org.eurekastreams.server.domain.Page;
import org.eurekastreams.web.client.history.CreateUrlRequest;
import org.eurekastreams.web.client.jsni.WidgetJSNIFacadeImpl;
import org.eurekastreams.web.client.ui.Session;
import org.eurekastreams.web.client.ui.pages.widget.CommentWidget;
import org.eurekastreams.web.client.ui.pages.widget.UserProfileBadgeWidget;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Widget;

/**
 * Creates a page given a page and view.
 */
public class ConnectPageFactory
{
    // /**
    // * Creates a page given a page and view.
    // *
    // * @param page
    // * the page.
    // * @param views
    // * the views.
    // * @return the page widget.
    // */
    // public Widget createPage(final Page page, final List<String> views)
    // {
    // RootPanel.get().setStyleName("");
    //
    // String view = "";
    // if (!views.isEmpty())
    // {
    // view = views.get(0);
    // }
    //
    // switch (page)
    // {
    // case WIDGET_COMMENT:
    // return new CommentWidget(view);
    // case WIDGET_LIKE_SHARE:
    // case WIDGET_PROFILE_BADGE:
    // case WIDGET_STREAM:
    // default:
    // return null;
    // }
    // }

    /**
     * Creates a widget and sets the history to match it.
     *
     * @param widgetName
     *            Name of widget desired.
     * @param util
     *            For getting URL parameters.
     * @return The widget (or null).
     */
    public Widget createPageWithHistory(final String widgetName, final WidgetJSNIFacadeImpl util)
    {
        if ("comment".equals(widgetName))
        {
            String resourceId = util.getParameter("resourceurl");
            setHistory(new CreateUrlRequest(Page.WIDGET_COMMENT, resourceId));
            return new CommentWidget(resourceId);
        }
        else if ("badge".equals(widgetName))
        {
            String accountId = util.getParameter("accountid");
            return new UserProfileBadgeWidget(accountId);
        }        
        else
        {
            return null;
        }
    }

    /**
     * Sets URL and triggers history.
     *
     * @param request
     *            Description of URL to build.
     */
    private void setHistory(final CreateUrlRequest request)
    {
        String token = Session.getInstance().generateUrl(request);
        History.newItem(token, true);
    }

}