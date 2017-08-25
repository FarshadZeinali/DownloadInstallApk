# DownloadInstallApk
I wrote a new simple module that helps you to specify a url and a local device path and then start downloads and install apk.

sample code is as below :

        <java-code>
        String url="apk_url";
        String apk_path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/test_path/my_apk.apk";

        DownloadInstallApkManager downloadInstallApkManager=new DownloadInstallApkManager();
        downloadInstallApkManager.startDownloadInstall(this,url,apk_path,R.mipmap.ic_launcher_round);
        </java-code>
