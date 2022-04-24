package com.example.posapp.Ui.home;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.posapp.R;
import com.example.posapp.Ui.order.OrderMenu;
import com.example.posapp.Ui.product.ProductMenu;
import com.example.posapp.Ui.sale.SaleMenu;
import com.example.posapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private GridLayout gridLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        gridLayout = (GridLayout) root.findViewById(R.id.menuGridLayout);

        setSingleEvent(gridLayout);

//        Button buttonSayHi = (Button) root.findViewById(R.id.saleBtn);
//        Button buttonSayHi2 = (Button) root.findViewById(R.id.billBtn);
//        Button buttonSatHi3 = (Button) root.findViewById(R.id.editProductBtn);
//        buttonSayHi.setOnClickListener(this);
//        buttonSayHi2.setOnClickListener(this);
//        buttonSatHi3.setOnClickListener(this);

//        ((AppCompatActivity)getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void setSingleEvent(GridLayout singleEvent) {
        for (int i = 0 ; i < gridLayout.getChildCount(); i++) {
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finals = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finals == 0) {
                        Intent intent = new Intent(getActivity(), SaleMenu.class);
                        intent.putExtra("key", true);
                        startActivity(intent);
                    }
                    else if (finals == 1) {
                        Intent intent2 = new Intent(getActivity(), OrderMenu.class);
                        startActivity(intent2);
                    }
                    else if (finals == 2) {
                        Intent intent3 = new Intent(getActivity(), ProductMenu.class);
                        startActivity(intent3);
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
//            case R.id.saleBtn:
//                Intent intent = new Intent(getActivity(), SaleMenu.class);
//                intent.putExtra("key", true);
//                startActivity(intent);
//                break;
//            case R.id.billBtn:
//                Intent intent2 = new Intent(getActivity(), OrderMenu.class);
//                startActivity(intent2);
//                break;
//            case  R.id.editProductBtn:
//                Intent intent3 = new Intent(getActivity(), ProductMenu.class);
//                startActivity(intent3);
//                break;
        }
    }
}