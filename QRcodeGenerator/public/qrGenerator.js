/**
 * QRアプリ操作JS
 */
 	//QR生成ボタンが押された時の処理
   $(function(){
       $("#qr-gn").click(function(){
       	   $("#qrcode").html("");
           var txt = $.trim($('textarea[name="txt"]').val());
           //空欄なら、エラーアラートを返す
           if(txt == '') {
       	   	 alert("入力欄が空欄です！");
       	   	 return false;
       	   }
       	   //size欄から、生成するQRの縦幅と横幅を取得
           var size = $('select[name="size"]').val();
           var sizeSplit = size.split('x');
           var width = sizeSplit[0];
           var height = sizeSplit[1];
           //QRコード生成呼び出し
           generateQRcode(width, height, txt );
           return false;
       });
      
      //JqueryでQR生成処理の呼び出し
	  function generateQRcode(width, height, text) {
	  	 $('#qrcode').qrcode({width: width,height: height,text: text});
	  }
  });