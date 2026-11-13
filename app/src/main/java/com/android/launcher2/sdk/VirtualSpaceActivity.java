package com.android.launcher2.sdk;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * First-stage virtual-space catalogue.
 *
 * This screen only stores a user's selection of installed apps. It does not
 * install, clone, sandbox, or independently execute those applications.
 */
public final class VirtualSpaceActivity extends Activity {
    private static final String PREFS = "spatch_virtual_space";
    private static final String KEY_PACKAGES = "selected_packages";
    private final List<CheckBox> appChecks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(18), dp(18), dp(18));
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        TextView title = new TextView(this);
        title.setText("مساحة SPatch");
        title.setTextSize(24);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        root.addView(title);

        TextView note = new TextView(this);
        note.setText("اختر التطبيقات التي تريد إضافتها إلى قائمتك الخاصة. هذه مرحلة تنظيمية فقط؛ النسخ الافتراضي المستقل غير متاح بعد.");
        note.setTextSize(15);
        note.setPadding(0, dp(10), 0, dp(16));
        root.addView(note);

        ScrollView scroll = new ScrollView(this);
        LinearLayout list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);
        scroll.addView(list, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(scroll, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

        loadApps(list);

        Button save = new Button(this);
        save.setText("حفظ التطبيقات المختارة");
        save.setOnClickListener(v -> saveSelection());
        root.addView(save);

        setContentView(root);
    }

    private void loadApps(LinearLayout list) {
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        Set<String> saved = getSharedPreferences(PREFS, MODE_PRIVATE)
                .getStringSet(KEY_PACKAGES, Collections.emptySet());

        List<ApplicationInfo> launchable = new ArrayList<>();
        for (ApplicationInfo info : apps) {
            if (pm.getLaunchIntentForPackage(info.packageName) != null
                    && !getPackageName().equals(info.packageName)) {
                launchable.add(info);
            }
        }
        Collections.sort(launchable, Comparator.comparing(
                info -> pm.getApplicationLabel(info).toString(),
                String.CASE_INSENSITIVE_ORDER));

        for (ApplicationInfo info : launchable) {
            CheckBox check = new CheckBox(this);
            check.setText(pm.getApplicationLabel(info));
            check.setTextSize(16);
            check.setCompoundDrawablesWithIntrinsicBounds(
                    pm.getApplicationIcon(info), null, null, null);
            check.setCompoundDrawablePadding(dp(12));
            check.setGravity(Gravity.CENTER_VERTICAL);
            check.setMinHeight(dp(52));
            check.setTag(info.packageName);
            check.setChecked(saved.contains(info.packageName));
            list.addView(check, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            appChecks.add(check);
        }

        if (launchable.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("لم يتم العثور على تطبيقات قابلة للتشغيل.");
            list.addView(empty);
        }
    }

    private void saveSelection() {
        Set<String> selected = new HashSet<>();
        for (CheckBox check : appChecks) {
            if (check.isChecked()) selected.add((String) check.getTag());
        }
        getSharedPreferences(PREFS, MODE_PRIVATE).edit()
                .putStringSet(KEY_PACKAGES, selected).apply();
        Toast.makeText(this, "تم حفظ " + selected.size() + " تطبيق", Toast.LENGTH_SHORT).show();
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
