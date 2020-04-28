package com.unnamed.b.atv.sample.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.sample.R;
import com.unnamed.b.atv.sample.databinding.FragmentSelectableNodesBinding;
import com.unnamed.b.atv.sample.holder.ArrowExpandSelectableHeaderHolder;
import com.unnamed.b.atv.sample.holder.IconTreeItemHolder;
import com.unnamed.b.atv.view.AndroidTreeView;

/**
 * Created by Bogdan Melnychuk on 2/12/15 modified by Szigeti Peter 2/2/16.
 */
public class TwoDScrollingArrowExpandFragment extends Fragment implements TreeNode.TreeNodeClickListener{
    private static final String NAME = "Very long name for folder";

    private AndroidTreeView mTreeView;

    private FragmentSelectableNodesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSelectableNodesBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        binding.status.setVisibility(View.GONE);
        ViewGroup containerView = binding.container;

        TreeNode root = TreeNode.root();

        TreeNode s1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Folder with very long name ")).setViewHolder(
            new ArrowExpandSelectableHeaderHolder(getActivity()));
        TreeNode s2 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Another folder with very long name")).setViewHolder(
            new ArrowExpandSelectableHeaderHolder(getActivity()));

        fillFolder(s1);
        fillFolder(s2);

        root.addChildren(s1, s2);

        mTreeView = new AndroidTreeView(getActivity(), root)
                            .setDefaultAnimation(true)
                            .setUse2dScroll(true)
                            .setDefaultContainerStyle(R.style.TreeNodeStyleCustom)
                            .setDefaultNodeClickListener(TwoDScrollingArrowExpandFragment.this)
                            .setDefaultViewHolder(ArrowExpandSelectableHeaderHolder.class);

        containerView.addView(mTreeView.getView());
        mTreeView.setUseAutoToggle(false);

        mTreeView.expandAll();

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                mTreeView.restoreState(state);
            }
        }
        return rootView;
    }

    private void fillFolder(TreeNode folder) {
        TreeNode currentNode = folder;
        for (int i = 0; i < 4; i++) {
            TreeNode file = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, NAME + " " + i));
            currentNode.addChild(file);
            currentNode = file;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", mTreeView.getSaveState());
    }

    @Override
    public void onClick(TreeNode node, Object value) {
        Toast toast = Toast.makeText(getActivity(), ((IconTreeItemHolder.IconTreeItem)value).text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
