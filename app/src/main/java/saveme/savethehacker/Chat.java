package saveme.savethehacker;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconTextView;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;


public class Chat extends FragmentActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {
    EmojiconEditText mEditEmojicon;
    EmojiconTextView mTxtEmojicon;
    View emoti_grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        emoti_grid = findViewById(R.id.emojicons);
        mEditEmojicon = (EmojiconEditText) findViewById(R.id.editEmojicon);
        mTxtEmojicon = (EmojiconTextView) findViewById(R.id.txtEmojicon);

        mEditEmojicon.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTxtEmojicon.setText(s);
                //Log.d("emo",s.toString());
            }
        });
        final int height = 440;
       findViewById(R.id.emoti_toggle).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(emoti_grid.getLayoutParams().height==height){
                   emoti_grid.getLayoutParams().height=0;
                   InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                   inputMethodManager.showSoftInput(mEditEmojicon, InputMethodManager.SHOW_IMPLICIT);
               }
               else {
                   emoti_grid.getLayoutParams().height=height;
                   InputMethodManager imm = (InputMethodManager)getSystemService(
                           Context.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(mEditEmojicon.getWindowToken(), 0);
               }
           }
       });
        mEditEmojicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoti_grid.getLayoutParams().height=0;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        Log.d("emo", emojicon.getEmoji()+"..."+emojicon.getIcon()+"..."+emojicon.getValue()+"..."+emojicon.toString());
        EmojiconsFragment.input(mEditEmojicon, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEditEmojicon);
    }
}
