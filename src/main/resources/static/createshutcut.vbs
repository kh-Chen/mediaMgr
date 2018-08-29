Set objArgs = WScript. Arguments

'msgbox objArgs(0)
'msgbox objArgs(1)

Set a=CreateObject("WScript.Shell") 
Set b=a.CreateShortcut(objArgs(0))
b.TargetPath=objArgs(1)
b.WorkingDirectory=""
b.Save
