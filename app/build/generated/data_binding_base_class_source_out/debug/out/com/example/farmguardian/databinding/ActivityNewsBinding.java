// Generated by view binder compiler. Do not edit!
package com.example.farmguardian.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.farmguardian.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityNewsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView backNews;

  @NonNull
  public final Button btnAgric;

  @NonNull
  public final Button btnHealth;

  @NonNull
  public final Button btnScince;

  @NonNull
  public final Button btnTech;

  @NonNull
  public final Button btngeneral;

  @NonNull
  public final Button business;

  @NonNull
  public final ProgressBar load;

  @NonNull
  public final RecyclerView recyclerNews;

  @NonNull
  public final SearchView serachbar;

  @NonNull
  public final TextView textViewFG;

  private ActivityNewsBinding(@NonNull LinearLayout rootView, @NonNull ImageView backNews,
      @NonNull Button btnAgric, @NonNull Button btnHealth, @NonNull Button btnScince,
      @NonNull Button btnTech, @NonNull Button btngeneral, @NonNull Button business,
      @NonNull ProgressBar load, @NonNull RecyclerView recyclerNews, @NonNull SearchView serachbar,
      @NonNull TextView textViewFG) {
    this.rootView = rootView;
    this.backNews = backNews;
    this.btnAgric = btnAgric;
    this.btnHealth = btnHealth;
    this.btnScince = btnScince;
    this.btnTech = btnTech;
    this.btngeneral = btngeneral;
    this.business = business;
    this.load = load;
    this.recyclerNews = recyclerNews;
    this.serachbar = serachbar;
    this.textViewFG = textViewFG;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityNewsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityNewsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_news, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityNewsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.backNews;
      ImageView backNews = ViewBindings.findChildViewById(rootView, id);
      if (backNews == null) {
        break missingId;
      }

      id = R.id.btnAgric;
      Button btnAgric = ViewBindings.findChildViewById(rootView, id);
      if (btnAgric == null) {
        break missingId;
      }

      id = R.id.btnHealth;
      Button btnHealth = ViewBindings.findChildViewById(rootView, id);
      if (btnHealth == null) {
        break missingId;
      }

      id = R.id.btnScince;
      Button btnScince = ViewBindings.findChildViewById(rootView, id);
      if (btnScince == null) {
        break missingId;
      }

      id = R.id.btnTech;
      Button btnTech = ViewBindings.findChildViewById(rootView, id);
      if (btnTech == null) {
        break missingId;
      }

      id = R.id.btngeneral;
      Button btngeneral = ViewBindings.findChildViewById(rootView, id);
      if (btngeneral == null) {
        break missingId;
      }

      id = R.id.business;
      Button business = ViewBindings.findChildViewById(rootView, id);
      if (business == null) {
        break missingId;
      }

      id = R.id.load;
      ProgressBar load = ViewBindings.findChildViewById(rootView, id);
      if (load == null) {
        break missingId;
      }

      id = R.id.recycler_news;
      RecyclerView recyclerNews = ViewBindings.findChildViewById(rootView, id);
      if (recyclerNews == null) {
        break missingId;
      }

      id = R.id.serachbar;
      SearchView serachbar = ViewBindings.findChildViewById(rootView, id);
      if (serachbar == null) {
        break missingId;
      }

      id = R.id.textViewFG;
      TextView textViewFG = ViewBindings.findChildViewById(rootView, id);
      if (textViewFG == null) {
        break missingId;
      }

      return new ActivityNewsBinding((LinearLayout) rootView, backNews, btnAgric, btnHealth,
          btnScince, btnTech, btngeneral, business, load, recyclerNews, serachbar, textViewFG);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
