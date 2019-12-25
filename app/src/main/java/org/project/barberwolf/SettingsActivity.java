package org.project.barberwolf;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    public static boolean active = false;

    public String VOLUME_KEY;
    public int DEFAULT_VOLUME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        active = true;

        final SeekBar volumeBar = findViewById(R.id.volume_bar);

        VOLUME_KEY = getResources().getString(R.string.volume_key);
        DEFAULT_VOLUME = getResources().getInteger(R.integer.defaultVolume);

        volumeBar.setProgress(getSharedPreferences("Settings", MODE_PRIVATE)
                .getInt(VOLUME_KEY, DEFAULT_VOLUME));

        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Empty method
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Empty method
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                getSharedPreferences("Settings", MODE_PRIVATE).edit()
                        .putInt(VOLUME_KEY, progress).apply();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }
}
