/*
 * Copyright (c) 2009-2011 Lockheed Martin Corporation
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
package org.eurekastreams.web.client.ui.common.widgets.activity;

import java.util.HashSet;

import org.eurekastreams.server.action.request.stream.PostActivityRequest;
import org.eurekastreams.server.domain.DomainConversionUtility;
import org.eurekastreams.server.domain.EntityType;
import org.eurekastreams.server.domain.stream.ActivityDTO;
import org.eurekastreams.server.domain.stream.StreamScope;
import org.eurekastreams.server.domain.stream.StreamScope.ScopeType;
import org.eurekastreams.web.client.events.EventBus;
import org.eurekastreams.web.client.events.MessageAttachmentChangedEvent;
import org.eurekastreams.web.client.events.MessageStreamAppendEvent;
import org.eurekastreams.web.client.events.Observer;
import org.eurekastreams.web.client.events.data.GotAllPopularHashTagsResponseEvent;
import org.eurekastreams.web.client.events.data.GotSystemSettingsResponseEvent;
import org.eurekastreams.web.client.events.data.PostableStreamScopeChangeEvent;
import org.eurekastreams.web.client.model.ActivityModel;
import org.eurekastreams.web.client.model.AllPopularHashTagsModel;
import org.eurekastreams.web.client.ui.Session;
import org.eurekastreams.web.client.ui.TimerFactory;
import org.eurekastreams.web.client.ui.TimerHandler;
import org.eurekastreams.web.client.ui.common.autocomplete.ExtendedTextArea;
import org.eurekastreams.web.client.ui.common.avatar.AvatarWidget.Size;
import org.eurekastreams.web.client.ui.common.stream.attach.Attachment;
import org.eurekastreams.web.client.ui.common.stream.attach.bookmark.AddLinkComposite;
import org.eurekastreams.web.client.ui.common.stream.decorators.ActivityDTOPopulator;
import org.eurekastreams.web.client.ui.common.stream.decorators.ActivityDTOPopulatorStrategy;
import org.eurekastreams.web.client.ui.common.stream.decorators.object.NotePopulator;
import org.eurekastreams.web.client.ui.common.stream.decorators.verb.PostPopulator;
import org.eurekastreams.web.client.ui.common.stream.renderers.AvatarRenderer;
import org.eurekastreams.web.client.ui.pages.master.StaticResourceBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Post box.
 */
public class PostBoxComposite extends Composite
{
    /**
     * Binder for building UI.
     */
    private static LocalUiBinder binder = GWT.create(LocalUiBinder.class);

    /**
     * 
     * Binder for building UI.
     */
    interface LocalUiBinder extends UiBinder<Widget, PostBoxComposite>
    {
    }

    /**
     * Post box CssResource style.
     */
    interface PostBoxStyle extends CssResource
    {
        /**
         * Visible post box style.
         * 
         * @return Visible post box style.
         */
        String visiblePostBox();

        /**
         * Post char count over limit.
         * 
         * @return Post char count over limit.
         */
        String postCharCountOverLimit();

        /**
         * Post button inactive.
         * 
         * @return post button inactive.
         */
        String postButtonInactive();

        /**
         * Active hashtag style.
         * 
         * @return Active hashtag style.
         */
        String activeHashTag();
    }

    /**
     * Post box CssResource style.
     */
    @UiField
    PostBoxStyle style;

    /**
     * UI element for poster avatar.
     */
    @UiField
    HTMLPanel posterAvatar;

    /**
     * UI element for post panel.
     */
    @UiField
    HTMLPanel postPanel;

    /**
     * UI element for post box.
     */
    @UiField
    ExtendedTextArea postBox;

    /**
     * UI element for post options.
     */
    @UiField
    DivElement postOptions;

    /**
     * UI element for post button.
     */
    @UiField
    Label postButton;

    /**
     * UI element for post char count.
     */
    @UiField
    DivElement postCharCount;

    /**
     * UI element for add link composite.
     */
    @UiField
    AddLinkComposite addLinkComposite;

    /**
     * Hash Tags.
     */
    @UiField
    FlowPanel hashTags;

    /**
     * The content warning.
     */
    @UiField
    Label contentWarning;

    /**
     * Hide delay after blur on post box.
     */
    private static final Integer BLUR_DELAY = 500;

    /**
     * Max chars for post.
     */
    private static final Integer POST_MAX = 250;

    /**
     * Post box default height.
     */
    private static final int POST_BOX_DEFAULT_HEIGHT = 250;

    /**
     * Currently active item.
     */
    private Label activeItem = null;

    /**
     * Timer factory.
     */
    private TimerFactory timerFactory = new TimerFactory();

    /**
     * Current stream to post to.
     */
    private StreamScope currentStream = new StreamScope(ScopeType.PERSON, Session.getInstance().getCurrentPerson()
            .getAccountId());

    /**
     * Avatar Renderer.
     */
    private AvatarRenderer avatarRenderer = new AvatarRenderer();

    /**
     * Activity Populator.
     */
    private final ActivityDTOPopulator activityPopulator = new ActivityDTOPopulator();

    /**
     * Attachment.
     */
    private Attachment attachment = null;

    /**
     * All hash tags.
     */
    private HashSet<String> allHashTags;

    /**
     * Default constructor.
     */
    public PostBoxComposite()
    {
        initWidget(binder.createAndBindUi(this));
        buildPage();
    }

    /**
     * Build page.
     */
    private void buildPage()
    {
        posterAvatar.add(avatarRenderer.render(Session.getInstance().getCurrentPerson().getEntityId(), Session
                .getInstance().getCurrentPerson().getAvatarId(), EntityType.PERSON, Size.Small));
        postCharCount.setInnerText(POST_MAX.toString());
        checkPostBox();
        postBox.setLabel("Post to your stream...");
        postBox.reset();

        addEvents();

        postBox.addKeyUpHandler(new KeyUpHandler()
        {
            public void onKeyUp(final KeyUpEvent event)
            {
                checkPostBox();
            }
        });

        postBox.addChangeHandler(new ChangeHandler()
        {
            public void onChange(final ChangeEvent event)
            {
                checkPostBox();
            }
        });

        postBox.addFocusHandler(new FocusHandler()
        {
            public void onFocus(final FocusEvent event)
            {
                postOptions.addClassName(style.visiblePostBox());
            }
        });

        postBox.addBlurHandler(new BlurHandler()
        {
            public void onBlur(final BlurEvent event)
            {

                timerFactory.runTimer(BLUR_DELAY, new TimerHandler()
                {
                    public void run()
                    {
                        if (postBox.getText().trim().length() == 0 && !addLinkComposite.inAddMode()
                                && attachment == null)
                        {
                            postOptions.removeClassName(style.visiblePostBox());
                            postBox.getElement().getStyle().clearHeight();
                            postBox.reset();
                        }
                    }
                });
            }
        });

        postButton.addClickHandler(new ClickHandler()
        {
            public void onClick(final ClickEvent event)
            {
                if (!postButton.getStyleName().contains(style.postButtonInactive()))
                {
                    ActivityDTOPopulatorStrategy objectStrat = attachment != null ? attachment.getPopulator()
                            : new NotePopulator();

                    ActivityDTO activity = activityPopulator.getActivityDTO(postBox.getText(),
                            DomainConversionUtility.convertToEntityType(currentStream.getScopeType()),
                            currentStream.getUniqueKey(), new PostPopulator(), objectStrat);
                    PostActivityRequest postRequest = new PostActivityRequest(activity);

                    ActivityModel.getInstance().insert(postRequest);
                }
            }
        });

        postBox.addKeyDownHandler(new KeyDownHandler()
        {
            public void onKeyDown(final KeyDownEvent event)
            {

                if (event.getNativeKeyCode() == KeyCodes.KEY_TAB && !event.isAnyModifierKeyDown()
                        && activeItem != null)
                {
                    activeItem.getElement().dispatchEvent(
                            Document.get().createClickEvent(1, 0, 0, 0, 0, false, false, false, false));
                    event.preventDefault();
                    event.stopPropagation();
                    // clearSearch();

                }
                else if (event.getNativeKeyCode() == KeyCodes.KEY_DOWN && activeItem != null)
                {
                    int activeIndex = hashTags.getWidgetIndex(activeItem);

                    if (activeIndex + 1 < hashTags.getWidgetCount())
                    {
                        selectItem((Label) hashTags.getWidget(activeIndex + 1));
                    }
                    event.preventDefault();
                    event.stopPropagation();
                }
                else if (event.getNativeKeyCode() == KeyCodes.KEY_UP && activeItem != null)
                {
                    int activeIndex = hashTags.getWidgetIndex(activeItem);

                    if (activeIndex - 1 >= 0)
                    {
                        selectItem((Label) hashTags.getWidget(activeIndex - 1));
                    }
                    event.preventDefault();
                    event.stopPropagation();
                }
            }
        });

        postBox.addKeyUpHandler(new KeyUpHandler()
        {
            public void onKeyUp(final KeyUpEvent event)
            {
                hashTags.getElement().getStyle().setWidth(postBox.getElement().getClientWidth() + 8, Unit.PX);
                hashTags.clear();
                hashTags.setVisible(false);
                String[] words = postBox.getText().split("\\s");

                if (words.length >= 1 && !postBox.getText().endsWith(" "))
                {
                    final String lastWord = words[words.length - 1];
                    if (lastWord.startsWith("#"))
                    {
                        activeItem = null;

                        for (final String tag : allHashTags)
                        {
                            if (hashTags.getWidgetCount() > 9)
                            {
                                break;
                            }
                            else
                            {
                                if (tag.startsWith(lastWord))
                                {
                                    hashTags.setVisible(true);
                                    final Label tagLbl = new Label(tag);
                                    hashTags.add(tagLbl);
                                    tagLbl.addClickHandler(new ClickHandler()
                                    {
                                        public void onClick(final ClickEvent event)
                                        {
                                            String postText = postBox.getText();
                                            postText = postText.substring(0, postText.length() - lastWord.length())
                                                    + tag + " ";
                                            postBox.setText(postText);
                                            hashTags.clear();
                                            hashTags.setVisible(false);
                                        }
                                    });

                                    tagLbl.addMouseOverHandler(new MouseOverHandler()
                                    {
                                        public void onMouseOver(final MouseOverEvent arg0)
                                        {
                                            selectItem(tagLbl);
                                        }
                                    });

                                    if (activeItem == null)
                                    {
                                        selectItem(tagLbl);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        AllPopularHashTagsModel.getInstance().fetch(null, true);
        hashTags.setVisible(false);
    }

    /**
     * Add events.
     */
    private void addEvents()
    {
        EventBus.getInstance().addObserver(MessageStreamAppendEvent.class, new Observer<MessageStreamAppendEvent>()
        {
            public void update(final MessageStreamAppendEvent event)
            {
                attachment = null;
                addLinkComposite.close();
                postBox.setText("");
                postBox.reset();
                postBox.getElement().getStyle().clearHeight();
                postOptions.removeClassName(style.visiblePostBox());
                checkPostBox();
            }
        });

        EventBus.getInstance().addObserver(PostableStreamScopeChangeEvent.class,
                new Observer<PostableStreamScopeChangeEvent>()
                {
                    public void update(final PostableStreamScopeChangeEvent stream)
                    {
                        currentStream = stream.getResponse();
                        if (currentStream != null && !"".equals(currentStream.getDisplayName()))
                        {
                            if (currentStream.getScopeType().equals(ScopeType.PERSON))
                            {
                                if (currentStream.getDisplayName().endsWith("s"))
                                {
                                    postBox.setLabel("Post to " + currentStream.getDisplayName() + "' stream...");
                                }
                                else
                                {
                                    postBox.setLabel("Post to " + currentStream.getDisplayName() + "'s stream...");
                                }
                            }
                            else
                            {
                                postBox.setLabel("Post to the " + currentStream.getDisplayName() + " stream...");
                            }
                        }
                        else
                        {
                            postBox.setLabel("Post to your stream...");
                        }

                        postPanel.setVisible(stream.getResponse().getScopeType() != null);
                    }
                });

        EventBus.getInstance().addObserver(MessageAttachmentChangedEvent.class,
                new Observer<MessageAttachmentChangedEvent>()
                {
                    public void update(final MessageAttachmentChangedEvent evt)
                    {
                        attachment = evt.getAttachment();
                    }
                });

        EventBus.getInstance().addObserver(GotSystemSettingsResponseEvent.class,
                new Observer<GotSystemSettingsResponseEvent>()
                {
                    public void update(final GotSystemSettingsResponseEvent event)
                    {
                        String warning = event.getResponse().getContentWarningText();
                        if (warning != null && !warning.isEmpty())
                        {
                            contentWarning.setText(warning);
                        }
                        else
                        {
                            contentWarning.setVisible(false);
                        }
                    }
                });

        Session.getInstance()
                .getEventBus()
                .addObserver(GotAllPopularHashTagsResponseEvent.class,
                        new Observer<GotAllPopularHashTagsResponseEvent>()
                        {
                            public void update(final GotAllPopularHashTagsResponseEvent event)
                            {
                                allHashTags = event.getResponse();
                            }
                        });

    }

    /**
     * Check the post box.
     */
    private void checkPostBox()
    {
        if (postBox.getElement().getClientHeight() < postBox.getElement().getScrollHeight())
        {
            postBox.getElement().getStyle().setHeight(postBox.getElement().getScrollHeight(), Unit.PX);
        }

        postCharCount.setInnerText(Integer.toString(POST_MAX - postBox.getText().length()));

        if (POST_MAX - postBox.getText().length() < 0)
        {
            postCharCount.addClassName(style.postCharCountOverLimit());
        }
        else
        {
            postCharCount.removeClassName(style.postCharCountOverLimit());
        }

        if ((postBox.getText().length() > 0 && POST_MAX - postBox.getText().length() >= 0) || attachment != null)
        {
            postButton.removeStyleName(style.postButtonInactive());
        }
        else
        {
            postButton.addStyleName(style.postButtonInactive());
        }
    }

    /**
     * Select an item.
     * 
     * @param item
     *            the item.
     */
    private void selectItem(final Label item)
    {
        if (activeItem != null)
        {
            activeItem.removeStyleName(StaticResourceBundle.INSTANCE.coreCss().active());
        }
        item.addStyleName(style.activeHashTag());
        activeItem = item;
    }

}
