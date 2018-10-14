package science.aditya.historewind.ui.cached;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import science.aditya.historewind.R;
import science.aditya.historewind.data.model.HistoryEvent;

public class PrevDigestAdapter extends RecyclerView.Adapter<PrevDigestAdapter.HistoryEventViewHolder> {

    private List<HistoryEvent> events;

    public static class  HistoryEventViewHolder extends RecyclerView.ViewHolder{

        TextView rvYearView;
        TextView rvDescView;
        TextView rvTypeView;

        HistoryEventViewHolder(View itemView){
            super(itemView);

            rvYearView = (TextView) itemView.findViewById(R.id.rvYearView);
            rvDescView = (TextView) itemView.findViewById(R.id.rvDescView);

        }

        public void bind(HistoryEvent historyEvent) {
            rvYearView.setText(historyEvent.getYear());
            rvDescView.setText(historyEvent.getDesc());
        }

    }

    PrevDigestAdapter(List<HistoryEvent> events) {
        this.events = events;
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
        eventViewHolder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


}
