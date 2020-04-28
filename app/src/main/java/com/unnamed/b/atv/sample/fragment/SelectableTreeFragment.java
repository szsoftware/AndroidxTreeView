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
import com.unnamed.b.atv.sample.holder.IconTreeItemHolder;
import com.unnamed.b.atv.sample.holder.ProfileHolder;
import com.unnamed.b.atv.sample.holder.SelectableHeaderHolder;
import com.unnamed.b.atv.sample.holder.SelectableItemHolder;
import com.unnamed.b.atv.view.AndroidTreeView;

/**
 * Created by Bogdan Melnychuk on 2/12/15.
 */
public class SelectableTreeFragment extends Fragment {
    private AndroidTreeView mTreeView;
    private boolean mSelectionModeEnabled = false;

    FragmentSelectableNodesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSelectableNodesBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        ViewGroup containerView = binding.container;
        View selectionModeButton = binding.btnToggleSelection;

        selectionModeButton.setOnClickListener(v -> {
            mSelectionModeEnabled = !mSelectionModeEnabled;
            mTreeView.setSelectionModeEnabled(mSelectionModeEnabled);
        });

        View selectAllBtn = binding.btnSelectAll;
        selectAllBtn.setOnClickListener(v -> {
            if (!mSelectionModeEnabled) {
                Toast.makeText(getActivity(), "Enable selection mode first", Toast.LENGTH_SHORT).show();
            }
            mTreeView.selectAll(true);
        });

        View deselectAll = binding.btnDeselectAll;
        deselectAll.setOnClickListener(v -> {
            if (!mSelectionModeEnabled) {
                Toast.makeText(getActivity(), "Enable selection mode first", Toast.LENGTH_SHORT).show();
            }
            mTreeView.deselectAll();
        });

        View check = binding.btnCheckSelection;
        check.setOnClickListener(v -> {
            if (!mSelectionModeEnabled) {
                Toast.makeText(getActivity(), "Enable selection mode first", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), mTreeView.getSelected().size() + " selected", Toast.LENGTH_SHORT).show();
            }
        });

        TreeNode root = TreeNode.root();

        TreeNode s1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_sd_storage, "Storage1")).setViewHolder(new ProfileHolder(getActivity()));
        TreeNode s2 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_sd_storage, "Storage2")).setViewHolder(new ProfileHolder(getActivity()));
        s1.setSelectable(false);
        s2.setSelectable(false);

        TreeNode folder1 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Folder 1")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode folder2 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Folder 2")).setViewHolder(new SelectableHeaderHolder(getActivity()));
        TreeNode folder3 = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Folder 3")).setViewHolder(new SelectableHeaderHolder(getActivity()));

        fillFolder(folder1);
        fillFolder(folder2);
        fillFolder(folder3);

        s1.addChildren(folder1, folder2);
        s2.addChildren(folder3);

        root.addChildren(s1, s2);

        mTreeView = new AndroidTreeView(getActivity(), root);
        mTreeView.setDefaultAnimation(true);
        containerView.addView(mTreeView.getView());

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                mTreeView.restoreState(state);
            }
        }
        return rootView;
    }

    private void fillFolder(TreeNode folder) {
        TreeNode file1 = new TreeNode("File1").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file2 = new TreeNode("File2").setViewHolder(new SelectableItemHolder(getActivity()));
        TreeNode file3 = new TreeNode("File3").setViewHolder(new SelectableItemHolder(getActivity()));
        folder.addChildren(file1, file2, file3);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tState", mTreeView.getSaveState());
    }
}
