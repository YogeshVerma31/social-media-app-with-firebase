package com.tennine.app.databinding;
import com.tennine.app.R;
import com.tennine.app.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemHomeLayoutBindingImpl extends ItemHomeLayoutBinding implements com.tennine.app.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linearLayoutCompat, 11);
        sViewsWithIds.put(R.id.imageView20, 12);
        sViewsWithIds.put(R.id.textView45, 13);
        sViewsWithIds.put(R.id.ratingBar, 14);
        sViewsWithIds.put(R.id.ivEmoji, 15);
        sViewsWithIds.put(R.id.textView8, 16);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView7;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback8;
    @Nullable
    private final android.view.View.OnClickListener mCallback6;
    @Nullable
    private final android.view.View.OnClickListener mCallback9;
    @Nullable
    private final android.view.View.OnClickListener mCallback7;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemHomeLayoutBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private ItemHomeLayoutBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[5]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.ImageView) bindings[12]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[15]
            , (com.tennine.app.utils.SquareImageView) bindings[3]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (androidx.appcompat.widget.LinearLayoutCompat) bindings[11]
            , (androidx.appcompat.widget.LinearLayoutCompat) bindings[6]
            , (android.widget.RatingBar) bindings[14]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[2]
            );
        this.btnAbout.setTag(null);
        this.imageView19.setTag(null);
        this.ivPost.setTag(null);
        this.ivProfile.setTag(null);
        this.linearLayoutCompat2.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView7 = (android.widget.TextView) bindings[7];
        this.mboundView7.setTag(null);
        this.textView10.setTag(null);
        this.textView39.setTag(null);
        this.textView46.setTag(null);
        this.tvUserName.setTag(null);
        setRootTag(root);
        // listeners
        mCallback8 = new com.tennine.app.generated.callback.OnClickListener(this, 3);
        mCallback6 = new com.tennine.app.generated.callback.OnClickListener(this, 1);
        mCallback9 = new com.tennine.app.generated.callback.OnClickListener(this, 4);
        mCallback7 = new com.tennine.app.generated.callback.OnClickListener(this, 2);
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
            setModel((com.tennine.app.model.PostModel) variable);
        }
        else if (BR.position == variableId) {
            setPosition((int) variable);
        }
        else if (BR.itemlistener == variableId) {
            setItemlistener((com.tennine.app.listener.OnFeedClickListener) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setModel(@Nullable com.tennine.app.model.PostModel Model) {
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
    public void setItemlistener(@Nullable com.tennine.app.listener.OnFeedClickListener Itemlistener) {
        this.mItemlistener = Itemlistener;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.itemlistener);
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
        com.tennine.app.model.PostModel model = mModel;
        java.lang.String modelPostImage = null;
        java.lang.String modelImageUrl = null;
        int position = mPosition;
        java.lang.String modelUserName = null;
        java.lang.Boolean modelLikeOrNot = null;
        java.lang.String modelPostCommentsJavaLangStringComments = null;
        long modelPostDate = 0;
        java.lang.Long modelPostComments = null;
        java.lang.String modelPostDescription = null;
        java.lang.String modelPostLikesJavaLangStringOthers = null;
        java.lang.String modelPostLikesJavaLangStringLikes = null;
        com.tennine.app.listener.OnFeedClickListener itemlistener = mItemlistener;
        java.lang.Long modelPostLikes = null;

        if ((dirtyFlags & 0x9L) != 0) {



                if (model != null) {
                    // read model.postImage
                    modelPostImage = model.getPostImage();
                    // read model.imageUrl
                    modelImageUrl = model.getImageUrl();
                    // read model.userName
                    modelUserName = model.getUserName();
                    // read model.likeOrNot
                    modelLikeOrNot = model.getLikeOrNot();
                    // read model.postDate
                    modelPostDate = model.getPostDate();
                    // read model.postComments
                    modelPostComments = model.getPostComments();
                    // read model.postDescription
                    modelPostDescription = model.getPostDescription();
                    // read model.postLikes
                    modelPostLikes = model.getPostLikes();
                }


                // read (model.postComments) + (" comments")
                modelPostCommentsJavaLangStringComments = (modelPostComments) + (" comments");
                // read (model.postLikes) + (" others")
                modelPostLikesJavaLangStringOthers = (modelPostLikes) + (" others");
                // read (model.postLikes) + (" likes")
                modelPostLikesJavaLangStringLikes = (modelPostLikes) + (" likes");
        }
        // batch finished
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.btnAbout, modelPostLikesJavaLangStringLikes);
            com.tennine.app.adapter.BindingAdapters.setLikeBtn(this.imageView19, modelLikeOrNot);
            com.tennine.app.adapter.BindingAdapters.setPostImage(this.ivPost, modelPostImage);
            com.tennine.app.adapter.BindingAdapters.setImageUrl(this.ivProfile, modelImageUrl);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView7, modelPostCommentsJavaLangStringComments);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.textView10, modelPostLikesJavaLangStringOthers);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.textView39, modelPostDescription);
            com.tennine.app.adapter.BindingAdapters.setPostTime(this.textView46, modelPostDate);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tvUserName, modelUserName);
        }
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.imageView19.setOnClickListener(mCallback8);
            this.ivProfile.setOnClickListener(mCallback6);
            this.linearLayoutCompat2.setOnClickListener(mCallback9);
            this.tvUserName.setOnClickListener(mCallback7);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        switch(sourceId) {
            case 3: {
                // localize variables for thread safety
                // model
                com.tennine.app.model.PostModel model = mModel;
                // position
                int position = mPosition;
                // itemlistener
                com.tennine.app.listener.OnFeedClickListener itemlistener = mItemlistener;
                // itemlistener != null
                boolean itemlistenerJavaLangObjectNull = false;



                itemlistenerJavaLangObjectNull = (itemlistener) != (null);
                if (itemlistenerJavaLangObjectNull) {





                    itemlistener.onLikeClick(callbackArg_0, model, position);
                }
                break;
            }
            case 1: {
                // localize variables for thread safety
                // model
                com.tennine.app.model.PostModel model = mModel;
                // position
                int position = mPosition;
                // itemlistener
                com.tennine.app.listener.OnFeedClickListener itemlistener = mItemlistener;
                // itemlistener != null
                boolean itemlistenerJavaLangObjectNull = false;



                itemlistenerJavaLangObjectNull = (itemlistener) != (null);
                if (itemlistenerJavaLangObjectNull) {




                    itemlistener.onUserView(model, position);
                }
                break;
            }
            case 4: {
                // localize variables for thread safety
                // model
                com.tennine.app.model.PostModel model = mModel;
                // position
                int position = mPosition;
                // itemlistener
                com.tennine.app.listener.OnFeedClickListener itemlistener = mItemlistener;
                // itemlistener != null
                boolean itemlistenerJavaLangObjectNull = false;



                itemlistenerJavaLangObjectNull = (itemlistener) != (null);
                if (itemlistenerJavaLangObjectNull) {




                    itemlistener.onCommentClick(model, position);
                }
                break;
            }
            case 2: {
                // localize variables for thread safety
                // model
                com.tennine.app.model.PostModel model = mModel;
                // position
                int position = mPosition;
                // itemlistener
                com.tennine.app.listener.OnFeedClickListener itemlistener = mItemlistener;
                // itemlistener != null
                boolean itemlistenerJavaLangObjectNull = false;



                itemlistenerJavaLangObjectNull = (itemlistener) != (null);
                if (itemlistenerJavaLangObjectNull) {




                    itemlistener.onUserView(model, position);
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
        flag 2 (0x3L): itemlistener
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}