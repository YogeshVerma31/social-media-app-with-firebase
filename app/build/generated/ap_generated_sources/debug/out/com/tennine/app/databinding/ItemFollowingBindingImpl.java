package com.tennine.app.databinding;
import com.tennine.app.R;
import com.tennine.app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemFollowingBindingImpl extends ItemFollowingBinding implements com.tennine.app.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback12;
    @Nullable
    private final android.view.View.OnClickListener mCallback13;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemFollowingBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private ItemFollowingBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[4]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[2]
            );
        this.btnRemoveFollowing.setTag(null);
        this.ivProfile.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvNickNameFollowing.setTag(null);
        this.tvUsername.setTag(null);
        setRootTag(root);
        // listeners
        mCallback12 = new com.tennine.app.generated.callback.OnClickListener(this, 1);
        mCallback13 = new com.tennine.app.generated.callback.OnClickListener(this, 2);
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
        else if (BR.itemCLickListener == variableId) {
            setItemCLickListener((com.tennine.app.listener.OnClickListener) variable);
        }
        else if (BR.position == variableId) {
            setPosition((int) variable);
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
    public void setItemCLickListener(@Nullable com.tennine.app.listener.OnClickListener ItemCLickListener) {
        this.mItemCLickListener = ItemCLickListener;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.itemCLickListener);
        super.requestRebind();
    }
    public void setPosition(int Position) {
        this.mPosition = Position;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.position);
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
        com.tennine.app.listener.OnClickListener itemCLickListener = mItemCLickListener;
        int position = mPosition;
        java.lang.String modelImage = null;
        java.lang.String modelUsername = null;
        java.lang.String modelNickname = null;

        if ((dirtyFlags & 0x9L) != 0) {



                if (model != null) {
                    // read model.image
                    modelImage = model.getImage();
                    // read model.username
                    modelUsername = model.getUsername();
                    // read model.nickname
                    modelNickname = model.getNickname();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.btnRemoveFollowing.setOnClickListener(mCallback13);
            this.ivProfile.setOnClickListener(mCallback12);
        }
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 1

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
                // itemCLickListener
                com.tennine.app.listener.OnClickListener itemCLickListener = mItemCLickListener;
                // position
                int position = mPosition;
                // itemCLickListener != null
                boolean itemCLickListenerJavaLangObjectNull = false;



                itemCLickListenerJavaLangObjectNull = (itemCLickListener) != (null);
                if (itemCLickListenerJavaLangObjectNull) {




                    itemCLickListener.onUserViewCLick(model, position);
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // model
                com.tennine.app.model.FollowingFollowersModel model = mModel;
                // itemCLickListener
                com.tennine.app.listener.OnClickListener itemCLickListener = mItemCLickListener;
                // position
                int position = mPosition;
                // itemCLickListener != null
                boolean itemCLickListenerJavaLangObjectNull = false;



                itemCLickListenerJavaLangObjectNull = (itemCLickListener) != (null);
                if (itemCLickListenerJavaLangObjectNull) {




                    itemCLickListener.onRemoveUser(model, position);
                }
                break;
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): model
        flag 1 (0x2L): itemCLickListener
        flag 2 (0x3L): position
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}