package com.example.sstep.todo.notice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sstep.R;
import com.example.sstep.commute.Dispute_WriteStaff;
import com.example.sstep.commute.commute_api.CommuteApiService;
import com.example.sstep.commute.commute_api.CommuteResponseDto;
import com.example.sstep.store.store_api.NullOnEmptyConverterFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Notice_RecyclerViewAdpater extends RecyclerView.Adapter<Notice_RecyclerViewAdpater.Holder> {
    long noticeId;
    private Context context;
    private List<Notice_recyclerViewItem> list = new ArrayList<>();

    // 어댑터 생성자
    public Notice_RecyclerViewAdpater(Context context, List<Notice_recyclerViewItem> list) {
        this.context = context;
        this.list.addAll(list);
    }

    // 데이터 업데이트 메서드 추가
    public void updateData(List<Notice_recyclerViewItem> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    // ViewHolder 생성
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 아이템 뷰의 레이아웃을 인플레이션하여 Holder 객체 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_recycle_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    // 데이터를 아이템 뷰에 바인딩
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        noticeId = list.get(itemposition).noticeId;
        holder.titleTv.setText(list.get(itemposition).title);
        holder.dateTv.setText(list.get(itemposition).writeDate);
        holder.contentTv.setText(list.get(itemposition).contents);
        holder.nameTv.setText(list.get(itemposition).writerName);
        holder.hitsTv.setText("조회수 : " + String.valueOf(list.get(itemposition).hits));

        // 아이템 뷰 클릭 시 이벤트 처리
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Notice_view.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 오류 해결

                // 값 전달
                intent.putExtra("noticeId", noticeId);

                context.startActivity(intent);
            }
        });
    }

    // 데이터 리스트 크기 반환
    @Override
    public int getItemCount() {
        return list.size();
    }

    // 아이템 뷰의 구성요소를 보유하는 뷰 홀더 클래스
    public class Holder extends RecyclerView.ViewHolder{
        public TextView titleTv, contentTv, nameTv, dateTv, hitsTv;
        public LinearLayout layout, innerlayout;

        public Holder(View view){
            super(view);
            titleTv = (TextView) view.findViewById(R.id.notice_recycle_titleTv);
            contentTv = (TextView) view.findViewById(R.id.notice_recycle_contentTv);
            nameTv = (TextView) view.findViewById(R.id.notice_recycle_nameTv);
            dateTv = (TextView) view.findViewById(R.id.notice_recycle_dateTv);
            hitsTv = (TextView) view.findViewById(R.id.notice_recycle_hitsTv);
            layout = (LinearLayout) view.findViewById(R.id.notice_recycle_layout);
            innerlayout = (LinearLayout) view.findViewById(R.id.notice_recycle_innerlayout);
        }
    }

}