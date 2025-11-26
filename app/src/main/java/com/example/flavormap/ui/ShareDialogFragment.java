package com.example.flavormap.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.flavormap.R;

public class ShareDialogFragment extends DialogFragment {
    private static final String ARG_NAME = "name";
    private static final String ARG_LOCATION = "location";
    private static final String ARG_RATING = "rating";

    public static ShareDialogFragment newInstance(String name, String location, String rating) {
        ShareDialogFragment frag = new ShareDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_LOCATION, location);
        args.putString(ARG_RATING, rating);
        frag.setArguments(args);
        return frag;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.dialog_share, container, false);
        Button emailBtn   = view.findViewById(R.id.shareBtn1);        // Email
        Button facebookBtn = view.findViewById(R.id.shareBtn2);       // Facebook
        Button twitterBtn  = view.findViewById(R.id.shareBtn3);       // Twitter
        Button cancelBtn   = view.findViewById(R.id.cancelShareBtn4); // Cancel

        Bundle args = getArguments();
        String name = args != null ? args.getString(ARG_NAME, "") : "";
        String location = args != null ? args.getString(ARG_LOCATION, "") : "";
        String rating = args != null ? args.getString(ARG_RATING, "") : "";

        String shareText = "Check out this restaurant:\n"
                + name + "\n"
                + "Location: " + location + "\n"
                + "Rating: " + rating;

        // Email
        emailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this restaurant!");
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(intent, "Send email"));
        });

        // Generic SHARE intent for Facebook & Twitter (they appear in chooser if installed)
        View.OnClickListener socialShareListener = v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(intent, "Share via"));
        };

        facebookBtn.setOnClickListener(socialShareListener);
        twitterBtn.setOnClickListener(socialShareListener);

        // Cancel
        cancelBtn.setOnClickListener(v -> dismiss());

        return view;
    }
}
