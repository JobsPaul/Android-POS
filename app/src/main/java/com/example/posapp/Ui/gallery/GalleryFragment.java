package com.example.posapp.Ui.gallery;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.posapp.MainMenu;
import com.example.posapp.R;
import com.example.posapp.Ui.Login.Login;
import com.example.posapp.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
//        builder1.setMessage("คุณต้องการออกจากระบบหรือไม่ ?");
//        builder1.setCancelable(true);
//
//        builder1.setPositiveButton(
//                "ตกลง",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        startActivity(new Intent(getActivity(), Login.class));
//                        getActivity().finish();
//                        dialog.cancel();
//                    }
//                });
//
//        builder1.setNegativeButton(
//                "ยกเลิก",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alert11 = builder1.create();
//        alert11.show();

        showDialogLogout();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void showDialogLogout() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_alert_dialog);
        TextView tileDialog = dialog.findViewById(R.id.titleCustomDialog);
        TextView subTitleDialog = dialog.findViewById(R.id.subtitleDialog);
        Button submit = dialog.findViewById(R.id.submitBtn);
        Button cancel = dialog.findViewById(R.id.cancelBtn);
        ImageButton cancelIcon = dialog.findViewById(R.id.cancelCustomIconBtn);

        tileDialog.setText("  ออกจากระบบ");
        subTitleDialog.setText("   คุณต้องการออกจากระบบหรือไม่ ?");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainMenu.class));
                getActivity().finish();
                dialog.dismiss();
            }
        });

        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainMenu.class));
                getActivity().finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}