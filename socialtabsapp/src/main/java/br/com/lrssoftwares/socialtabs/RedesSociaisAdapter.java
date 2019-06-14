package br.com.lrssoftwares.socialtabs;

//region [ IMPORTS ]

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
//endregion

public class RedesSociaisAdapter extends RecyclerView.Adapter<RedesSociaisAdapter.ViewHolder> {

    //region [ VARIAVEIS ]
    private final ArrayList<ItemListaRedesSociaisClass> itemListaRedesSociaisClasses;

    private RecyclerViewButtonClickInterface recyclerViewButtonClickInterface;
    private RecyclerViewImageButtonClickInterface recyclerViewImageButtonClickInterface;
    //endregion

    RedesSociaisAdapter(ArrayList<ItemListaRedesSociaisClass> linhaItemRedesSociaisClass) {
        this.itemListaRedesSociaisClasses = linhaItemRedesSociaisClass;
    }

    //region [ EVENTOS ]
    @NonNull
    @Override
    public RedesSociaisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_configuracoes_item, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RedesSociaisAdapter.ViewHolder holder, int position) {
        final ItemListaRedesSociaisClass row_pos = itemListaRedesSociaisClasses.get(position);

        holder.txtIdLista.setText(Integer.toString(row_pos.getId()));

        holder.swtRedeSocial.setText(row_pos.getNome());
        if (row_pos.getAtivo() == 1)
            holder.swtRedeSocial.setChecked(true);
        else
            holder.swtRedeSocial.setChecked(false);

        holder.txtPosicaoTab.setText(Integer.toString(row_pos.getPosicaoTab()));
        holder.btnAlterarCorTab.setBackgroundColor(row_pos.getCorTab());
        holder.btnAlterarCorTab.setBackgroundTintList(ColorStateList.valueOf(row_pos.getCorTab()));

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return itemListaRedesSociaisClasses.size();
    }
    //endregion

    //region [ METODOS ]
    void setRecyclerViewButtonClickClass(RecyclerViewButtonClickInterface recyclerViewButtonClickInterface) {
        this.recyclerViewButtonClickInterface = recyclerViewButtonClickInterface;
    }

    void setRecyclerViewImageButtonClickClass(RecyclerViewImageButtonClickInterface recyclerViewImageButtonClickInterface) {
        this.recyclerViewImageButtonClickInterface = recyclerViewImageButtonClickInterface;
    }

    //endregion

    //region [ CLASSES ]
    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView txtIdLista;
        final Switch swtRedeSocial;
        final TextView txtPosicaoTab;
        final FloatingActionButton btnAlterarCorTab;
        final ImageButton btnExcluirRedeSocial;

        ViewHolder(View itemView) {
            super(itemView);

            txtIdLista = itemView.findViewById(R.id.txtIdLista);
            swtRedeSocial = itemView.findViewById(R.id.swtRedeSocial);
            txtPosicaoTab = itemView.findViewById(R.id.txtPosicaoTab);

            btnAlterarCorTab = itemView.findViewById(R.id.btnAlterarCorTab);
            btnAlterarCorTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewButtonClickInterface != null)
                        recyclerViewButtonClickInterface.onButtonClick(btnAlterarCorTab);
                }
            });

            btnExcluirRedeSocial = itemView.findViewById(R.id.btnExcluirRedeSocial);
            btnExcluirRedeSocial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewImageButtonClickInterface != null)
                        recyclerViewImageButtonClickInterface.onButtonClick(btnExcluirRedeSocial, Integer.valueOf(txtIdLista.getText().toString()), swtRedeSocial.getText().toString());
                }
            });
        }
    }
    //endregion
}