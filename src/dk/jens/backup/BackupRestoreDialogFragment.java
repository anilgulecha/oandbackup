package dk.jens.backup;

import android.app.AlertDialog;
import android.app.Dialog;
//import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

public class BackupRestoreDialogFragment extends DialogFragment
{
    final static String TAG = OAndBackup.TAG; 
    String packageName, label;
    Context context;
    boolean isInstalled;
    AppInfo appInfo;
    public BackupRestoreDialogFragment(Context context, AppInfo appInfo)
    {
        this.context = context;
        this.appInfo = appInfo;
        this.packageName = appInfo.getPackageName();
        this.label = appInfo.getLabel();
        this.isInstalled = appInfo.isInstalled;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(packageName);
        builder.setTitle(label);

        //midlertidigt indtil custom layout med flere muligheder
        if(isInstalled)
        {
            builder.setPositiveButton("backup", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    OAndBackup obackup = (OAndBackup) getActivity();
                    obackup.callBackup(appInfo);
                }
            });
        }
        if(!appInfo.getLastBackupTimestamp().equals(context.getString(R.string.noBackupYet)))
        {
            builder.setNegativeButton("restore", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int id)
                {
                    RestoreOptionsDialogFragment restoreDialog = new RestoreOptionsDialogFragment(appInfo);
//                    restoreDialog.show(getFragmentManager(), "DialogFragment");
                    restoreDialog.show(getActivity().getSupportFragmentManager(), "DialogFragment");
                }
            });
        }
        return builder.create();
    }
}
