package science.aditya.historewind.ui.cached;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.List;

import science.aditya.historewind.R;
import science.aditya.historewind.data.model.HistoryEvent;

public class PrevDigestAdapter extends RecyclerView.Adapter<PrevDigestAdapter.HistoryEventViewHolder> {

    private List<HistoryEvent> events;
    private Context context;

    public static class  HistoryEventViewHolder extends RecyclerView.ViewHolder{

        TextView rvYearView;
        TextView rvDescView;
        TextView rvTypeView;
        ImageView rvImgView;

        HistoryEventViewHolder(View itemView){
            super(itemView);

            rvImgView = (ImageView) itemView.findViewById(R.id.rvImageView);
            rvTypeView = (TextView) itemView.findViewById(R.id.rvTypeView);
            rvYearView = (TextView) itemView.findViewById(R.id.rvYearView);
            rvDescView = (TextView) itemView.findViewById(R.id.rvDescView);

        }

        public void bind(Context c, HistoryEvent historyEvent) {

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
        HistoryEventViewHolder vh = new HistoryEventViewHolder(v);
        return vh;
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
