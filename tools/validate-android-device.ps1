param(
    [string]$Serial = "",
    [string]$ApkPath = "",
    [string]$PackageName = "com.atifbukhari.cloudgaming.debug",
    [string]$OutputDir = ".\validation-output"
)

$ErrorActionPreference = "Stop"

function Resolve-Adb {
    if (Get-Command adb -ErrorAction SilentlyContinue) {
        return (Get-Command adb).Source
    }
    if ($env:ANDROID_SDK_ROOT) {
        $candidate = Join-Path $env:ANDROID_SDK_ROOT "platform-tools\adb.exe"
        if (Test-Path $candidate) { return $candidate }
    }
    if ($env:ANDROID_HOME) {
        $candidate = Join-Path $env:ANDROID_HOME "platform-tools\adb.exe"
        if (Test-Path $candidate) { return $candidate }
    }
    $candidate = Join-Path $env:LOCALAPPDATA "Android\Sdk\platform-tools\adb.exe"
    if (Test-Path $candidate) { return $candidate }
    throw "ADB not found. Install Android SDK Platform-Tools or set ANDROID_SDK_ROOT."
}

$adb = Resolve-Adb
& $adb start-server | Out-Null

$deviceLines = & $adb devices
$devices = @()
foreach ($line in $deviceLines) {
    if ($line -match "^([^\s]+)\s+device$") {
        $devices += $Matches[1]
    }
}

if (-not $Serial) {
    $physical = @($devices | Where-Object { $_ -notmatch "^emulator-" })
    if ($physical.Count -eq 1) {
        $Serial = $physical[0]
    }
    elseif ($physical.Count -gt 1) {
        throw "Multiple physical Android devices are connected. Re-run with -Serial <device>."
    }
    elseif ($devices.Count -eq 1) {
        $Serial = $devices[0]
        Write-Warning "No physical phone detected; falling back to $Serial."
    }
    else {
        throw "No authorized Android device found. Enable USB debugging and approve the RSA prompt."
    }
}

if (-not ($devices -contains $Serial)) {
    throw "Device '$Serial' is not in ADB device state."
}

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null

function AdbShell([string]$Command) {
    return (& $adb -s $Serial shell $Command 2>$null | Out-String).Trim()
}

$hostEpoch = [DateTimeOffset]::UtcNow.ToUnixTimeSeconds()
$deviceEpochRaw = AdbShell "date +%s"
$deviceEpoch = 0L
[void][Int64]::TryParse(($deviceEpochRaw -replace "[^0-9]",""), [ref]$deviceEpoch)
$clockSkewSeconds = if ($deviceEpoch -gt 0) { [math]::Abs($hostEpoch - $deviceEpoch) } else { -1 }

$profile = [ordered]@{
    captured_utc = [DateTime]::UtcNow.ToString("o")
    serial = $Serial
    manufacturer = AdbShell "getprop ro.product.manufacturer"
    model = AdbShell "getprop ro.product.model"
    device = AdbShell "getprop ro.product.device"
    android_release = AdbShell "getprop ro.build.version.release"
    sdk = AdbShell "getprop ro.build.version.sdk"
    abi = AdbShell "getprop ro.product.cpu.abi"
    abi_list = AdbShell "getprop ro.product.cpu.abilist"
    build_fingerprint = AdbShell "getprop ro.build.fingerprint"
    clock_skew_seconds = $clockSkewSeconds
    wifi_ip = AdbShell "ip -4 addr show wlan0"
    display = AdbShell "wm size"
    density = AdbShell "wm density"
}

$profilePath = Join-Path $OutputDir "device-profile-$($Serial -replace '[^A-Za-z0-9._-]','_').json"
$profile | ConvertTo-Json -Depth 4 | Set-Content -Encoding UTF8 $profilePath

Write-Host "Device: $($profile.manufacturer) $($profile.model)"
Write-Host "Android: $($profile.android_release) (SDK $($profile.sdk))"
Write-Host "ABI: $($profile.abi)"
if ($clockSkewSeconds -ge 0) {
    Write-Host "Host/device clock skew: $clockSkewSeconds second(s)"
    if ($clockSkewSeconds -gt 120) {
        Write-Warning "Clock skew exceeds 120 seconds. Fix device time before Sunshine pairing."
    }
}

if ($ApkPath) {
    $resolvedApk = (Resolve-Path $ApkPath).Path
    Write-Host "Installing: $resolvedApk"
    & $adb -s $Serial install -r $resolvedApk
    if ($LASTEXITCODE -ne 0) { throw "APK installation failed." }
}

$installed = (& $adb -s $Serial shell pm list packages $PackageName | Out-String)
if ($installed -match [regex]::Escape($PackageName)) {
    Write-Host "Launching $PackageName"
    & $adb -s $Serial shell monkey -p $PackageName -c android.intent.category.LAUNCHER 1 | Out-Null
}
else {
    Write-Warning "$PackageName is not installed. Supply -ApkPath to install it."
}

$codecPath = Join-Path $OutputDir "media-codecs-$($Serial -replace '[^A-Za-z0-9._-]','_').txt"
& $adb -s $Serial shell dumpsys media.codec 2>$null | Set-Content -Encoding UTF8 $codecPath

$logcatPath = Join-Path $OutputDir "logcat-baseline-$($Serial -replace '[^A-Za-z0-9._-]','_').txt"
& $adb -s $Serial logcat -d -t 1500 2>$null | Set-Content -Encoding UTF8 $logcatPath

Write-Host ""
Write-Host "Validation capture complete:"
Write-Host "  Device profile: $profilePath"
Write-Host "  Codec dump:     $codecPath"
Write-Host "  Logcat:         $logcatPath"
Write-Host ""
Write-Host "Next: pair to Sunshine, stream SuperTuxKart, test Auto/Fast/Balanced/Quality,"
Write-Host "then capture telemetry and verify Disconnect -> Resume -> End Game."
