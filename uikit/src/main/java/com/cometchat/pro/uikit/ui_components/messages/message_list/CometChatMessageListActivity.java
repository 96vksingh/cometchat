package com.cometchat.pro.uikit.ui_components.messages.message_list;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.fragment.app.Fragment;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.models.BaseMessage;
import com.cometchat.pro.uikit.R;

import java.util.List;

import com.cometchat.pro.uikit.ui_resources.constants.UIKitConstants;
import com.cometchat.pro.uikit.ui_components.messages.message_actions.listener.MessageActionCloseListener;
import com.cometchat.pro.uikit.ui_components.messages.message_actions.listener.OnMessageLongClick;
import com.cometchat.pro.uikit.ui_settings.UISettings;

/**

* Purpose - CometChatMessageListActivity.class is a Activity used to display messages using CometChatMessageScreen.class. It takes
            parameter like TYPE to differentiate between User MessageScreen & Group MessageScreen.

            It passes parameters like UID (userID) ,AVATAR (userAvatar) ,NAME (userName) ,STATUS (userStatus) to CometChatMessageScreen.class
            if TYPE is CometChatConstant.RECEIVER_TYPE_USER

            It passes parameters like GUID (groupID) ,AVATAR (groupIcon) ,NAME (groupName) ,GROUP_OWNER (groupOwner) to CometChatMessageScreen.class
            if TYPE is CometChatConstant.RECEIVER_TYPE_GROUP

            @see com.cometchat.pro.constants.CometChatConstants
            @see CometChatMessageList


*/

public class CometChatMessageListActivity extends AppCompatActivity implements MessageAdapter.OnMessageLongClick {

    private static final String TAG = "CometChatMessageListAct";

    private OnMessageLongClick messageLongClick;

    Fragment fragment = new CometChatMessageList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cometchat_message_list);
        if (UISettings.getColor() !=null) {
            getWindow().setStatusBarColor(Color.parseColor(UISettings.getColor()));
        }

        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
        EmojiCompat.init(config);

         if (getIntent()!=null) {
             Bundle bundle = new Bundle();

             bundle.putString(UIKitConstants.IntentStrings.AVATAR, getIntent().getStringExtra(UIKitConstants.IntentStrings.AVATAR));
             bundle.putString(UIKitConstants.IntentStrings.NAME, getIntent().getStringExtra(UIKitConstants.IntentStrings.NAME));
             bundle.putString(UIKitConstants.IntentStrings.TYPE,getIntent().getStringExtra(UIKitConstants.IntentStrings.TYPE));

              if (getIntent().hasExtra(UIKitConstants.IntentStrings.TYPE)&&
                      getIntent().getStringExtra(UIKitConstants.IntentStrings.TYPE).equals(CometChatConstants.RECEIVER_TYPE_USER)) {

                  bundle.putString(UIKitConstants.IntentStrings.UID, getIntent().getStringExtra(UIKitConstants.IntentStrings.UID));
                  bundle.putString(UIKitConstants.IntentStrings.STATUS, getIntent().getStringExtra(UIKitConstants.IntentStrings.STATUS));

              }else {
                  bundle.putString(UIKitConstants.IntentStrings.GUID, getIntent().getStringExtra(UIKitConstants.IntentStrings.GUID));
                  bundle.putString(UIKitConstants.IntentStrings.GROUP_OWNER,getIntent().getStringExtra(UIKitConstants.IntentStrings.GROUP_OWNER));
                  bundle.putInt(UIKitConstants.IntentStrings.MEMBER_COUNT,getIntent().getIntExtra(UIKitConstants.IntentStrings.MEMBER_COUNT,0));
                  bundle.putString(UIKitConstants.IntentStrings.GROUP_TYPE,getIntent().getStringExtra(UIKitConstants.IntentStrings.GROUP_TYPE));
                  bundle.putString(UIKitConstants.IntentStrings.GROUP_DESC,getIntent().getStringExtra(UIKitConstants.IntentStrings.GROUP_DESC));
                  bundle.putString(UIKitConstants.IntentStrings.GROUP_PASSWORD,getIntent().getStringExtra(UIKitConstants.IntentStrings.GROUP_PASSWORD));
              }
              fragment.setArguments(bundle);
             getSupportFragmentManager().beginTransaction().replace(R.id.ChatFragment, fragment).commit();
         }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Log.d(TAG, "onActivityResult: ");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setLongMessageClick(List<BaseMessage> baseMessage) {
        if (fragment!=null)
        ((OnMessageLongClick)fragment).setLongMessageClick(baseMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void handleDialogClose(DialogInterface dialog) {
        ((MessageActionCloseListener)fragment).handleDialogClose(dialog);
        dialog.dismiss();
    }
}
