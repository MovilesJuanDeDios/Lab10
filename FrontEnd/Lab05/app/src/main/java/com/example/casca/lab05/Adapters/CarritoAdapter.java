package com.example.casca.lab05.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.casca.lab05.R;

import java.util.List;

/**
 * Created by Giancarlo on 16/4/2018.
 */

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ProductViewHolder> {

    private Context ctx;
    private List<Product> productList;

    public CarritoAdapter(Context ctx, List<Product> productList) {
        this.ctx = ctx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.carrito_layout, null);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        Product product = productList.get(position);

        holder.title.setText(product.getTitle());
        holder.desc.setText(product.getShortdesc());
        holder.price.setText(String.valueOf(product.getPrice()));

        holder.imageView.setImageDrawable(ctx.getResources().getDrawable(product.getImage()));

        final Product prodData = productList.get(position);

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removeItem(prodData);
                ctx.startActivity(new Intent(ctx, Carrito.class));
                ((Activity)ctx).finish();
            }
        });


    }

    private void removeItem(Product p) {
        int position = productList.indexOf(p);
        productList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, desc, cantidad, price;
        ImageButton imgDel;

        public ProductViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textViewTitle);
            desc = itemView.findViewById(R.id.textViewShortDesc);
            cantidad = itemView.findViewById(R.id.textViewCantidad);
            price = itemView.findViewById(R.id.textViewPrice);
            imgDel = itemView.findViewById(R.id.imageButtonDelete);
        }
    }
}
