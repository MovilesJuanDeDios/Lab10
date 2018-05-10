package com.example.casca.lab05.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casca.lab05.Activity.Carrito;
import com.example.casca.lab05.Model.Product;
import com.example.casca.lab05.Model.Usuario;
import com.example.casca.lab05.R;
import com.example.casca.lab05.Utils.Data;

import java.util.List;

/**
 * Created by Giancarlo on 16/4/2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context ctx;
    private List<Product> productList;

    private SharedPreferences sharedPref;
    private String username;

    public ProductAdapter(Context ctx, List<Product> productList) {
        this.ctx = ctx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.list_layout, null);
        ProductViewHolder holder = new ProductViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        final Product product = productList.get(position);

        holder.title.setText(product.getTitle());
        holder.desc.setText(product.getShortdesc());
        holder.cantidad.setText(String.valueOf(product.getCantidad()));
        holder.price.setText(String.valueOf(product.getPrice()));

        holder.imageView.setImageDrawable(ctx.getResources().getDrawable(product.getImage()));

        holder.carrito.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                username = sharedPref.getString(ctx.getString(R.string.user_pref), ctx.getString(R.string.default_user));

                if (username.equals("")) {
                    Toast.makeText(ctx.getApplicationContext(), "Debe iniciar sesion primero", Toast.LENGTH_SHORT).show();
                } else {
                    getUsuario(username).getListaProductos().add(product);
                    Toast.makeText(ctx, "Añadido el producto al carrito", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, desc, cantidad, price;
        ImageButton carrito;

        public ProductViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textViewTitle);
            desc = itemView.findViewById(R.id.textViewShortDesc);
            cantidad = itemView.findViewById(R.id.textViewCantidad);
            price = itemView.findViewById(R.id.textViewPrice);
            carrito = itemView.findViewById(R.id.imageButtonCarrito);
        }
    }

    public Usuario getUsuario(String username) {
        Usuario aux = new Usuario();
        for (Usuario usr : Data.listaUsuarios) {
            if (usr.getUsername().equals(username))
                aux = usr;
        }
        return aux;
    }

}
