/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\iii\\Desktop\\java\\TeeViewer\\src\\com\\asgreywolf\\teeviewer\\UpdateService.aidl
 */
package com.asgreywolf.teeviewer;
public interface UpdateService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.asgreywolf.teeviewer.UpdateService
{
private static final java.lang.String DESCRIPTOR = "com.asgreywolf.teeviewer.UpdateService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.asgreywolf.teeviewer.UpdateService interface,
 * generating a proxy if needed.
 */
public static com.asgreywolf.teeviewer.UpdateService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.asgreywolf.teeviewer.UpdateService))) {
return ((com.asgreywolf.teeviewer.UpdateService)iin);
}
return new com.asgreywolf.teeviewer.UpdateService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_GetServer:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
com.asgreywolf.teeviewer.ServerTransfer _result = this.GetServer(_arg0);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_GetServers:
{
data.enforceInterface(DESCRIPTOR);
com.asgreywolf.teeviewer.ServerTransfer[] _result = this.GetServers();
reply.writeNoException();
reply.writeTypedArray(_result, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
return true;
}
case TRANSACTION_AddServer:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
this.AddServer(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_EditServer:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _arg2;
_arg2 = data.readInt();
this.EditServer(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_RemoveServer:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.RemoveServer(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_Check:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.Check(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_CheckAll:
{
data.enforceInterface(DESCRIPTOR);
this.CheckAll();
reply.writeNoException();
return true;
}
case TRANSACTION_CheckSettings:
{
data.enforceInterface(DESCRIPTOR);
this.CheckSettings();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.asgreywolf.teeviewer.UpdateService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public com.asgreywolf.teeviewer.ServerTransfer GetServer(int id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.asgreywolf.teeviewer.ServerTransfer _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(id);
mRemote.transact(Stub.TRANSACTION_GetServer, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = com.asgreywolf.teeviewer.ServerTransfer.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public com.asgreywolf.teeviewer.ServerTransfer[] GetServers() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
com.asgreywolf.teeviewer.ServerTransfer[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetServers, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArray(com.asgreywolf.teeviewer.ServerTransfer.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void AddServer(java.lang.String ip, int port) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(ip);
_data.writeInt(port);
mRemote.transact(Stub.TRANSACTION_AddServer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void EditServer(int id, java.lang.String ip, int port) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(id);
_data.writeString(ip);
_data.writeInt(port);
mRemote.transact(Stub.TRANSACTION_EditServer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RemoveServer(int id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(id);
mRemote.transact(Stub.TRANSACTION_RemoveServer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void Check(int id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(id);
mRemote.transact(Stub.TRANSACTION_Check, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void CheckAll() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_CheckAll, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void CheckSettings() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_CheckSettings, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_GetServer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_GetServers = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_AddServer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_EditServer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_RemoveServer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_Check = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_CheckAll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_CheckSettings = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
}
public com.asgreywolf.teeviewer.ServerTransfer GetServer(int id) throws android.os.RemoteException;
public com.asgreywolf.teeviewer.ServerTransfer[] GetServers() throws android.os.RemoteException;
public void AddServer(java.lang.String ip, int port) throws android.os.RemoteException;
public void EditServer(int id, java.lang.String ip, int port) throws android.os.RemoteException;
public void RemoveServer(int id) throws android.os.RemoteException;
public void Check(int id) throws android.os.RemoteException;
public void CheckAll() throws android.os.RemoteException;
public void CheckSettings() throws android.os.RemoteException;
}
