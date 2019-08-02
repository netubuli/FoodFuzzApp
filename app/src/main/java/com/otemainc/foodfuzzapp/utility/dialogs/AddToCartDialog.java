package com.otemainc.foodfuzzapp.utility.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.otemainc.foodfuzzapp.fragment.Cart;

import com.otemainc.foodfuzzapp.R;

public class AddToCartDialog extends AppCompatDialogFragment {
    Cart cart;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_cart_dialog,null);
        builder.setView(view)
        .setTitle("Cart")
                .setNegativeButton("ContinueShopping", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                })
                .setPositiveButton("CheckOut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        cart = new Cart();
                        gotToFragment(cart,false);
                    }
                });
        return builder.create();

    }
    public void gotToFragment(Fragment fragment, boolean isTop){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        if(!isTop)
            fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }
}
