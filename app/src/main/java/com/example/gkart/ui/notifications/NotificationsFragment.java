package com.example.gkart.ui.notifications;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gkart.R;
import com.example.gkart.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private TextView username;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        username = getView().findViewById(R.id.username1);
        SharedPreferences switchState;
        switchState = this.getActivity().getSharedPreferences("userdetails",MODE_PRIVATE);
        String emailid = switchState.getString("emailid","default").split("@",2)[0];
        username.setText(emailid);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}