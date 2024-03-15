// Generated by view binder compiler. Do not edit!
package com.example.farmguardian.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.farmguardian.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AniamalCaretakerBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView Animalcaretakerbackhome;

  @NonNull
  public final ProgressBar Barprogress;

  @NonNull
  public final BottomNavigationView bottomNavigationView;

  @NonNull
  public final RelativeLayout containerLayout;

  @NonNull
  public final FrameLayout fragmentContainer;

  @NonNull
  public final ListView listViewAcaretakers;

  @NonNull
  public final TextView noInternet;

  private AniamalCaretakerBinding(@NonNull RelativeLayout rootView,
      @NonNull TextView Animalcaretakerbackhome, @NonNull ProgressBar Barprogress,
      @NonNull BottomNavigationView bottomNavigationView, @NonNull RelativeLayout containerLayout,
      @NonNull FrameLayout fragmentContainer, @NonNull ListView listViewAcaretakers,
      @NonNull TextView noInternet) {
    this.rootView = rootView;
    this.Animalcaretakerbackhome = Animalcaretakerbackhome;
    this.Barprogress = Barprogress;
    this.bottomNavigationView = bottomNavigationView;
    this.containerLayout = containerLayout;
    this.fragmentContainer = fragmentContainer;
    this.listViewAcaretakers = listViewAcaretakers;
    this.noInternet = noInternet;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AniamalCaretakerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AniamalCaretakerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.aniamal_caretaker, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AniamalCaretakerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Animalcaretakerbackhome;
      TextView Animalcaretakerbackhome = ViewBindings.findChildViewById(rootView, id);
      if (Animalcaretakerbackhome == null) {
        break missingId;
      }

      id = R.id.Barprogress;
      ProgressBar Barprogress = ViewBindings.findChildViewById(rootView, id);
      if (Barprogress == null) {
        break missingId;
      }

      id = R.id.bottom_navigation_view;
      BottomNavigationView bottomNavigationView = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigationView == null) {
        break missingId;
      }

      RelativeLayout containerLayout = (RelativeLayout) rootView;

      id = R.id.fragment_container;
      FrameLayout fragmentContainer = ViewBindings.findChildViewById(rootView, id);
      if (fragmentContainer == null) {
        break missingId;
      }

      id = R.id.listViewAcaretakers;
      ListView listViewAcaretakers = ViewBindings.findChildViewById(rootView, id);
      if (listViewAcaretakers == null) {
        break missingId;
      }

      id = R.id.noInternet;
      TextView noInternet = ViewBindings.findChildViewById(rootView, id);
      if (noInternet == null) {
        break missingId;
      }

      return new AniamalCaretakerBinding((RelativeLayout) rootView, Animalcaretakerbackhome,
          Barprogress, bottomNavigationView, containerLayout, fragmentContainer,
          listViewAcaretakers, noInternet);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
