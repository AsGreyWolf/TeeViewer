package com.asgreywolf.teeviewer;

import android.util.Log;

public class Tools {

	public static String[] flags;
	static int currentapiVersion = android.os.Build.VERSION.SDK_INT;
	public static boolean Honeycomb(){
		if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
			return true;
		}
		return false;
	}
	public static boolean Froyo(){
		if (currentapiVersion >= android.os.Build.VERSION_CODES.FROYO){
			return true;
		}
		return false;
	}
	public static void Err(String err){
		Log.e("TeeViewer", err);
	}
	public static void Info(String err){
		Log.v("TeeViewer", err);
	}
	public static void Warn(String err){
		Log.w("TeeViewer", err);
	}
	public static void Exc(Exception e){
		Log.e("TeeViewer","exception", e);
	}
	public static void InitFlags(){
		flags=new String[1000];
		for(int i=0;i<1000;i++){
			flags[i]=null;
		}
		flags[0]="default";
		flags[901]="XEN";
		flags[902]="XNI";
		flags[903]="XSC";
		flags[904]="XWA";
		flags[737]="SS";
		flags[4]="AF";
		flags[248]="AX";
		flags[8]="AL";
		flags[12]="DZ";
		flags[16]="AS";
		flags[20]="AD";
		flags[24]="AO";
		flags[660]="AI";
		flags[28]="AG";
		flags[32]="AR";
		flags[51]="AM";
		flags[533]="AW";
		flags[36]="AU";
		flags[40]="AT";
		flags[31]="AZ";
		flags[44]="BS";
		flags[48]="BH";
		flags[50]="BD";
		flags[52]="BB";
		flags[112]="BY";
		flags[56]="BE";
		flags[84]="BZ";
		flags[204]="BJ";
		flags[60]="BM";
		flags[64]="BT";
		flags[68]="BO";
		flags[70]="BA";
		flags[72]="BW";
		flags[76]="BR";
		flags[86]="IO";
		flags[96]="BN";
		flags[100]="BG";
		flags[854]="BF";
		flags[108]="BI";
		flags[116]="KH";
		flags[120]="CM";
		flags[124]="CA";
		flags[132]="CV";
		flags[136]="KY";
		flags[140]="CF";
		flags[148]="TD";
		flags[152]="CL";
		flags[156]="CN";
		flags[162]="CX";
		flags[166]="CC";
		flags[170]="CO";
		flags[174]="KM";
		flags[178]="CG";
		flags[180]="CD";
		flags[184]="CK";
		flags[188]="CR";
		flags[384]="CI";
		flags[191]="HR";
		flags[192]="CU";
		flags[531]="CW";
		flags[196]="CY";
		flags[203]="CZ";
		flags[208]="DK";
		flags[262]="DJ";
		flags[212]="DM";
		flags[214]="DO";
		flags[218]="EC";
		flags[818]="EG";
		flags[222]="SV";
		flags[226]="GQ";
		flags[232]="ER";
		flags[233]="EE";
		flags[231]="ET";
		flags[238]="FK";
		flags[234]="FO";
		flags[242]="FJ";
		flags[246]="FI";
		flags[250]="FR";
		flags[254]="GF";
		flags[258]="PF";
		flags[260]="TF";
		flags[266]="GA";
		flags[270]="GM";
		flags[268]="GE";
		flags[276]="DE";
		flags[288]="GH";
		flags[292]="GI";
		flags[300]="GR";
		flags[304]="GL";
		flags[308]="GD";
		flags[312]="GP";
		flags[316]="GU";
		flags[320]="GT";
		flags[831]="GG";
		flags[324]="GN";
		flags[624]="GW";
		flags[328]="GY";
		flags[332]="HT";
		flags[336]="VA";
		flags[640]="HN";
		flags[344]="HK";
		flags[348]="HU";
		flags[352]="IS";
		flags[356]="IN";
		flags[360]="ID";
		flags[364]="IR";
		flags[368]="IQ";
		flags[372]="IE";
		flags[833]="IM";
		flags[376]="IL";
		flags[380]="IT";
		flags[388]="JM";
		flags[392]="JP";
		flags[832]="JE";
		flags[400]="JO";
		flags[398]="KZ";
		flags[404]="KE";
		flags[296]="KI";
		flags[408]="KP";
		flags[410]="KR";
		flags[414]="KW";
		flags[417]="KG";
		flags[418]="LA";
		flags[428]="LV";
		flags[422]="LB";
		flags[426]="LS";
		flags[430]="LR";
		flags[434]="LY";
		flags[438]="LI";
		flags[440]="LT";
		flags[442]="LU";
		flags[446]="MO";
		flags[807]="MK";
		flags[450]="MG";
		flags[454]="MW";
		flags[458]="MY";
		flags[462]="MV";
		flags[466]="ML";
		flags[470]="MT";
		flags[584]="MH";
		flags[474]="MQ";
		flags[478]="MR";
		flags[480]="MU";
		flags[484]="MX";
		flags[583]="FM";
		flags[498]="MD";
		flags[492]="MC";
		flags[496]="MN";
		flags[499]="ME";
		flags[500]="MS";
		flags[504]="MA";
		flags[508]="MZ";
		flags[104]="MM";
		flags[516]="NA";
		flags[520]="NR";
		flags[524]="NP";
		flags[528]="NL";
		flags[540]="NC";
		flags[554]="NZ";
		flags[558]="NI";
		flags[562]="NE";
		flags[566]="NG";
		flags[570]="NU";
		flags[574]="NF";
		flags[580]="MP";
		flags[578]="NO";
		flags[512]="OM";
		flags[586]="PK";
		flags[585]="PW";
		flags[591]="PA";
		flags[598]="PG";
		flags[600]="PY";
		flags[604]="PE";
		flags[608]="PH";
		flags[612]="PN";
		flags[616]="PL";
		flags[620]="PT";
		flags[630]="PR";
		flags[634]="QA";
		flags[638]="RE";
		flags[642]="RO";
		flags[643]="RU";
		flags[646]="RW";
		flags[652]="BL";
		flags[654]="SH";
		flags[659]="KN";
		flags[662]="LC";
		flags[663]="MF";
		flags[666]="PM";
		flags[670]="VC";
		flags[882]="WS";
		flags[674]="SM";
		flags[678]="ST";
		flags[682]="SA";
		flags[686]="SN";
		flags[688]="RS";
		flags[690]="SC";
		flags[694]="SL";
		flags[702]="SG";
		flags[534]="SX";
		flags[703]="SK";
		flags[705]="SI";
		flags[90]="SB";
		flags[706]="SO";
		flags[710]="ZA";
		flags[239]="GS";
		flags[724]="ES";
		flags[144]="LK";
		flags[736]="SD";
		flags[740]="SR";
		flags[748]="SZ";
		flags[752]="SE";
		flags[756]="CH";
		flags[760]="SY";
		flags[158]="TW";
		flags[762]="TJ";
		flags[834]="TZ";
		flags[764]="TH";
		flags[626]="TL";
		flags[768]="TG";
		flags[772]="TK";
		flags[776]="TO";
		flags[780]="TT";
		flags[788]="TN";
		flags[792]="TR";
		flags[795]="TM";
		flags[796]="TC";
		flags[798]="TV";
		flags[800]="UG";
		flags[804]="UA";
		flags[784]="AE";
		flags[826]="GB";
		flags[840]="US";
		flags[858]="UY";
		flags[860]="UZ";
		flags[548]="VU";
		flags[862]="VE";
		flags[704]="VN";
		flags[92]="VG";
		flags[850]="VI";
		flags[876]="WF";
		flags[732]="EH";
		flags[887]="YE";
		flags[894]="ZM";
		flags[716]="ZW";
		String n=flags[0];
		for(int i=0;i<100;i++){
			if(flags[i]==null)
				flags[i]=n;
		}
	}
}
