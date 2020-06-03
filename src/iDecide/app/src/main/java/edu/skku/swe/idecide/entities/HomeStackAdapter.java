package edu.skku.swe.idecide.entities;

import com.loopeer.cardstack.StackAdapter;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.loopeer.cardstack.CardStackView;

import java.util.ArrayList;

import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.entities.Item;

public class HomeStackAdapter extends StackAdapter<Integer> {
    //변수설정
    ArrayList<Item> items;
    RecyclerView recycler;

    //constructor
    public HomeStackAdapter(FragmentActivity activity) {
        super(activity);
    }
    public HomeStackAdapter(FragmentActivity activity, ArrayList<Item> list) {
        super(activity);
        this.items = list;
    }

    //override
    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
//        switch (viewType) {
//            case R.layout.list_card_item_larger_header:
//                view = getLayoutInflater().inflate(R.layout.list_card_item_larger_header, parent, false);
//                return new ColorItemLargeHeaderViewHolder(view);
//            case R.layout.list_card_item_with_no_header:
//                view = getLayoutInflater().inflate(R.layout.list_card_item_with_no_header, parent, false);
//                return new ColorItemWithNoHeaderViewHolder(view);
//            default:
//                view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
//                return new ColorItemViewHolder(view);
//    }
        view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
        return new ColorItemViewHolder(view);
    }
    @Override
    public int getItemViewType(int position) {
        //ItemViewType - 헤더 크기 조절 가능함.
//        if (position == 6) {//TODO TEST LARGER ITEM
//            return R.layout.list_card_item_larger_header;
//        } else if (position == 10) {
//            return R.layout.list_card_item_with_no_header;
//        }else {
//            return R.layout.list_card_item;
//        }
        return R.layout.list_card_item;
    }
    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
//        if (holder instanceof ColorItemLargeHeaderViewHolder) {
//            ColorItemLargeHeaderViewHolder h = (ColorItemLargeHeaderViewHolder) holder;
//            h.onBind(data, position);
//        }
//        if (holder instanceof ColorItemWithNoHeaderViewHolder) {
//            ColorItemWithNoHeaderViewHolder h = (ColorItemWithNoHeaderViewHolder) holder;
//            h.onBind(data, position);
//        }
        if (holder instanceof ColorItemViewHolder) {
            ColorItemViewHolder h = (ColorItemViewHolder) holder;
            h.onBind(data, position);
        }
    }


    static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;

        RecyclerView mHomeRecycler;

        public ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);

        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);//true 면 visible, 아니면 gone
        }

        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(String.valueOf(position));

        }

    }









//    static class ColorItemWithNoHeaderViewHolder extends CardStackView.ViewHolder {
//        View mLayout;
//        TextView mTextTitle;
//
//        public ColorItemWithNoHeaderViewHolder(View view) {
//            super(view);
//            mLayout = view.findViewById(R.id.frame_list_card_item);
//            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
//        }
//
//        @Override
//        public void onItemExpand(boolean b) {
//        }
//
//        public void onBind(Integer data, int position) {
//            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
//            mTextTitle.setText(String.valueOf(position));
//
//        }
//
//    }

//    static class ColorItemLargeHeaderViewHolder extends CardStackView.ViewHolder {
//        View mLayout;
//        View mContainerContent;
//        TextView mTextTitle;
//
//        public ColorItemLargeHeaderViewHolder(View view) {
//            super(view);
//            mLayout = view.findViewById(R.id.frame_list_card_item);
//            mContainerContent = view.findViewById(R.id.container_list_content);
//            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
//        }
//
//        @Override
//        public void onItemExpand(boolean b) {
//            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
//        }
//
//        @Override
//        protected void onAnimationStateChange(int state, boolean willBeSelect) {
//            super.onAnimationStateChange(state, willBeSelect);
//            if (state == CardStackView.ANIMATION_STATE_START && willBeSelect) {
//                onItemExpand(true);
//            }
//            if (state == CardStackView.ANIMATION_STATE_END && !willBeSelect) {
//                onItemExpand(false);
//            }
//        }

//        public void onBind(Integer data, int position) {
//            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
//            mTextTitle.setText(String.valueOf(position));
//
//            itemView.findViewById(R.id.text_view).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ((CardStackView)itemView.getParent()).performItemClick(ColorItemLargeHeaderViewHolder.this);
//                }
//            });
}


