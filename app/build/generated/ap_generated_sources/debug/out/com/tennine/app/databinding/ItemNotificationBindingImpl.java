package com.tennine.app.databinding;
import com.tennine.app.R;
import com.tennine.app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemNotificationBindingImpl extends ItemNotificationBinding implements com.tennine.app.generated.callback.OnClickListener.Listener {

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
    @NonNull
    private final androidx.appcompat.widget.AppCompatTextView mboundView4;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback1;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemNotificationBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private ItemNotificationBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatTextView) bindings[3]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[5]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[2]
            );
        this.appCompatTextView.setTag(null);
        this.ivPost.setTag(null);
        this.ivProfile.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView4 = (androidx.appcompat.widget.AppCompatTextView) bindings[4];
        this.mboundView4.setTag(null);
        this.tvUserName.setTag(null);
        setRootTag(root);
        // listeners
        mCallback1 = new com.tennine.app.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
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
            setModel((com.tennine.app.model.NotificationModel) variable);
        }
        else if (BR.listener == variableId) {
            setListener((com.tennine.app.listener.NotificationListener) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setModel(@Nullable com.tennine.app.model.NotificationModel Model) {
        this.mModel = Model;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.model);
        super.requestRebind();
    }
    public void setListener(@Nullable com.tennine.app.listener.NotificationListener Listener) {
        this.mListener = Listener;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.listener);
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
        com.tennine.app.model.NotificationModel model = mModel;
        java.lang.String modelPostImage = null;
        java.lang.String modelUserName = null;
        long modelNotificationAt = 0;
        java.lang.String modelDescription = null;
        com.tennine.app.listener.NotificationListener listener = mListener;
        java.lang.String modelUserImage = null;

        if ((dirtyFlags & 0x5L) != 0) {



                if (model != null) {
                    // read model.postImage
                    modelPostImage = model.getPostImage();
                    // read model.userName
                    modelUserName = model.getUserName();
                    // read model.notificationAt
                    modelNotificationAt = model.getNotificationAt();
                    // read model.description
                    modelDescription = model.getDescription();
                    // read model.userImage
                    modelUserImage = model.getUserImage();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            com.tennine.app.adapter.BindingAdapters.setPostTime(this.appCompatTextView, modelNotificationAt);
            com.tennine.app.adapter.BindingAdapters.setPostImage(this.ivPost, modelPostImage);
            com.tennine.app.adapter.BindingAdapters.setImageUrl(this.ivProfile, modelUserImage);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, modelDescription);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvUserName, modelUserName);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            this.mboundView0.setOnClickListener(mCallback1);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // model
        com.tennine.app.model.NotificationModel model = mModel;
        // listener != null
        boolean listenerJavaLangObjectNull = false;
        // listener
        com.tennine.app.listener.NotificationListener listener = mListener;



        listenerJavaLangObjectNull = (listener) != (null);
        if (listenerJavaLangObjectNull) {



            listener.onClick(model);
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): model
        flag 1 (0x2L): listener
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}