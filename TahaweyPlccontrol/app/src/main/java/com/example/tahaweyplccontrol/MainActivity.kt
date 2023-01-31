package com.example.tahaweyplccontrol

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.os.Bundle

import android.os.Handler
import android.os.Message
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var menu :Int?=null
    var context: Context? = null
    var listView: ListView? = null
    var adapter: ListAdapter? = null
    var dialog: Dialog? = null
    var tahaweyDataSource: ArrayList<RegisterValue>? = null
    var connect: Button? = null
    var connect_m: Button? =null
    var writeBtn: Button? = null
    lateinit var port: String
    var ip:String? = null
    var address:String? = null
    var length:String? = null
    var slaveId:String? = null
    var txt1:EditText?=null
    var txt2:EditText?=null
    var head: ModHead? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //     setContentView(R.layout.lay3)
//
        //val hdb = databaseHandler(this)

        menu = 1
        txt1 = findViewById(R.id.eT1) as EditText
        txt2 = findViewById(R.id.eT2) as EditText


        port = "502" as String
        ip = txt1!!.text.toString()
        length = "5" as String
        address = "1" as String
        slaveId = txt2!!.text.toString()
        connect = findViewById(R.id.connectBtn) as Button
        connect_m = findViewById(R.id.button_Otp1) as Button
        tahaweyDataSource = ArrayList()
        listView = findViewById(R.id.ListView1) as ListView
        adapter = ListAdapter(this, R.layout.recyclerview_item, tahaweyDataSource)
        //context = this
        listView!!.adapter = adapter

//

      connect_m!!.setOnClickListener {


           // setContentView(R.layout.point_ticket)
            tvShowNumber.text = tahaweyDataSource!![0].regValue.toString()
            tvShowNumber.text =decimalToBinary(tvShowNumber.text.toString().toFloat().toInt())
            println(tvShowNumber.text.length)
            println(tvShowNumber.text.length)

            update_lay1()
            menu = 2

        }
        button_Otp2.setOnClickListener {
            var rno:Int
            rno=tV3.text.toString().toInt()
            var name_m:String
                name_m=tV4.text.toString()



          //  hdb.addData(ob)
            println("data added ")
            //Toast.makeText(applicationContext, "Data Added", Toast.LENGTH_LONG).show()
            //hdb.

            // setContentView(R.layout.point_ticket)
            tvShowNumber.text = tahaweyDataSource!![1].regValue.toString()
            tvShowNumber.text =decimalToBinary(tvShowNumber.text.toString().toFloat().toInt())
            println(tvShowNumber.text.length)
           // update_lay1()
            menu = 3

        }
        button_Otp3.setOnClickListener {

            // setContentView(R.layout.point_ticket)
            tvShowNumber.text = tahaweyDataSource!![2].regValue.toString()
            tvShowNumber.text =decimalToBinary(tvShowNumber.text.toString().toFloat().toInt())

            println(tvShowNumber.text.length)

            update_lay1()
            menu = 4


        }

        connect!!.setOnClickListener {
            tV1.text = ip.toString()
            tV2.text = slaveId.toString()
            try {val endpoint = EndPoint(ip.toString(),port.toInt(),address.toString().toInt(),length.toString().toInt(),slaveId.toString().toInt()                )
                Mod.getInstance().config(endpoint);head = ModHead(tahaweyHandler);head!!.connect();head!!.startPolling()
                closeKeyBored()
            } catch (e: Exception) {e.printStackTrace()}

        }

    }

        fun update_lay1() {

  //println(tvShowNumber.length())
            var len:Int
            len=tvShowNumber.text.length
            if (tvShowNumber.text.length>=11){len=11}
  for (m in 0..len-1) {
      var id = resources.getIdentifier("b0$m", "id", context!!.packageName)
      var b = findViewById<View>(id)
      b.setBackgroundResource(R.color.Blue)
      if (tvShowNumber.text!!.get(m)=='0') {b.setBackgroundResource(R.color.red)} else{b.setBackgroundResource(R.color.green)}
    }
    }
/*override fun onBackPressed() {        if (menu==1){this.finishAffinity();}
  if(menu==2){     setContentView(R.layout.activity_main); menu=1  }    }*/
fun get_item(){
  address = "4019" as String
  try {val endpoint = EndPoint(ip.toString(), port.toString().toInt(), address.toString().toInt(), length.toString().toInt(), slaveId.toString().toInt())
      Mod.getInstance().config(endpoint);
      head = ModHead(tahaweyHandler);head!!.connect();head!!.startPolling();closeKeyBored();} catch (e: Exception) {e.printStackTrace(); 		}

}
var connectionHandler: Handler = object : Handler() {
  override fun handleMessage(msg: Message) {val connected = msg.arg1;if (connected == 0) { connect!!.text = "Connect";} else {;connect!!.text = "Disconnect";}}
}
    var tahaweyHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            val vals = msg.data.getParcelableArrayList<RegisterValue>("regs")
            if (vals != null && vals.size != 0) {
                tahaweyDataSource!!.clear()
                tahaweyDataSource!!.addAll(vals)
                adapter!!.notifyDataSetChanged()
                tV1.text=tahaweyDataSource!![0].regValue.toString()
                tV2.text=tahaweyDataSource!![1].regValue.toString()
                tV3.text=tahaweyDataSource!![2].regValue.toString()
                tV4.text=tahaweyDataSource!![3].regValue.toString()
                tV5.text=tahaweyDataSource!![4].regValue.toString()


            }
        }

    }


    fun closeKeyBored() { val view = this.currentFocus;if (view != null) {val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager;imm.hideSoftInputFromWindow(view.windowToken, 0);}}
    fun decimalToBinary(num: Int): String? {println("num222 $num");println(Integer.toBinaryString(num));var b:String;b=Integer.toBinaryString(num).toString();return b;
       // buAdd2();
        //LoadQuery("%");
        println("sasasadsadsadsafsdafdafdafdafdafadsfd");

        //println("bu add2");
    }
    fun pad(num: String, lengh: Int = 12):String? {var b:String;b=num;        for (m in 1..(lengh-num.length)){ b=b+"0" };        return b;    }

    fun buAdd2() {
//        var bundle:Bundle= intent.extras!!

        //      var id=bundle.getInt("ID",0)

        var values = ContentValues()
        values.put("Title", " meet professor")
        values.put("Description", "Create any pattern of your own - tiles, texture, skin, wallpaper, comic effect, website background and more.  Change any artwork of pattern you found into different flavors and call them your own.")
        //listNotes.add(Note(1," meet professor","Create any pattern of your own - tiles, texture, skin, wallpaper, comic effect, website background and more.  Change any artwork of pattern you found into different flavors and call them your own."))



    }

}
