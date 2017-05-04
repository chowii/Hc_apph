package au.com.holcim.holcimapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jovan on 3/5/17.
 */

public class ProgressBarView extends FrameLayout {

    @Bind(R.id.tv_progress) TextView mTvProgress;
    @Bind(R.id.tv_tracking) TextView mTvTracking;
    @Bind(R.id.rc_progress_bar) RoundCornerProgressBar mRcProgressBar;

    public ProgressBarView(Context context) {
        super(context);
        init(context, null);
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.progress_bar_view, this);
        ButterKnife.bind(this);

        if (attrs!=null){
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.ProgressBarView,
                    0, 0);

            mTvTracking.setText(a.getText(R.styleable.ProgressBarView_tracking));
            mTvProgress.setText(a.getText(R.styleable.ProgressBarView_progress_text));
            mRcProgressBar.setMax(a.getFloat(R.styleable.ProgressBarView_max, 0));
            mRcProgressBar.setProgress(a.getFloat(R.styleable.ProgressBarView_progress, 0));
        }
    }

    public void setContent(Float progress, Float max, String progressText) {
        mRcProgressBar.setMax(max);
        mRcProgressBar.setProgress(progress);
        if(progressText != null) {
            mTvProgress.setText(progressText);
        } else {
            mTvProgress.setText(progress + " / " + max);
        }
    }
}
