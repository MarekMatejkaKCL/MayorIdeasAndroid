package mm.mayorideas.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import mm.mayorideas.gson.NewCommentPOSTGson;
import mm.mayorideas.objects.Comment;

public class CommentAPI {

    private static String COMMENT = "comment/";

    public static void addComment(
            int userID,
            int ideaID,
            String commentText) {
        String url = ServerAPIHelper.getServer() + COMMENT + "add";

        NewCommentPOSTGson comment = new NewCommentPOSTGson(userID, ideaID, commentText);
        StringEntity entity = null;
        try {
            Gson gson = new Gson();
            entity = new StringEntity(gson.toJson(comment));
        }catch (UnsupportedEncodingException e) {e.printStackTrace();}

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(null, url, entity, "application/json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("error", "" + responseString);
//                if (listener != null)
//                    listener.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                if (listener != null && !responseString.equals("-1"))
//                    listener.onSuccess(Integer.parseInt(responseString));
            }
        });
    }

    public interface GetCommentsForIdeaListener {
        void onSuccess(List<Comment> comments);
        void onFailure();
    }

    public static void getAllCommentsForIdea(
            int ideaID,
            final GetCommentsForIdeaListener listener) {
        String url = ServerAPIHelper.getServer()+COMMENT+"/idea/"+ideaID;


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                Log.e("all comments", response);
                if (response != null && response.length() > 0 && !response.equals("null")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Comment>>() {}.getType();
                    List<Comment> comments = gson.fromJson(response, type);

                    if(listener != null) {
                        listener.onSuccess(comments);
                    }
                } else {
                    if (listener != null) {
                        listener.onFailure();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
    }

    public static void getLast2CommentsForIdea(
            int ideaID,
            final GetCommentsForIdeaListener listener) {
        String url = ServerAPIHelper.getServer()+COMMENT+"/idea/last/"+ideaID;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                Log.e("last 2 comments", response);
                if (response != null && response.length() > 0 && !response.equals("null")) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Comment>>() {}.getType();
                    List<Comment> comments = gson.fromJson(response, type);

                    if(listener != null) {
                        listener.onSuccess(comments);
                    }
                } else {
                    if (listener != null) {
                        listener.onFailure();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
    }
}