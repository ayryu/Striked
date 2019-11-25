package com.example.alexr.todolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CompanyListAdapter extends RecyclerView.Adapter <CompanyListAdapter.CompanyViewHolder> {

        class CompanyViewHolder extends RecyclerView.ViewHolder {
            private final TextView companyItemView;

            private CompanyViewHolder(View itemView) {
                super(itemView);
                companyItemView = itemView.findViewById(R.id.textView);
            }
        }
        //declare interface
        private OnCompanyClicked onClick;

        private final LayoutInflater mInflater;
        private List<Company> companies; // Cached copy of words

        CompanyListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
            return new CompanyViewHolder(itemView);
        }

        public void setOnClick(OnCompanyClicked onClick) {
            this.onClick=onClick;
        }

        @Override
        public void onBindViewHolder(CompanyViewHolder holder, final int position) {
            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Company current = companies.get(position);
                    onClick.onCompanyClick(current);
                }
            };

            holder.companyItemView.setOnClickListener(clickListener);
            if (companies != null) {
                Company current = companies.get(position);
                holder.companyItemView.setText(current.getName());
            } else {
                // Covers the case of data not being ready yet.
                holder.companyItemView.setText("No Company");
            }
        }

        void setCompanies(List<Company> companies){
            this.companies = companies;
            notifyDataSetChanged();
        }

        // getItemCount() is called many times, and when it is first called,
        // mWords has not been updated (means initially, it's null, and we can't return null).
        @Override
        public int getItemCount() {
            if (companies != null)
                return companies.size();
            else return 0;
        }
}
