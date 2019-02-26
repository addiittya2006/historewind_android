package science.aditya.historewind.ui.cached;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import science.aditya.historewind.R;
import science.aditya.historewind.data.model.HistoryEvent;

public class PrevDigestAdapter extends RecyclerView.Adapter<PrevDigestAdapter.HistoryEventViewHolder> {

    private List<HistoryEvent> events;
    private Context context;

    static class  HistoryEventViewHolder extends RecyclerView.ViewHolder{

        TextView rvYearView;
        TextView rvDescView;
        TextView rvTypeView;
        ImageView rvImgView;

        HistoryEventViewHolder(View itemView){
            super(itemView);

            rvImgView = itemView.findViewById(R.id.rvImageView);
            rvTypeView = itemView.findViewById(R.id.rvTypeView);
            rvYearView = itemView.findViewById(R.id.rvYearView);
            rvDescView = itemView.findViewById(R.id.rvDescView);

        }

        void bind(Context c, HistoryEvent historyEvent) {

            rvYearView.setText(historyEvent.getYear());
            rvDescView.setText(Html.fromHtml(historyEvent.getDesc()).toString());

            Glide.with(c)
                    .load(historyEvent.getThumb())
                    .apply(RequestOptions.placeholderOf(R.drawable.wikimg))
                    .apply(RequestOptions.centerCropTransform())
                    .into(rvImgView);

            int type = historyEvent.getEventType();

            if (type == 1){
                rvTypeView.setBackgroundColor(Color.parseColor("#3E50B4"));
            } else if (type == 2) {
                rvTypeView.setBackgroundColor(Color.parseColor("#D0021B"));
            } else if (type == 3) {
                rvTypeView.setBackgroundColor(Color.parseColor("#757575"));
            }
        }
    }

    PrevDigestAdapter(Context c, List<HistoryEvent> events) {
        this.events = events;
        this.context = c;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public HistoryEventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_item_rv, viewGroup, false);
        return new HistoryEventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryEventViewHolder eventViewHolder, int position) {
        eventViewHolder.bind(context, events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

}
