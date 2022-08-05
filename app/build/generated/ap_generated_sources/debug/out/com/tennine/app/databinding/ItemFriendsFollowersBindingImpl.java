package com.tennine.app.databinding;
import com.tennine.app.R;
import com.tennine.app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemFriendsFollowersBindingImpl extends ItemFriendsFollowersBinding implements com.tennine.app.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.btnRemoveFollowing, 5);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback10;
    @Nullable
    private final android.view.View.OnClickListener mCallback11;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemFriendsFollowersBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private ItemFriendsFollowersBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[2]
            , (android.widget.Button) bindings[5]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[3]
            );
        this.btnILike.setTag(null);
        this.ivProfile.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvNickNameFollowing.setTag(null);
        this.tvUsername.setTag(null);
        setRootTag(root);
        // listeners
        mCallback10 = new com.tennine.app.generated.callback.OnClickListener(this, 1);
        mCallback11 = new com.tennine.app.generated.callback.OnClickListener(this, 2);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.model == variableId) {
            setModel((com.tennine.app.model.FollowingFollowersModel) variable);
        }
        else if (BR.position == variableId) {
            setPosition((int) variable);
        }
        else if (BR.onCLickListener == variableId) {
            setOnCLickListener((com.tennine.app.listener.OnClickListener) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setModel(@Nullable com.tennine.app.model.FollowingFollowersModel Model) {
        this.mModel = Model;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.model);
        super.requestRebind();
    }
    public void setPosition(int Position) {
        this.mPosition = Position;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.position);
        super.requestRebind();
    }
    public void setOnCLickListener(@Nullable com.tennine.app.listener.OnClickListener OnCLickListener) {
        this.mOnCLickListener = OnCLickListener;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.onCLickListener);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.tennine.app.model.FollowingFollowersModel model = mModel;
        int position = mPosition;
        java.lang.String modelImage = null;
        java.lang.String modelUsername = null;
        java.lang.String modelNickname = null;
        java.lang.Boolean modelConnected = null;
        com.tennine.app.listener.OnClickListener onCLickListener = mOnCLickListener;

        if ((dirtyFlags & 0x9L) != 0) {



                if (model != null) {
                    // read model.image
                    modelImage = model.getImage();
                    // read model.username
                    modelUsername = model.getUsername();
                    // read model.nickname
                    modelNickname = model.getNickname();
                    // read model.connected
                    modelConnected = model.getConnected();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.btnILike.setOnClickListener(mCallback11);
            this.ivProfile.setOnClickListener(mCallback10);
        }
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 1

            com.tennine.app.adapter.BindingAdapters.setbtnILikeText(this.btnILike, modelConnected);
            com.tennine.app.adapter.BindingAdapters.setImageUrl(this.ivProfile, modelImage);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvNickNameFollowing, modelNickname);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvUsername, modelUsername);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 1: {
                // localize variables for thread safety
                // model
                com.tennine.app.model.FollowingFollowersModel model = mModel;
                // onCLickListener != null
                boolean onCLickListenerJavaLangObjectNull = false;
                // position
                int position = mPosition;
                // onCLickListener
                com.tennine.app.listener.OnClickListener onCLickListener = mOnCLickListener;



                onCLickListenerJavaLangObjectNull = (onCLickListener) != (null);
                if (onCLickListenerJavaLangObjectNull) {




                    onCLickListener.onUserViewCLick(model, position);
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // model
                com.tennine.app.model.FollowingFollowersModel model = mModel;
                // onCLickListener != null
                boolean onCLickListenerJavaLangObjectNull = false;
                // position
                int position = mPosition;
                // onCLickListener
                com.tennine.app.listener.OnClickListener onCLickListener = mOnCLickListener;



                onCLickListenerJavaLangObjectNull = (onCLickListener) != (null);
                if (onCLickListenerJavaLangObjectNull) {




                    onCLickListener.onILikeClick(model, position);
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): model
        flag 1 (0x2L): position
        flag 2 (0x3L): onCLickListener
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}