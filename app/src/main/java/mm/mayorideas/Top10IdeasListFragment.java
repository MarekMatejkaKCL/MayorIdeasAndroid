package mm.mayorideas;

import android.os.Bundle;

import mm.mayorideas.api.IdeaAPI;

public class Top10IdeasListFragment extends IdeaListFragment {

    public static Top10IdeasListFragment newInstance() {
        return new Top10IdeasListFragment();
    }

    @Override
    protected void handleArguments(Bundle arguments) {}

    @Override
    protected void getIdeasToDisplay(IdeaAPI.GetIdeasListener ideasListener) {
        IdeaAPI.getTop10Ideas(ideasListener);
    }

    @Override
    protected int getEmptyListText() {
        return R.string.empty_list_no_ideas;
    }
}
