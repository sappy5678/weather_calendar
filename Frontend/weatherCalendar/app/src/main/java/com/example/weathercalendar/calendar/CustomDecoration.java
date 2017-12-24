package com.example.weathercalendar.calendar;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framgia.library.calendardayview.EventView;
import com.framgia.library.calendardayview.PopupView;
import com.framgia.library.calendardayview.data.IEvent;
import com.framgia.library.calendardayview.data.IPopup;
import com.framgia.library.calendardayview.decoration.CdvDecorationDefault;
import com.squareup.picasso.Picasso;

/**
 * Created by sappy5678 on 12/19/17.
 */

public class CustomDecoration extends CdvDecorationDefault {

    public CustomDecoration(Context context) {
        super(context);
    }

    @Override
    public EventView getEventView(final IEvent event, Rect eventBound, int hourHeight,
                                  int seperateHeight) {
        final EventView eventView =
                super.getEventView(event, eventBound, hourHeight, seperateHeight);

        // hide event name
        TextView textEventName = (TextView) eventView.findViewById(com.framgia.library.calendardayview.R.id.item_event_name);
        textEventName.setVisibility(View.INVISIBLE);

        // hide event header
        TextView textHeader1 = (TextView) eventView.findViewById(com.framgia.library.calendardayview.R.id.item_event_header_text1);
        TextView textHeader2 = (TextView) eventView.findViewById(com.framgia.library.calendardayview.R.id.item_event_header_text2);

        textHeader1.setVisibility(View.GONE);
        textHeader2.setVisibility(View.GONE);

        LinearLayout layout = (LinearLayout)eventView.findViewById(com.framgia.library.calendardayview.R.id.item_event_container);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
        params.width = 10;
        layout.setLayoutParams(params);


        return eventView;
    }

    @Override
    public PopupView getPopupView(final IPopup popup, Rect eventBound, int hourHeight,
                                  int seperateH) {
        PopupView popupView = super.getPopupView(popup, eventBound, hourHeight, seperateH);
        // popupView.show();
        CardView cardView = (CardView) popupView.findViewById(com.framgia.library.calendardayview.R.id.cardview);
        TextView textQuote = (TextView) popupView.findViewById(com.framgia.library.calendardayview.R.id.quote);
        TextView textTitle = (TextView) popupView.findViewById(com.framgia.library.calendardayview.R.id.title);
        ImageView imvEnd = (ImageView) popupView.findViewById(com.framgia.library.calendardayview.R.id.imv_end);
        ImageView imvStart = (ImageView) popupView.findViewById(com.framgia.library.calendardayview.R.id.image_start);

        textQuote.setVisibility(View.GONE);
        // do something with views
        Picasso.with(mContext)
                .load(popup.getImageStart())
                .into(imvStart);

        return popupView;
    }
}
